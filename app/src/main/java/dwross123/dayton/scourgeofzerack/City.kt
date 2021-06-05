package dwross123.dayton.scourgeofzerack

class City(override var xPos: Float, override var yPos: Float, override val player: Int, override val faction: Faction):Clickable{
    //xPos, yPos is of center
    override var size =150f
    val team =player
    var productionProgress =0
    val clickableType = ClickableType.CITY
}