package Classes

import Classes.CharacterClasses.AnimalFighter
import Classes.CharacterClasses.Explorer
import Classes.CharacterClasses.IAnimal
import Classes.Factions.IFaction
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

open class Character : ITargetActions {
    var position= 1
    var alive = true
    var level = 1
    var health = 1000
    //lateinit var characterClass : IFighter
    private var characterClass : CharacterClass? = null
    private var range = 1
    private var factionGroup: IFactionGroup = FactionGroup() //MutableList<IFaction> = mutableListOf()

    fun dealDamage(target: ITargetActions, amount: Int) {
        if (target == this) return
        if (abs(target.getTargetPosition() - this.position) > range) return
        target.receiveDamage(this, amount)
    }

    fun isCharacterAllie(character: Character): Boolean {
        return factionGroup.isCharacterAllie(character)
    }

    override fun receiveDamage(attacker: Character, amount: Int) {
        if (isCharacterAllie(attacker)) return
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
        if (!isCharacterAllie(target) && target != this) return
        target.receiveHeal(amount)
    }

    private fun receiveHeal(amount: Int) {
        health = min(1000, health + amount)
    }

    fun setClass(typeOfCharacter: CharacterClass) {
        characterClass = typeOfCharacter
        range = typeOfCharacter.initialRange()
    }

    fun getRanged(): Int {
        return range
    }

    fun getFactions(): List<IFaction> {
        return factionGroup.getAllFactions()
    }

    fun joinFaction(faction: IFaction) {
        factionGroup.joinToFaction(faction)
    }

    fun leaveFaction(faction: IFaction) {
        factionGroup.leaveFaction(faction)
    }

    fun pet(animal: Character) {
        if (characterClass !is Explorer) { return }
        (animal.characterClass as AnimalFighter).petBy(this, animal)
    }
}