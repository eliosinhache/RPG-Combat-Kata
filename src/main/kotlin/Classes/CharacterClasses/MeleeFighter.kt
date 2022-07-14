package Classes.CharacterClasses

open class MeleeFighter : CharacterClass(), IFighter {
    override fun initialRange(): Int {
        return 2
    }
}