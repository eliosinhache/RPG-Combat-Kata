package Classes.CharacterClasses

import Classes.Character

open class AnimalFighter : CharacterClass(), IFighter, IAnimal {

    override fun petBy(byCharacter: Character, me: Classes.Character) {
        if (me.isCharacterAllie(byCharacter)) { return }
        me.dealDamage(byCharacter, 500)
    }

    override fun initialRange(): Int {
        TODO("Not yet implemented")
    }
}