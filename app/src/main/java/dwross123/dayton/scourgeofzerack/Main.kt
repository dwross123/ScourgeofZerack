package dwross123.dayton.scourgeofzerack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

const val EXTRA_MESSAGE = "dwross123.dayton.scourgeofzerack.MESSAGE"
class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val new_game = findViewById<Button>(R.id.new_game)
        new_game.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val message = 0
                val intent = Intent(this@Main, Grid::class.java).apply {
                    putExtra(EXTRA_MESSAGE, message)
                }
                startActivity(intent)
            }
        })

        val contine_game = findViewById<Button>(R.id.new_game)
        new_game.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val message = 1
                val intent = Intent(this@Main, Grid::class.java).apply {
                    putExtra(EXTRA_MESSAGE, message)
                }
                startActivity(intent)
            }
        })

        /** Called when the user taps the Send button */
        fun sendMessage(view: View) {
            // Do something in response to button
        }
    }
}