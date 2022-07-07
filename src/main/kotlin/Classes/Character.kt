package Classes

class Character {
    var Alive = true
    val Level = 1
    var Health = 1000

    fun DealDamage(target: Character, amount: Int) {
        target.ReceiveDamage(amount)
    }

    private fun ReceiveDamage(amount: Int) {
        Health = Math.max(0, Health - amount)
        if (Health == 0) { Alive = false }
    }

    fun Heal(target: Character, amount: Int) {
        if (!target.Alive) { return }
        target.ReceiveHeal(amount)
    }

    private fun ReceiveHeal(amount: Int) {
        Health = Math.min(1000, Health + amount)
    }
}