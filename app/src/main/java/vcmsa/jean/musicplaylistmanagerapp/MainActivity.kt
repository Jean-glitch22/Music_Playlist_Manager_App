package vcmsa.jean.musicplaylistmanagerapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }//code starts here

        val songTitles = arrayListOf<String>()
        val artistName = arrayListOf<String>()
        val rating = arrayListOf<Int>()
        val comments = arrayListOf<String>()

        val btnAddToPlaylist = findViewById<Button>(R.id.btnAddToPlaylist)
        val edtSongTitles = findViewById<EditText>(R.id.edtSongTitles)
        val edtArtistName = findViewById<EditText>(R.id.edtArtistName)
        val edtRating = findViewById<EditText>(R.id.edtRating)
        val edtComments = findViewById<EditText>(R.id.edtComments)
        val btnNextScreen = findViewById<Button>(R.id.btnNextScreen)
        val btnExitApp = findViewById<Button>(R.id.btnExitApp)
        }


            }

