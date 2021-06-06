package dwross123.dayton.scourgeofzerack

class City(override var xPos: Float, override var yPos: Float, override val player: Int, override val faction: Faction, override var size: Float):Clickable{
    //xPos, yPos is of center
    val team =player
    var productionProgress =0
    val clickableType = ClickableType.CITY
    override var speed =0f //NO!
}