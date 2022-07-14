import Classes.*
import Classes.CharacterClasses.AnimalFighter
import Classes.CharacterClasses.Explorer
import Classes.CharacterClasses.MeleeFighter
import Classes.CharacterClasses.RangedFighter
import Classes.Factions.IFaction
import Classes.Factions.ScarFaceFaction
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito
import org.mockito.kotlin.*

class CharacterShould {
    private val maxHealth = 1000
    private val minHealth = 0
    private val factionGroupCharacter01 = mock<IFactionGroup>()
    private val factionGroupCharacter02 = mock<IFactionGroup>()
    private val character = Character(factionGroupCharacter01)
    private val characterTwo = Character(factionGroupCharacter02)
    private val animal = Character(factionGroupCharacter02)

    @Test
    fun  `Start with max Health` () {
        assertThat(character.health).isEqualTo(maxHealth)
    }

    @Test
    fun `Start with first Level` () {
        assertThat(character.level).isEqualTo(1)
    }

    @Test
    fun `Start Alive` () {
        assertThat(character.alive).isTrue
    }

    @Test
    fun `Deal damage to other character`() {
        characterTwo.dealDamage(character, 100)

        assertThat(character.health).isEqualTo(900)
    }

    @Test
    fun `Die when damage received exceeds current health`() {
        characterTwo.dealDamage(character, 1200)

        assertThat(character.health).isEqualTo(minHealth)
        assertThat(character.alive).isFalse
    }

    @Test
    fun `Not heal other character`() {
        characterTwo.dealDamage(character, 300)
        characterTwo.heal(character, 200)

        assertThat(character.health).isEqualTo(700)
    }

    @Test
    fun `Heal itself`() {
        characterTwo.dealDamage(character, 300)
        character.heal(character, 200)

        assertThat(character.health).isEqualTo(900)
    }

    @Test
    fun `Not healed if is dead`() {
        characterTwo.dealDamage(character, maxHealth)
        characterTwo.heal(character, 200)

        assertThat(character.health).isEqualTo(0)
    }

    @Test
    fun `Not raise health above max health`() {
        characterTwo.dealDamage(character, 100)
        character.heal(character, 200)

        assertThat(character.health).isEqualTo(maxHealth)
    }

    @Test
    fun `Not deal damage to itself`() {
        character.dealDamage(character, 100)

        assertThat(character.health).isEqualTo(maxHealth)
    }

    @Test
    fun `Deal 50% less damage if target are 5 levels above it`() {
        character.level = 10
        characterTwo.level = 5

        characterTwo.dealDamage(character, 100)

        assertThat(character.health).isEqualTo(950)
    }

    @Test
    fun `Deal 50% more damage if target are 5 levels below it`() {
        character.level = 5
        characterTwo.level = 10

        characterTwo.dealDamage(character, 100)

        assertThat(character.health).isEqualTo(850)
    }

    @Test
    fun `Have max range`() {
        assertThat(character.getRanged()).isNotNull
    }

    @Test
    fun `Have range of two if it is melee fighter`() {
        val iFighter = Mockito.mock(MeleeFighter()::class.java)
        Mockito.`when`(iFighter.initialRange()).thenReturn(2)

        character.setClass(iFighter)
        assertThat(character.getRanged()).isEqualTo(2)
    }

    @Test
    fun `Have range of twenty if it is ranged fighter`() {
        val iFighter = Mockito.mock(RangedFighter::class.java)
        Mockito.`when`(iFighter.initialRange()).thenReturn(20)

        character.setClass(iFighter)
        assertThat(character.getRanged()).isEqualTo(20)
    }

    @Test
    fun `Deal damage if target is in range`() {
        characterTwo.position = 20
        character.position = 0
        val rangedFighter = Mockito.mock(RangedFighter::class.java)
        Mockito.`when`(rangedFighter.initialRange()).thenReturn(20)
        val meleeFighter = Mockito.mock(MeleeFighter::class.java)
        Mockito.`when`(meleeFighter.initialRange()).thenReturn(2)

        character.setClass(rangedFighter)
        characterTwo.setClass(meleeFighter)

        character.dealDamage(characterTwo, 200)
        characterTwo.dealDamage(character, 200)

        assertThat(characterTwo.health).isEqualTo(800)
        assertThat(character.health).isEqualTo(maxHealth)
    }

    @Test
    fun `Not deal damage if target is not in range`() {
        characterTwo.position = 20
        character.position = 0
        val iFighter = Mockito.mock(MeleeFighter::class.java)
        Mockito.`when`(iFighter.initialRange()).thenReturn(2)
        character.setClass(iFighter)

        character.dealDamage(characterTwo, 200)

        assertThat(characterTwo.health).isEqualTo(1000)
    }

    @Test
    fun `Start without factions`() {
        assertThat(character.getFactions().isEmpty()).isTrue
    }

    @Test
    fun `Join to one or more factions`() {
        val faction = Mockito.mock(IFaction::class.java)
        character.joinFaction(faction)
        assertThat(character.getFactions()).isNotNull
    }
    @Test
    fun `Leave one or more factions`() {
        val faction = Mockito.mock(IFaction::class.java)
        character.joinFaction(faction)
        character.leaveFaction(faction)
        assertThat(character.getFactions().isEmpty()).isTrue
    }

    @Test
    fun `Not deal damage to allies`() {
        whenever(factionGroupCharacter02.isCharacterAllie(any())).thenReturn(true)

        character.dealDamage(characterTwo, 200)

        assertThat(characterTwo.health).isEqualTo(maxHealth)
    }

    @Test
    fun `Heal allies`() {
        Mockito.`when`(factionGroupCharacter01.isCharacterAllie(any())).thenReturn(true)
        Mockito.`when`(factionGroupCharacter02.isCharacterAllie(any())).thenReturn(false)

        val factionGroupEnemy = Mockito.mock(IFactionGroup::class.java)
        Mockito.`when`(factionGroupEnemy.isCharacterAllie(any())).thenReturn(false)
        val enemyCharacter = Character(factionGroupEnemy)
        enemyCharacter.dealDamage(characterTwo, 200)
        character.heal(characterTwo, 100)

        assertThat(characterTwo.health).isEqualTo(900)
    }

    @Test
    fun `Deal damage to props`() {
        val tree = mock<Prop>()
        whenever(tree.getTargetPosition()).thenReturn(1)

        character.dealDamage(tree, 2000)

        verify(tree).receiveDamage(any(), any())
    }

    @Test
    fun `Pet animal character`() {
        val animalClass = mock<AnimalFighter>()
        val explorerClass = mock<Explorer>()

        animal.setClass(animalClass)
        character.setClass(explorerClass)

        character.pet(animal)

        verify(animalClass).petBy(anyVararg(), anyVararg())
    }
}