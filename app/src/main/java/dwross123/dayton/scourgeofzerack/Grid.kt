package dwross123.dayton.scourgeofzerack

import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.FileUtils
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dwross123.dayton.scourgeofzerack.databinding.ActivityGridBinding


class Grid : AppCompatActivity() {

    private lateinit var binding: ActivityGridBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGridBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val message = intent.getIntExtra(EXTRA_MESSAGE, -1)

        val displayMetrics = DisplayMetrics()
        windowManager.getDefaultDisplay().getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels
        val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas: Canvas = Canvas(bitmap)

        //val file = FileUtils. getFile(this,selectedfileUri)
        val bit1 = BitmapFactory.decodeResource(getResources(), R.drawable.smudge)
        val bit2 = BitmapFactory.decodeResource(getResources(), R.drawable.tree)
        val leftOffSet1 = 300f
        val topOffSet1 = 300f
        val leftOffSet2 = 350f
        val topOffSet2 = 350f
        canvas.drawBitmap(bit1, leftOffSet1, topOffSet1, null)
        canvas.drawBitmap(bit2, leftOffSet2, topOffSet2, null)

        // set bitmap as background to ImageView
        val imageV = findViewById<View>(R.id.imageView)
        imageV.background = BitmapDrawable(getResources(), bitmap)
    }
}