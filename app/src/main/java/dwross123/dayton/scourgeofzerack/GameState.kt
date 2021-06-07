package dwross123.dayton.scourgeofzerack

import kotlin.math.sqrt
import kotlin.random.Random


class GameState (val playerCount: Int, val grid: Grid, turnsPerZombieBatch: Int, zombiesPerBatch: Int, turnsPerPlayerBatch: Int, warriorsPerBatch: Int ){

    var units = ArrayList<Unit>()
    var graves = ArrayList<Grave>()
    var cities = ArrayList<City>()
    var playerTurn = 0
    var hasMove = HashSet<Clickable>()
    var gameOver = false
    var zombiesKilled = 0
    var warriorsLost = 0
    var currentZombies = 0
    var unitSize = 0f
    var citySize = 0f

    fun findNearby(xPos: Float, yPos: Float):Clickable?{ //Biased towards units
        val areaChecked = 50f
        val unit = checkPotentialUnitIntersections(null, areaChecked, xPos-(areaChecked/2), yPos-(areaChecked/2))
        if(unit != null){
            return unit
        }
        val city = checkPotentialCityIntersections(areaChecked, xPos-(areaChecked/2), yPos-(areaChecked/2))
        if(city != null){
            return city
        }
        return null
    }
    fun move(unit:Unit, xPos:Float, yPos:Float) :Boolean{

        var finalXPos = xPos
        var finalYPos = yPos
        if(xPos<(unit.size/2)) finalXPos=(unit.size/2)
        if(yPos<(unit.size/2)) finalYPos=(unit.size/2)
        val maxX = grid.canvas.width-(unit.size/2)
        val maxY = grid.canvas.height-(unit.size/2)
        if(xPos>maxX) finalXPos=maxX
        if(yPos>maxY) finalYPos=maxY
        var xDist = (unit.xPos-finalXPos)*(unit.xPos-finalXPos)
        var yDist = (unit.yPos-finalYPos)*(unit.yPos-finalYPos)
        val dist = sqrt(xDist+yDist)

        if(dist>unit.speed){
            val proportion = unit.speed/dist
            val xMove = (finalXPos-unit.xPos)*proportion
            val yMove = (finalYPos-unit.yPos)*proportion
            finalXPos = unit.xPos+xMove
            finalYPos = unit.yPos+yMove
            //Log.w("GameState" ,"unit move attempt $dist, $proportion, $xPos, $yPos, ${unit.xPos}, ${unit.yPos}, $finalXPos, $finalYPos, ${unit.player}")
        }
        val impactedUnit = checkPotentialUnitIntersections(unit, unit.size, finalXPos, finalYPos)
        if(impactedUnit!=null){
            //Log.w("GameState" ,"UNIT IMPACT!?!?!?!?!? $xPos, $yPos")
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
                    //Log.w("GameState" ,"city impact $xPos, $yPos")
                    razeCity(impactedCity)
                }
            }
        }
        unit.xPos = finalXPos
        unit.yPos = finalYPos
        grid.drawGameState()
        if(unit.faction == Faction.HUMAN){
            hasMove.remove(unit)
            if(hasMove.isEmpty()) setTurn(1)//Make AI turn
        }
        return true
    }

    private fun killUnit(unit: Unit){
        if(unit.player==1){
            zombiesKilled++
            currentZombies--
            graves.add(Grave(unit.xPos,unit.yPos,unit.size,unit.faction))
        }else{
            warriorsLost++
            graves.add(Grave(unit.xPos,unit.yPos,unit.size,unit.faction))
        }
        units.remove(unit)
    }
    private fun razeCity(city: City){
        cities.remove(city)
        if(checkFactionEliminated(city.player)){
            gameOver=true
            //(TODO)remove player control/set feral once MP
            grid.endGame((city.player+1)%2)
        }
    }
    private fun checkFactionEliminated(player:Int):Boolean{
        for(city in cities){
            if(city.player==player)return false
        }
        return true
    }

    //intersect
    private fun checkPotentialUnitIntersections(inboundUnit:Unit?, size1: Float, left1: Float, top1: Float):Unit?{
        for(unit in units){
            if(unit == inboundUnit){
                continue
            }
            if(intersect(size1, left1, top1, unit.size, unit.xPos, unit.yPos)) return unit
        }
        return null
    }
    private fun checkPotentialCityIntersections(size1: Float, left1: Float, top1: Float):City?{
        for(city in cities){
            if(intersect(size1, left1, top1, city.size, city.xPos, city.yPos)) return city
        }
        return null
    }
    private fun intersect(size1: Float, left1: Float, top1: Float, size2: Float, left2: Float, top2: Float): Boolean {
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
    fun setTurn(player: Int){
        if(gameOver) return
        hasMove.clear()
        initializeMovement(player)
        if(player==1) {//If is AI
            runAIMovement(player)
        }
        runProduction(player)
        //Log.w("GameState" , "hasMove $hasMove.size")
        if (hasMove.isEmpty()){
            //Log.w("GameState" , "hasMove empty")
            setTurn((player+1)%playerCount)
            //Log.w("GameState" , "Recursive call completed")
        }
        grid.imageV.invalidate()
    }
    private fun initializeMovement(player: Int){
        for (unit in units){
            if(unit.player == player){
                hasMove.add(unit)
            }
        }
    }
    private fun runAIMovement(player: Int){
        for(unit in hasMove){
            if (unit is Unit){
                for( i in 1..10) {
                    var dx = Random.nextFloat() - .5f
                    var dy = Random.nextFloat() - .5f
                    var dist = (dx * dx) + (dy * dy)
                    dist = sqrt(dist)
                    dx = dx * unit.speed / dist
                    dy = dy * unit.speed / dist
                    if(move(unit, unit.xPos + dx, unit.yPos + dy)){
                        break
                    }
                }
            }
        }
        setTurn(0)
    }
    private fun runProduction(player: Int){
        if (player==0){//human production
            for (city in cities){
                if (city.player != player){
                    continue
                }

                if(city.productionProgress == 3){
                    placeUnitOnCity(city)
                    city.productionProgress = 0
                }else city.productionProgress++
            }
        }
        else{//Undead production
            for (city in cities){
                if (city.player != player){
                    continue
                }
                //for(i in 1..2) yPos+(-150f+100f*i) //for doable production
                if(city.productionProgress == 1) {
                    placeUnitOnCity(city)
                    //city.productionProgress = 0
                }else city.productionProgress++
            }
        }
    }
    private fun placeUnitOnCity(city: City){
        if (!createUnit(city.xPos, city.yPos, city.player, city.faction)) {
            createUnitOffCenter(city)
        }
    }
    private fun createUnitOffCenter(city: City){
        val cityLeft = city.xPos-(city.size/2)
        val cityTop = city.yPos-(city.size/2)
        var created = false
        for (x in 0..(city.size.toInt()/5)){
            for (y in 0..(city.size.toInt()/5)){
                if(createUnit(cityLeft+5f*x, cityTop+5f*y, city.player, city.faction)) {
                    created = true
                    break
                }
            }
            if(created) break
        }
    }
    //Creation
    fun createUnit(xPos: Float, yPos: Float, player: Int, faction: Faction):Boolean{
        val unit = Unit(xPos, yPos, player, faction, unitSize)
        if(checkPotentialUnitIntersections(null, unit.size, xPos, yPos)==null) {
            if(unit.faction == Faction.UNDEAD) currentZombies++
            units.add(unit)
            return true
        }
        return false
    }
    fun createCity(xPos: Float, yPos: Float, player: Int, faction: Faction){
        val city = City(xPos, yPos, player, faction, citySize)
        cities.add(city)
    }
}