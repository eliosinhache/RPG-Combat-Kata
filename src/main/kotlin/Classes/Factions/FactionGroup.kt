package Classes.Factions

import Classes.Character

class FactionGroup : IFactionGroup {
    private val factions : MutableList<IFaction> = mutableListOf()

    override fun isCharacterAllie(character: Character): Boolean {
        character.getFactions().forEach {
            if (getAllFactions().contains(it)) return true
        }
        return false
    }

    override fun getAllFactions(): List<IFaction> {
        return factions
    }

    override fun joinToFaction(faction: IFaction) {
        factions.add(faction)
    }

    override fun leaveFaction(faction: IFaction) {
        factions.remove(faction)
    }
}
