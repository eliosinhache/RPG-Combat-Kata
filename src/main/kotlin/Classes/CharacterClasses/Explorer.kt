package Classes.CharacterClasses

import Classes.ICharacterInteraction

open class Explorer: CharacterClass(), ICharacterInteraction {
    override fun getRange(): Int {
        return 3
    }
}