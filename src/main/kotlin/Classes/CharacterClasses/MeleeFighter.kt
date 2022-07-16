package Classes.CharacterClasses

open class MeleeFighter : CharacterClass(), IFighter {
    override fun getRange(): Int {
        return 2
    }
}