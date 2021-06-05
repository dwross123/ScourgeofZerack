package dwross123.dayton.scourgeofzerack

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import dwross123.dayton.scourgeofzerack.databinding.ActivityGridBinding
import android.widget.Toast
import java.time.LocalDateTime
import java.util.*


class Grid : AppCompatActivity() {

    lateinit var binding: ActivityGridBinding
    lateinit var imageV:ImageView
    val gameState = GameState(2, this)
    lateinit var humanCity: Bitmap
    lateinit var humanWarrior: Bitmap
    lateinit var undeadCity: Bitmap
    lateinit var undeadWarrior: Bitmap
    lateinit var canvas: Canvas
    var width =0
    var height =0
    var selected :Clickable? = null
    var lastClickTime = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGridBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val message = intent.getIntExtra(EXTRA_MESSAGE, -1)

        val displayMetrics = DisplayMetrics()
        windowManager.getDefaultDisplay().getMetrics(displayMetrics)

        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels
        humanCity = BitmapFactory.decodeResource(getResources(), R.drawable.h_city_idle_1) //size 337x309
        humanWarrior = BitmapFactory.decodeResource(getResources(), R.drawable.warrior_idle_1) //size 158x158
        undeadCity = BitmapFactory.decodeResource(getResources(), R.drawable.z_city_idle_1) //size 337x309
        undeadWarrior = BitmapFactory.decodeResource(getResources(), R.drawable.basic_undead_idle1) //size 158x158

        //val file = FileUtils. getFile(this,selectedfileUri)

/*        val leftOffSet1 = 300f
        val topOffSet1 = 300f
        val leftOffSet2 = 350f
        val topOffSet2 = 350f
        canvas.drawBitmap(bit1, leftOffSet1, topOffSet1, null)
        canvas.drawBitmap(bit2, leftOffSet2, topOffSet2, null)*/

        // set bitmap as background to ImageView
        imageV = findViewById<ImageView>(R.id.imageView)
        gameState.createCity(100f, 350f, 0, Faction.HUMAN)
        gameState.createCity(800f, 350f, 1, Faction.UNDEAD)
        gameState.createUnit(100f, 350f, 0, Faction.HUMAN)
        for(i in 1..3){
            for(j in 1..3){
                gameState.createUnit((500f+(i*100)), (150f+(j*100)), 1, Faction.UNDEAD)
            }
        }
        drawGameState()
        gameState.setTurn(0)
    }

/*    fun moveSelected(unit: Unit) {

    }*/
    override fun onTouchEvent(e: MotionEvent): Boolean {
        val timeSinceClick = System.currentTimeMillis()-lastClickTime
        lastClickTime = System.currentTimeMillis()
        if (timeSinceClick< 250){
            return true
        }
        val xPos: Float = e.x
        val yPos: Float = e.y
        if(selected != null && selected is Unit){
            if(gameState.move(selected as Unit, xPos, yPos)) selected = null
            Toast.makeText(applicationContext,"x = $xPos y = $yPos",Toast.LENGTH_SHORT).show()
            return true
        }
        //Toast.makeText(applicationContext,"x = $xPos y = $yPos",Toast.LENGTH_LONG).show()
        var left = e.x-10
        var top = e.y-10
        var right = e.x+10
        var bottom = e.y+10

        // draw oval shape to canvas
        /*var shapeDrawable = ShapeDrawable(OvalShape())
        shapeDrawable.setBounds( left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        shapeDrawable.getPaint().setColor(Color.parseColor("#009191"))
        shapeDrawable.draw(canvas)*/
        selected = gameState.findNearby(xPos, yPos) //Biased towards units
        val thing:Clickable? = selected
        if(thing != null){
            if (thing.player != gameState.playerTurn) selected = null
            if (!gameState.hasMove.contains(thing)) selected = null
        }
        imageV.invalidate()   
        return true
    }
    fun drawGameState(){
        val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
        //drawTerrain()
        drawCities()
        drawUnits()
        imageV.background = BitmapDrawable(getResources(), bitmap)
        imageV.invalidate()
    }
    private fun drawTerrain(){
        //TODO terrain
    }
    private fun drawCities(){
        for (city in gameState.cities){
            val left = city.xPos-(city.size/2f)
            val top = city.yPos-(city.size/2f)
            when (city.faction) {
                Faction.HUMAN -> canvas.drawBitmap(humanCity, left, top, null)
                Faction.UNDEAD -> canvas.drawBitmap(undeadCity, left, top, null)
            }
        }
    }
    private fun drawUnits(){
        for (unit in gameState.units){
            val left = unit.xPos-(unit.size/2f)
            val top = unit.yPos-(unit.size/2f)
            when (unit.faction) {
                Faction.HUMAN -> canvas.drawBitmap(humanWarrior, left, top, null)
                Faction.UNDEAD -> canvas.drawBitmap(undeadWarrior, left, top, null)
            }
        }
    }
    fun endGame(player:Int){
        val intent = Intent(this, Main::class.java).apply {
            var message = if(player==0){
                "Congratulations you won!"
            } else "You have been overrun by the undead!"
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}