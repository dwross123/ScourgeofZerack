package dwross123.dayton.scourgeofzerack

class Unit (override var xPos: Float, override var yPos: Float, override val player: Int, override val faction: Faction):Clickable{
    //xPos, yPos is of center
    var speed =150f
    override var size =75f
    val team =player
    val clickableType = ClickableType.UNIT
}