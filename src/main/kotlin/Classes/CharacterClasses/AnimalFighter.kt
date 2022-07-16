package Classes.CharacterClasses

import Classes.Character

open class AnimalFighter : CharacterClass(), IFighter, IAnimal {

    override fun DomesticateBy(byCharacter: Character, me: Classes.Character) {
        if (me.isCharacterAllie(byCharacter)) { return }
        me.dealDamage(byCharacter, 500)
    }

    override fun getRange(): Int {
        return 3
    }
}