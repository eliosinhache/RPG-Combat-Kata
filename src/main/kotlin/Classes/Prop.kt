package Classes

import kotlin.math.max

open class Prop (var heal: Int, var position: Int): ITargetActions {
    var destroyed : Boolean = false

    override fun receiveDamage(attacker: Character, amount: Int) {
        heal = max(0, heal - amount)
        if (heal == 0)  destroyed = true
    }

    override fun getTargetPosition(): Int {
        return position
    }

}