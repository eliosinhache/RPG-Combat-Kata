package Classes

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

open class Character : ITargetActions {
    var position= 1
    var alive = true
    var level = 1
    var health = 1000
    private var range = 1
    private var factions: MutableList<IFaction> = mutableListOf()
    private lateinit var fighterClass : IFighter

    fun dealDamage(target: ITargetActions, amount: Int) {
        if (target == this) return
        if (abs(target.getTargetPosition() - this.position) > range) return
        target.receiveDamage(this, amount)
    }

    private fun isAttackerAllie(attacker: Character): Boolean {
        factions.forEach{
            if (attacker.getFactions().contains(it)) return true
        }
        return false
    }

    override fun receiveDamage(attacker: Character, amount: Int) {
        if (isAttackerAllie(attacker)) return
        var totalAmount = amount
        if ( this.level- 5 >= attacker.level ) totalAmount = amount / 2
        if (this.level + 5 <= attacker.level) totalAmount = amount + (amount / 2)
        health = max(0, health - totalAmount)
        if (health == 0) { alive = false }
    }

    override fun getTargetPosition(): Int {
        return position
    }

    fun heal(target: Character, amount: Int) {
        if (!target.alive) return
        if (!isAttackerAllie(target) && target != this) return
        target.receiveHeal(amount)
    }

    private fun receiveHeal(amount: Int) {
        health = min(1000, health + amount)
    }

    fun setTypeOfFighter(typeOfFighter: IFighter) {
        fighterClass = typeOfFighter
        range = typeOfFighter.initialRange()
    }

    fun getRanged(): Int {
        return range
    }

    fun getFactions(): List<IFaction> {
        return factions
    }

    fun joinFaction(faction: IFaction) {
        factions.add(faction)
    }

    fun leaveFaction(faction: IFaction) {
        factions.remove(faction)
    }
}