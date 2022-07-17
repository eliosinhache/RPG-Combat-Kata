package Classes.Actions

import Services.AllyChecker
import kotlin.math.abs
import kotlin.math.max

class DealDamageCommand (private val attacker: Classes.Character, private val target: Classes.Character,
                         private var amount: Int, private val allyChecker: AllyChecker) : Command {

    private fun dealDamage(){
        if (target == attacker) return
        if (targetNotInRange()) return
        if (allyChecker.areAlly(attacker, target)) return

        finalAmountOfDamage()
        target.health = finalHealthTarget()
        if (target.health == 0) { target.alive = false }
    }

    private fun finalHealthTarget() = max(0, target.health - amount)

    private fun finalAmountOfDamage() {
        if (target.level - 5 >= attacker.level) amount /= 2
        if (target.level + 5 <= attacker.level) amount += (amount / 2)
    }

    private fun targetNotInRange() =
        abs(target.getTargetPosition() - attacker.position) > attacker.characterClass.getRange()


    override fun execute() {
        dealDamage()
    }
}