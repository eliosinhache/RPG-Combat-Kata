package Services

open class AllyCheckerService : AllyChecker {
    override fun areAlly(characterOne : Classes.Character, characterTwo: Classes.Character) : Boolean{
        characterOne.getFactions().forEach { faction ->
            if (characterTwo.getFactions().contains(faction)){
                return true
            } }
        return false
    }
}
