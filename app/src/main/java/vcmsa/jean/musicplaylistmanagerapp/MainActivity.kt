package vcmsa.jean.musicplaylistmanagerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // these store the data of the playlist
    private val songTitlesList = ArrayList<String>()
    private val artistNameList = ArrayList<String>()
    private val ratingList = ArrayList<Int>()
    private val commentsList = ArrayList<String>()

    // Declare lateinit properties for your views
    private lateinit var edtSongTitles: EditText
    private lateinit var edtArtistName: EditText
    private lateinit var edtRating: EditText
    private lateinit var edtComments: EditText
    private lateinit var btnAddToPlaylist: Button
    private lateinit var btnNextScreen: Button
    private lateinit var btnExitApp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize your lateinit properties here
        edtSongTitles = findViewById(R.id.edtSongTitles)
        edtArtistName = findViewById(R.id.edtArtistName)
        edtRating = findViewById(R.id.edtRating)
        edtComments = findViewById(R.id.edtComments)
        btnAddToPlaylist = findViewById(R.id.btnAddToPlaylist)
        btnNextScreen = findViewById(R.id.btnNextScreen)
        btnExitApp = findViewById(R.id.btnExitApp)

        // Set click listeners inside onCreate
        btnAddToPlaylist.setOnClickListener {
            addSong()
        }

        btnNextScreen.setOnClickListener {
            val intent = Intent(this, DetailedViewScreen::class.java)
            intent.putStringArrayListExtra("songTitles", songTitlesList)
            intent.putStringArrayListExtra("artistName", artistNameList)
            intent.putIntegerArrayListExtra("rating", ratingList) // Use putIntegerArrayListExtra for ArrayList<Int>
            intent.putStringArrayListExtra("comments", commentsList)
            startActivity(intent)
        }

        btnExitApp.setOnClickListener {
            finishAffinity() // Closes all activities in the task
        }
    } // End of onCreate

    private fun addSong() { // Made private as it's only used within this class
        val songTitleText = edtSongTitles.text.toString().trim() // Use .trim()
        val artistNameText = edtArtistName.text.toString().trim() // Use .trim()
        val ratingText = edtRating.text.toString().trim()       // Use .trim()
        val commentsText = edtComments.text.toString().trim()     // Use .trim()

        if (songTitleText.isNotEmpty() && artistNameText.isNotEmpty() && ratingText.isNotEmpty()) {
            // Validate rating format (should be an integer)
            val ratingValue = ratingText.toIntOrNull() // Safely convert to Int or null
            if (ratingValue == null || ratingValue !in 1..5) { // Assuming 1-5 rating
                Toast.makeText(this, "Please enter a valid rating (1-5)", Toast.LENGTH_SHORT).show()
                edtRating.requestFocus() // Optionally focus the rating field
                return // Stop further execution if rating is invalid
            }

            songTitlesList.add(songTitleText)
            artistNameList.add(artistNameText)
            ratingList.add(ratingValue) // Add the validated integer rating
            commentsList.add(commentsText) // Comments can be empty if you allow it

            Toast.makeText(this, "Song added to playlist", Toast.LENGTH_SHORT).show()

            edtSongTitles.text.clear()
            edtArtistName.text.clear()
            edtRating.text.clear()
            edtComments.text.clear()
            edtSongTitles.requestFocus() // Optionally focus the first field for next entry
        } else {
            Toast.makeText(this, "Please enter song title, artist name, and rating", Toast.LENGTH_SHORT).show()
            // You could add logic here to highlight which specific field is missing
            if (songTitleText.isEmpty()) edtSongTitles.error = "Required"
            if (artistNameText.isEmpty()) edtArtistName.error = "Required"
            if (ratingText.isEmpty()) edtRating.error = "Required"
        }
    }
}



