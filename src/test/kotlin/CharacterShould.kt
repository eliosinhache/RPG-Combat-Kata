import Classes.Character
import Classes.IFighter
import Classes.MeleeFighter
import Classes.RangedFighter
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito
import org.mockito.kotlin.mock

class CharacterShould {
    private val maxHealth = 1000
    private val minHealth = 0
    private val character = Character()

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
        val characterTwo = Character()

        characterTwo.dealDamage(character, 100)

        assertThat(character.health).isEqualTo(900)
    }

    @Test
    fun `Die when damage received exceeds current health`() {
        val characterTwo = Character()

        characterTwo.dealDamage(character, 1200)

        assertThat(character.health).isEqualTo(minHealth)
        assertThat(character.alive).isFalse
    }

    @Test
    fun `Not heal other character`() {
        val characterTwo = Character()

        characterTwo.dealDamage(character, 300)
        characterTwo.heal(character, 200)

        assertThat(character.health).isEqualTo(700)
    }

    @Test
    fun `Heal itself`() {
        val characterTwo = Character()

        characterTwo.dealDamage(character, 300)
        character.heal(character, 200)

        assertThat(character.health).isEqualTo(900)
    }

    @Test
    fun `Not healed if is dead`() {
        val characterTwo = Character()

        characterTwo.dealDamage(character, maxHealth)
        characterTwo.heal(character, 200)

        assertThat(character.health).isEqualTo(0)
    }

    @Test
    fun `Not raise health above max health`() {
        val characterTwo = Character()

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
        val characterTwo = Character()
        character.level = 10
        characterTwo.level = 5

        characterTwo.dealDamage(character, 100)

        assertThat(character.health).isEqualTo(950)
    }

    @Test
    fun `Deal 50% more damage if target are 5 levels below it`() {
        val characterTwo = Character()
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
    fun `Have range of two if it is ranged fighter`() {
        val iFighter = Mockito.mock(IFighter::class.java)
        Mockito.`when`(iFighter.initialRange()).thenReturn(20)

        character.setTypeOfFighter(iFighter)
        assertThat(character.getRanged()).isEqualTo(20)
    }

    @Test
    fun `Deal damage if target is in range`() {
        val characterTwo = Character()
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
        val characterTwo = Character()
        characterTwo.position = 20
        character.position = 0
        val iFighter = Mockito.mock(IFighter::class.java)
        Mockito.`when`(iFighter.initialRange()).thenReturn(2)
        character.setTypeOfFighter(iFighter)

        character.dealDamage(characterTwo, 200)

        assertThat(characterTwo.health).isEqualTo(1000)
    }
}