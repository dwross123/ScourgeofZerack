package dwross123.dayton.scourgeofzerack

import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.FileUtils
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import dwross123.dayton.scourgeofzerack.databinding.ActivityGridBinding
import kotlin.Unit


class Grid : AppCompatActivity() {

    private lateinit var binding: ActivityGridBinding
    lateinit var imageV:ImageView
    val gameState = GameState(2, this)
    lateinit var bit1: Bitmap
    lateinit var bit2: Bitmap
    lateinit var canvas: Canvas
    var width =0
    var height =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGridBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val message = intent.getIntExtra(EXTRA_MESSAGE, -1)

        val displayMetrics = DisplayMetrics()
        windowManager.getDefaultDisplay().getMetrics(displayMetrics)

        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels
        bit1 = BitmapFactory.decodeResource(getResources(), R.drawable.smudge) //size 337x309
        bit2 = BitmapFactory.decodeResource(getResources(), R.drawable.tree) //size 158x158

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
        gameState.createCity(800f, 350f, 0, Faction.ZOMBIE)
        gameState.createUnit(100f, 350f, 0, Faction.HUMAN)
        drawGameState()
    }

/*    fun moveSelected(unit: Unit) {

    }*/

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
            canvas.drawBitmap(bit1, city.xPos, city.yPos, null)
        }
    }
    private fun drawUnits(){
        for (unit in gameState.units){
            canvas.drawBitmap(bit2, unit.xPos, unit.yPos, null)
        }
    }
}