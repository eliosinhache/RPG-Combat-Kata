package Classes.Factions

interface IFactionGroup {
    fun getAllFactions() : List<IFaction>
    fun joinToFaction(faction: IFaction)
    fun leaveFaction(faction: IFaction)
}
