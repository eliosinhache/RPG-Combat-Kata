package Classes

import kotlin.math.max
import kotlin.math.min

class Character {
    var alive = true
    var level = 1
    var health = 1000

    fun dealDamage(target: Character, amount: Int) {
        if (target == this) { return }
        var totalAmount = amount
        if (target.level - 5 >= this.level ) totalAmount = amount / 2
        if (target.level + 5 <= this.level) totalAmount = amount + (amount / 2)
        target.receiveDamage(totalAmount)
    }

    private fun receiveDamage(amount: Int) {
        health = max(0, health - amount)
        if (health == 0) { alive = false }
    }

    fun heal(target: Character, amount: Int) {

        if (!target.alive || target != this) { return }
        target.receiveHeal(amount)
    }

    private fun receiveHeal(amount: Int) {
        health = min(1000, health + amount)
    }
}