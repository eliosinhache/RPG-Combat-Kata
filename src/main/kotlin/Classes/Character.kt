package Classes

import Classes.CharacterClasses.AnimalFighter
import Classes.CharacterClasses.CharacterClass
import Classes.CharacterClasses.Explorer
import Classes.Factions.IFaction
import Classes.Factions.IFactionGroup

open class Character (val characterClass: CharacterClass, private var factionGroup: IFactionGroup) {
    var position= 1
    var alive = true
    var level = 1
    var health = 1000

    fun getTargetPosition(): Int {
        return position
    }

    fun getRanged(): Int {
        return characterClass.getRange()
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
        (animal.characterClass as AnimalFighter).DomesticateBy(this, animal)
    }
}