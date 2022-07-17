package Classes

import kotlin.math.max

open class Prop (var health: Int, var position: Int) {

    fun receiveDamage(attacker: Character, amount: Int) {
        if (health == 0) return
        health = max(0, health - amount)
    }

    fun getTargetPosition(): Int {
        return position
    }

    fun isDestroyed(): Boolean {
        return health == 0
    }

}