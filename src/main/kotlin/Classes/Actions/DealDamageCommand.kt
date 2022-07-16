package Classes.Actions

import Classes.ITargetActions
import kotlin.math.abs

class DealDamageCommand (val attacker: Classes.Character, val target: ITargetActions, var amount: Int) : Command {

    private fun dealDamage(){
        if (target == attacker) return
        if (abs(target.getTargetPosition() - attacker.position) > attacker.characterClass.getRange()) return
        target.receiveDamage(attacker, amount)
    }

    override fun execute() {
        dealDamage()
    }
}