package dwross123.dayton.scourgeofzerack

class City(val xPos: Float, val yPos: Float, override val player: Int, val faction: Faction):Clickable{
    //xPos, yPos is of center
    var size =100f
    val team =player
    var productionProgress =0
    val clickableType = ClickableType.CITY
}