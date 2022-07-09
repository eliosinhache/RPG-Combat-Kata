import Classes.*
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito

class CharacterShould {
    private val maxHealth = 1000
    private val minHealth = 0
    private val character = Character()
    private val characterTwo = Character()

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
        val iFighter = Mockito.mock(IFighter::class.java)
        Mockito.`when`(iFighter.initialRange()).thenReturn(2)

        character.setTypeOfFighter(iFighter)
        assertThat(character.getRanged()).isEqualTo(2)
    }

    @Test
    fun `Have range of twenty if it is ranged fighter`() {
        val iFighter = Mockito.mock(IFighter::class.java)
        Mockito.`when`(iFighter.initialRange()).thenReturn(20)

        character.setTypeOfFighter(iFighter)
        assertThat(character.getRanged()).isEqualTo(20)
    }

    @Test
    fun `Deal damage if target is in range`() {
        characterTwo.position = 20
        character.position = 0
        val rangedFighter = Mockito.mock(IFighter::class.java)
        Mockito.`when`(rangedFighter.initialRange()).thenReturn(20)
        val meleeFighter = Mockito.mock(IFighter::class.java)
        Mockito.`when`(meleeFighter.initialRange()).thenReturn(2)

        character.setTypeOfFighter(rangedFighter)
        characterTwo.setTypeOfFighter(meleeFighter)

        character.dealDamage(characterTwo, 200)
        characterTwo.dealDamage(character, 200)

        assertThat(characterTwo.health).isEqualTo(800)
        assertThat(character.health).isEqualTo(maxHealth)
    }

    @Test
    fun `Not deal damage if target is not in range`() {
        characterTwo.position = 20
        character.position = 0
        val iFighter = Mockito.mock(IFighter::class.java)
        Mockito.`when`(iFighter.initialRange()).thenReturn(2)
        character.setTypeOfFighter(iFighter)

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
    fun `Not deal damage yo allies`() {
        val faction = Mockito.mock(IFaction::class.java)
        character.joinFaction(faction)
        characterTwo.joinFaction(faction)

        character.dealDamage(characterTwo, 200)

        assertThat(characterTwo.health).isEqualTo(maxHealth)
    }

    @Test
    fun `Heal allies`() {
        val faction = Mockito.mock(IFaction::class.java)
        character.joinFaction(faction)
        characterTwo.joinFaction(faction)

        val enemyCharacter = Classes.Character()

        enemyCharacter.dealDamage(characterTwo, 200)
        character.heal(characterTwo, 100)

        assertThat(characterTwo.health).isEqualTo(900)
    }
}