package Classes.CharacterClasses

open class RangedFighter : CharacterClass(), IFighter {
    override fun getRange(): Int {
        return 20
    }
}