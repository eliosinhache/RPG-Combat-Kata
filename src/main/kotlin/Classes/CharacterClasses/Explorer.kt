package Classes.CharacterClasses

import Classes.CharacterClass
import Classes.ICharacterInteraction

open class Explorer: CharacterClass(), ICharacterInteraction {
    override fun initialRange(): Int {
        return 3
    }
}