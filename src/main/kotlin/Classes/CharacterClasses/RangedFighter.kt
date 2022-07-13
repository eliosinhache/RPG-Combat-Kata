package Classes.CharacterClasses

import Classes.CharacterClass

open class RangedFighter : CharacterClass(), IFighter {
    override fun initialRange(): Int {
        return 20
    }

    override fun getTypeOfFighter(): String {
        return this.toString()
    }
}