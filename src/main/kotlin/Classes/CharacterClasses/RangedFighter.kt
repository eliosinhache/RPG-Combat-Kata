package Classes.CharacterClasses

import Classes.CharacterClass

open class RangedFighter : CharacterClass(), IFighter {
    override fun initialRange(): Int {
        return 20
    }
}