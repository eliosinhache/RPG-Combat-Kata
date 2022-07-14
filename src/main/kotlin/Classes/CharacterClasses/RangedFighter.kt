package Classes.CharacterClasses

open class RangedFighter : CharacterClass(), IFighter {
    override fun initialRange(): Int {
        return 20
    }
}