import Classes.*
import Classes.Actions.DealDamageCommand
import Classes.Actions.Invoker
import Classes.CharacterClasses.AnimalFighter
import Classes.CharacterClasses.Explorer
import Classes.CharacterClasses.MeleeFighter
import Classes.CharacterClasses.RangedFighter
import Classes.Factions.IFaction
import Classes.Factions.IFactionGroup
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.mockito.kotlin.*

class CharacterShould {
    private lateinit var meleeCharacter : Character
    private lateinit var rangedCharacter : Character
    private lateinit var animalCharacter : Character
    private lateinit var explorerCharacter : Character
    private val maxHealth = 1000
    private val minHealth = 0
    private val firstLevel = 1
    private val factionGroupCharacter01 = mock<IFactionGroup>()
    private val factionGroupCharacter02 = mock<IFactionGroup>()
    private val prop = mock<Prop>()
    private val faction = mock<IFaction>()
    private val animalClass = mock<AnimalFighter>()
    private val rangedClass = mock<RangedFighter>()
    private val meleeClass =  mock<MeleeFighter>()
    private val explorerClass =  mock<Explorer>()
    private lateinit var invoker : Invoker

    @BeforeEach
    fun  `SetUp` () {
        meleeCharacter = Character( meleeClass, factionGroupCharacter01)
        rangedCharacter = Character(rangedClass, factionGroupCharacter02)
        explorerCharacter = Character( explorerClass, factionGroupCharacter01)
        animalCharacter = Character( animalClass, factionGroupCharacter02)
        invoker = Invoker()
    }

    @Test
    fun  `Start with max Health` () {
        assertThat(meleeCharacter.health).isEqualTo(maxHealth)
    }

    @Test
    fun `Start with first Level` () {
        assertThat(meleeCharacter.level).isEqualTo(firstLevel)
    }

    @Test
    fun `Start Alive` () {
        assertThat(meleeCharacter.alive).isTrue
    }

    @Test
    fun `Deal damage to other character`() {
        dealDamageFromTo(rangedCharacter, meleeCharacter, 100)

        assertThat(meleeCharacter.health).isEqualTo(900)
    }

    @Test
    fun `Die when damage received exceeds current health`() {
        //rangedCharacter.dealDamage(meleeCharacter, 1200)

        dealDamageFromTo(rangedCharacter, meleeCharacter, 1200)

        assertThat(meleeCharacter.health).isEqualTo(minHealth)
        assertThat(meleeCharacter.alive).isFalse
    }

    @Test
    fun `Not heal other character`() {
        dealDamageFromTo(rangedCharacter, meleeCharacter, 300)
        //rangedCharacter.dealDamage(meleeCharacter, 300)
        rangedCharacter.heal(meleeCharacter, 200)

        assertThat(meleeCharacter.health).isEqualTo(700)
    }

    @Test
    fun `Heal itself`() {
        //rangedCharacter.dealDamage(meleeCharacter, 300)
        dealDamageFromTo(rangedCharacter, meleeCharacter, 300)
        meleeCharacter.heal(meleeCharacter, 200)

        assertThat(meleeCharacter.health).isEqualTo(900)
    }

    @Test
    fun `Not healed if is dead`() {
//        rangedCharacter.dealDamage(meleeCharacter, maxHealth)
        dealDamageFromTo(rangedCharacter, meleeCharacter, maxHealth)
        rangedCharacter.heal(meleeCharacter, 200)

        assertThat(meleeCharacter.health).isEqualTo(0)
    }

    @Test
    fun `Not raise health above max health`() {
//        rangedCharacter.dealDamage(meleeCharacter, 100)
        dealDamageFromTo(rangedCharacter, meleeCharacter, 100)
        meleeCharacter.heal(meleeCharacter, 200)

        assertThat(meleeCharacter.health).isEqualTo(maxHealth)
    }

    @Test
    fun `Not deal damage to itself`() {
//        meleeCharacter.dealDamage(meleeCharacter, 100)
        dealDamageFromTo(meleeCharacter, meleeCharacter, 100)
        assertThat(meleeCharacter.health).isEqualTo(maxHealth)
    }

    @Test
    fun `Deal 50% less damage if target are 5 levels above it`() {
        meleeCharacter.level = 10
        rangedCharacter.level = 5

//        rangedCharacter.dealDamage(meleeCharacter, 100)
        dealDamageFromTo(rangedCharacter, meleeCharacter, 100)

        assertThat(meleeCharacter.health).isEqualTo(950)
    }

