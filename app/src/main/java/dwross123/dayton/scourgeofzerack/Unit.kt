package dwross123.dayton.scourgeofzerack

class Unit (override var xPos: Float, override var yPos: Float, override val player: Int, override val faction: Faction, override var size: Float):Clickable{
    //xPos, yPos is of center
    override var speed = size*2f
    val team =player
    val clickableType = ClickableType.UNIT
}