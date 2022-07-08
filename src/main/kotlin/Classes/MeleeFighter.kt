package Classes

class MeleeFighter : IFighter {
    override fun initialRange(): Int {
        return 2
    }

    override fun getTypeOfFighter(): String {
        return this.toString()
    }
}