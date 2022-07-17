package Classes.Factions

import Classes.Character

class FactionGroup : IFactionGroup {
    private val factions : MutableList<IFaction> = mutableListOf()

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