    @Test
    fun `Deal 50% more damage if target are 5 levels below it`() {
        meleeCharacter.level = 5
        rangedCharacter.level = 10

        dealDamageFromTo(rangedCharacter, meleeCharacter, 100)

        assertThat(meleeCharacter.health).isEqualTo(850)
    }

    @Test
    fun `Have max range`() {
        assertThat(meleeCharacter.getRanged()).isNotNull
    }

    @Test
    fun `Have range of two if it is melee fighter`() {
        Mockito.`when`(meleeClass.getRange()).thenReturn(2)

        assertThat(meleeCharacter.getRanged()).isEqualTo(2)
    }

    @Test
    fun `Have range of twenty if it is ranged fighter`() {
        Mockito.`when`(rangedClass.getRange()).thenReturn(20)

        assertThat(rangedCharacter.getRanged()).isEqualTo(20)
    }

    @Test
    fun `Deal damage if target is in range`() {
        rangedCharacter.position = 20
        meleeCharacter.position = 0

        Mockito.`when`(rangedClass.getRange()).thenReturn(20)

        dealDamageFromTo(rangedCharacter, meleeCharacter, 200)


        assertThat(meleeCharacter.health).isEqualTo(800)
    }

    @Test
    fun `Not deal damage if target is not in range`() {
        rangedCharacter.position = 20
        meleeCharacter.position = 0

        Mockito.`when`(meleeClass.getRange()).thenReturn(2)

        dealDamageFromTo(meleeCharacter, rangedCharacter, 200)

        assertThat(rangedCharacter.health).isEqualTo(1000)
    }

    @Test
    fun `Start without factions`() {
        assertThat(meleeCharacter.getFactions().isEmpty()).isTrue
    }

    @Test
    fun `Join to one or more factions`() {
        meleeCharacter.joinFaction(faction)
        assertThat(meleeCharacter.getFactions()).isNotNull
    }
    @Test
    fun `Leave one or more factions`() {
        meleeCharacter.joinFaction(faction)
        meleeCharacter.leaveFaction(faction)

        assertThat(meleeCharacter.getFactions().isEmpty()).isTrue
    }

    @Test
    fun `Not deal damage to allies`() {
        whenever(factionGroupCharacter02.isCharacterAllie(any())).thenReturn(true)

        dealDamageFromTo(meleeCharacter, rangedCharacter, 200)

        assertThat(rangedCharacter.health).isEqualTo(maxHealth)
    }

    @Test
    fun `Heal allies`() {
        Mockito.`when`(factionGroupCharacter02.isCharacterAllie(any())).thenReturn(false)
        Mockito.`when`(factionGroupCharacter01.isCharacterAllie(any())).thenReturn(true)

        val factionGroupEnemy = Mockito.mock(IFactionGroup::class.java)
        Mockito.`when`(factionGroupEnemy.isCharacterAllie(any())).thenReturn(false)
        val enemyCharacter = Character(rangedClass, factionGroupEnemy)

        dealDamageFromTo(enemyCharacter, rangedCharacter, 200)
        meleeCharacter.heal(rangedCharacter, 100)

        assertThat(rangedCharacter.health).isEqualTo(900)
    }

    @Test
    fun `Deal damage to props`() {
        whenever(prop.getTargetPosition()).thenReturn(1)

        dealDamageFromTo(meleeCharacter, prop, 2000)

        verify(prop).receiveDamage(any(), any())
    }

//    @Test
//    fun `Pet animal class character if is explorer class`() {
//        explorerCharacter.pet(animalCharacter)
//
//        verify(animalClass).DomesticateBy(anyVararg(), anyVararg())
//    }
//
//
//    @Test
//    fun `Not Pet animal class character if is explorer class`() {
//        meleeCharacter.pet(animalCharacter)
//
//        verify(animalClass, never()).DomesticateBy(anyVararg(), anyVararg())
//    }

    private fun dealDamageFromTo(attacker: Classes.Character, target: Classes.ITargetActions, amount: Int) {
        val dealDamage = DealDamageCommand(attacker, target, amount)
        invoker.addCommand(dealDamage)
        invoker.executeCommands()
    }
}