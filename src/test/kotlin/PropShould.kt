import Classes.Prop
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class PropShould {

    @Test
    fun `Receive damage`() {
        val tree = Prop(2000, 3)
        val character = Mockito.mock(Classes.Character::class.java)

        tree.receiveDamage(character, 500)

        assertThat(tree.heal).isEqualTo(1500)
    }

    @Test
    fun `Destroyed when heal equal to zero`() {
        val tree = Prop(2000, 3)
        val character = Mockito.mock(Classes.Character::class.java)

        tree.receiveDamage(character, 2000)

        assertThat(tree.destroyed).isTrue
    }
}