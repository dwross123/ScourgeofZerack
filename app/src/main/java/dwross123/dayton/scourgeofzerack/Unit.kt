package dwross123.dayton.scourgeofzerack

class Unit (var xPos: Float, var yPos: Float, override val player: Int, val faction: Faction):Clickable{
    //xPos, yPos is of center
    var speed =100f
    var size =50f
    val team =player
    val clickableType = ClickableType.UNIT
}