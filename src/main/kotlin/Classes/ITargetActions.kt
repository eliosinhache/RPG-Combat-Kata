package Classes

interface ITargetActions {
    fun receiveDamage(attacker: Character, amount: Int)
    fun getTargetPosition(): Int
}