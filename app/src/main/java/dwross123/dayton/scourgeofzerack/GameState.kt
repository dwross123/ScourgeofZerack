package dwross123.dayton.scourgeofzerack

import kotlin.math.sqrt


class GameState (val playerCount: Int, val grid: Grid){

    var units = ArrayList<Unit>()
    var cities = ArrayList<City>()
    var playerTurn = 0

    fun move(unit:Unit, xPos:Float, yPos:Float) :Boolean{
        var xDist = (unit.xPos-xPos)*(unit.xPos-xPos)
        var yDist = (unit.xPos-yPos)*(unit.xPos-yPos)
        val dist = sqrt(xDist+yDist)
        var finalXPos = xPos
        var finalYPos = yPos
        if(dist>unit.speed){
            val proportion = unit.speed/dist
            val xMove = (xPos-unit.xPos)*proportion
            val yMove = (yPos-unit.yPos)*proportion
            finalXPos = unit.xPos+xMove
            finalYPos = unit.yPos+yMove
        }
        val impactedUnit = checkPotentialUnitIntersections(unit.size, finalXPos, finalYPos)
        if(impactedUnit!=null){
            if(unit.team==impactedUnit.team){
                //reject move
                //TODO snap to position
                return false
            }else{
                //TODO damage unit
                killUnit(impactedUnit)
            }
        }else {
            val impactedCity = checkPotentialCityIntersections(unit.size, finalXPos, finalYPos)
            if(impactedCity!=null){
                if(unit.team!=impactedCity.team){
                    razeCity(impactedCity)
                }
            }
        }
        unit.xPos = finalXPos
        unit.yPos = finalYPos
        grid.drawGameState()
        return true
    }

    private fun killUnit(unit: Unit){
        units.remove(unit)
    }
    private fun razeCity(city: City){
        cities.remove(city)
        if(checkFactionEliminated(city.player)){
            //(TODO)remove player control/set feral once MP

        }
    }
    private fun checkFactionEliminated(player:Int):Boolean{
        for(city in cities){
            if(city.player==player)return false
        }
        return true
    }

    //intersect
    fun checkPotentialUnitIntersections(size1: Float, left1: Float, top1: Float):Unit?{
        for(unit in units){
            if(intersect(size1, left1, top1, unit.size, unit.xPos, unit.yPos)) return unit
        }
        return null
    }
    fun checkPotentialCityIntersections(size1: Float, left1: Float, top1: Float):City?{
        for(city in cities){
            if(intersect(size1, left1, top1, city.size, city.xPos, city.yPos)) return city
        }
        return null
    }
    fun intersect(size1: Float, left1: Float, top1: Float, size2: Float, left2: Float, top2: Float): Boolean {
        val right1 = left1 + size1
        val bottom1 = top1 + size1
        val right2 = left2 + size2
        val bottom2 = top2 + size2
        if (left1<=right2 && right1>=left2){
            if(top1<=bottom2 && bottom1>=top2) return true
        }
        return false
    }
    //Who's move


    //Who has movement left
    //todo hash table for units with movement

    //Creation
    fun createUnit(xPos: Float, yPos: Float, player: Int, faction: Faction){
        val unit = Unit(xPos, yPos, player, faction)
        if(checkPotentialUnitIntersections(unit.size, xPos, yPos)==null) {
            units.add(unit)
        } //Todo have unit move off city
    }
    fun createCity(xPos: Float, yPos: Float, player: Int, faction: Faction){
        val city = City(xPos, yPos, player, faction)
        cities.add(city)
    }
}