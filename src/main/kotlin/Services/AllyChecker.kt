package Services

interface AllyChecker {
    fun areAlly(characterOne : Classes.Character, characterTwo: Classes.Character) : Boolean
}