package Classes.Actions

import Services.AllyChecker
import kotlin.math.min

class HealCommand (
    private val healer: Classes.Character, private val target: Classes.Character,
    private val amount: Int, private val allyChecker: AllyChecker) : Command {

    private fun heal() {
        if (!target.alive) return
        if (!allyChecker.areAlly(healer, target) && target != healer) return
        target.health = finalTargetHealth()
    }

    private fun finalTargetHealth() = min(1000, target.health + amount)

    override fun execute() {
        heal()
    }
}