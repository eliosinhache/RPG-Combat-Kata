package Classes.Factions

import Classes.Character

interface IFactionGroup {
    fun isCharacterAllie(character: Character): Boolean
    fun getAllFactions() : List<IFaction>
    fun joinToFaction(faction: IFaction)
    fun leaveFaction(faction: IFaction)
}
