package Classes.CharacterClasses

import Classes.Character
import Classes.CharacterClass

open class AnimalFighter : CharacterClass(), IFighter, IAnimal {

    override fun petBy(byCharacter: Character, me: Classes.Character) {
        if (me.isCharacterAllie(byCharacter)) { return }
        me.dealDamage(byCharacter, 500)
    }

    override fun initialRange(): Int {
        TODO("Not yet implemented")
    }

    override fun getTypeOfFighter(): String {
        TODO("Not yet implemented")
    }
}