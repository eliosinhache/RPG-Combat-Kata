import Classes.Character
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.assertTrue

class CharacterShould {
    private val maxHealth = 1000
    private val minHealth = 0

    @Test
    fun  `Start with max Health` () {
        var character = Character()
        assertThat(character.Health).isEqualTo(maxHealth)
    }

    @Test
    fun `Start with first Level` () {
        val character = Character()
        assertThat(character.Level).isEqualTo(1)
    }

    @Test
    fun `Start Alive` () {
        val character = Character()
        assertThat(character.Alive).isTrue
    }

    @Test
    fun `Deal damage to other character`() {

        val characterOne = Character()
        val characterTwo = Character()

        characterTwo.DealDamage(characterOne, 100)

        assertThat(characterOne.Health).isEqualTo(900)
    }

    @Test
    fun `Die when damage received exceeds current health`() {

        val characterOne = Character()
        val characterTwo = Character()

        characterTwo.DealDamage(characterOne, 1200)

        assertThat(characterOne.Health).isEqualTo(0)
        assertThat(characterOne.Alive).isFalse
    }

    @Test
    fun `Heal other character`() {
        val characterOne = Character()
        val characterTwo = Character()

        characterTwo.DealDamage(characterOne, 300)
        characterTwo.Heal(characterOne, 200)

        assertThat(characterOne.Health).isEqualTo(900)
    }

    @Test
    fun `Not healed if is dead`() {
        val characterOne = Character()
        val characterTwo = Character()

        characterTwo.DealDamage(characterOne, maxHealth)
        characterTwo.Heal(characterOne, 200)

        assertThat(characterOne.Health).isEqualTo(0)
    }

    @Test
    fun `Not raise health above max health`() {
        val characterOne = Character()
        val characterTwo = Character()

        characterTwo.DealDamage(characterOne, 100)
        characterTwo.Heal(characterOne, 200)

        assertThat(characterOne.Health).isEqualTo(maxHealth)
    }
}