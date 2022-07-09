package Classes

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

open class Character {
    var position= 1
    var range = 1
    var alive = true
    var level = 1
    var health = 1000
    private lateinit var fighterClass : IFighter
    private var factions: MutableList<IFaction> = mutableListOf()

    fun dealDamage(target: Character, amount: Int) {
        if (target == this) return
        if (isTargetAllie(target)) return
        if (abs(target.position - this.position) > range) return
        var totalAmount = amount
        if (target.level - 5 >= this.level ) totalAmount = amount / 2
        if (target.level + 5 <= this.level) totalAmount = amount + (amount / 2)
        target.receiveDamage(totalAmount)
    }

    private fun isTargetAllie(target: Character): Boolean {
        factions.forEach{
            if (target.getFactions().contains(it)) return true
        }
        return false
    }

    private fun receiveDamage(amount: Int) {
        health = max(0, health - amount)
        if (health == 0) { alive = false }
    }

    fun heal(target: Character, amount: Int) {
        if (!target.alive) return
        if (!isTargetAllie(target) && target != this) return
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