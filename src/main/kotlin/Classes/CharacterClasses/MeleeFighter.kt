package Classes.CharacterClasses

import Classes.CharacterClass

open class MeleeFighter : CharacterClass(), IFighter {
    override fun initialRange(): Int {
        return 2
    }
}