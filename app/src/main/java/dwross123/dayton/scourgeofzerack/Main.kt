package dwross123.dayton.scourgeofzerack

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

const val EXTRA_MESSAGE = "dwross123.dayton.scourgeofzerack.MESSAGE"
class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val victoryMessage = intent.getStringExtra(EXTRA_MESSAGE)
        if(!victoryMessage.isNullOrEmpty()){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Game Over")
            builder.setMessage(victoryMessage)
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(
                    applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT
                ).show()
            }

            /*    builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(applicationContext,
                        android.R.string.no, Toast.LENGTH_SHORT).show()
                }

                builder.setNeutralButton("Maybe") { dialog, which ->
                    Toast.makeText(applicationContext,
                        "Maybe", Toast.LENGTH_SHORT).show()
                }*/
            builder.show()
        }

        val newGame = findViewById<Button>(R.id.new_game)
        newGame.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val message = 0
                val intent = Intent(this@Main, Grid::class.java).apply {
                    putExtra(EXTRA_MESSAGE, message)
                }
                startActivity(intent)
            }
        })

/*        val contine_game = findViewById<Button>(R.id.new_game)
        new_game.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val message = 1
                val intent = Intent(this@Main, Grid::class.java).apply {
                    putExtra(EXTRA_MESSAGE, message)
                }
                startActivity(intent)
            }
        })*/
    }
}