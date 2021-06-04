package dwross123.dayton.scourgeofzerack

import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.os.FileUtils
import android.view.View
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
/*import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode.Companion.SrcOver
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.DrawContext
import androidx.compose.ui.graphics.drawscope.Fill*/
import dwross123.dayton.scourgeofzerack.databinding.ActivityGridBinding
//import androidx.compose.ui.graphics.drawscope.CanvasDrawScope


class Grid : AppCompatActivity() {

    private lateinit var binding: ActivityGridBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGridBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getIntExtra(EXTRA_MESSAGE, -1)

        // Capture the layout's TextView and set the string as its text



        /*setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        val bitmap: Bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
        val canvas: Canvas = Canvas(bitmap)

        var shapeDrawable: ShapeDrawable

        // rectangle positions
        var left = 100
        var top = 100
        var right = 600
        var bottom = 400

        // draw rectangle shape to canvas
        shapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds( left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.parseColor("#009944"))
        shapeDrawable.draw(canvas)

        // oval positions
        left = 100
        top = 500
        right = 600
        bottom = 800

        // draw oval shape to canvas
        shapeDrawable = ShapeDrawable(OvalShape())
        shapeDrawable.setBounds( left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.parseColor("#009191"))
        shapeDrawable.draw(canvas)

        // now bitmap holds the updated pixels

        //val file = FileUtils. getFile(this,selectedfileUri)
        val bit1 = BitmapFactory.decodeResource(getResources(), R.drawable.tree)
        val bit2 = BitmapFactory.decodeResource(getResources(), R.drawable.smudge)
        //val bit1 = sad
        val leftOffSet1 = 300f
        val topOffSet1 = 300f
        val leftOffSet2 = 600f
        val topOffSet2 = 600f
        canvas.drawBitmap(bit1, leftOffSet1, topOffSet1, null)
        canvas.drawBitmap(bit2, leftOffSet2, topOffSet2, null)

        // set bitmap as background to ImageView
        val imageV = findViewById<View>(R.id.imageView)
        imageV.background = BitmapDrawable(getResources(), bitmap)



        /* unfinished beta library problems
        val topLeftOffset = Offset(50f, 100f)
        val drawCont = DrawContext()
        val canDraw = CanvasDrawScope()
        val halfGhost = .5f //half transparent
        canDraw.drawImage(
            bitmap.asImageBitmap(),
            topLeftOffset,
            halfGhost,
            Fill,
            null,
            SrcOver
        )*/
        //git is fussy
    }
}