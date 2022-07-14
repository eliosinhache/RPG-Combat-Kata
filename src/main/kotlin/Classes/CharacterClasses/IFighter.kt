package Classes.CharacterClasses

interface IFighter {
    fun initialRange(): Int
    fun getTypeOfFighter():String {
        return this.toString()
    }

}
