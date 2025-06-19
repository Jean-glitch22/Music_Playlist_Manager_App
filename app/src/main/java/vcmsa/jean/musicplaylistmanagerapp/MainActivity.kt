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

    // these are the views
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

        //these initialize the views
        edtSongTitles = findViewById(R.id.edtSongTitles)
        edtArtistName = findViewById(R.id.edtArtistName)
        edtRating = findViewById(R.id.edtRating)
        edtComments = findViewById(R.id.edtComments)
        btnAddToPlaylist = findViewById(R.id.btnAddToPlaylist)
        btnNextScreen = findViewById(R.id.btnNextScreen)
        btnExitApp = findViewById(R.id.btnExitApp)

        // sets a click listener on the button
        btnAddToPlaylist.setOnClickListener {
            addSong()
        }

        btnNextScreen.setOnClickListener {
            val intent = Intent(this, DetailedViewScreen::class.java)
            intent.putStringArrayListExtra("songTitles", songTitlesList)
            intent.putStringArrayListExtra("artistName", artistNameList)
            intent.putIntegerArrayListExtra("rating", ratingList) //using and int array here for the rating
            intent.putStringArrayListExtra("comments", commentsList)
            startActivity(intent)
        }

        btnExitApp.setOnClickListener {
            finishAffinity()
            //this will close the app
        }
    } // end of onCreate

    private fun addSong() { // this function will add the song to the playlist
        val songTitleText = edtSongTitles.text.toString().trim()
        val artistNameText = edtArtistName.text.toString().trim()
        val ratingText = edtRating.text.toString().trim()
        val commentsText = edtComments.text.toString().trim()  //these will get the text from the edit text

        if (songTitleText.isNotEmpty() && artistNameText.isNotEmpty() && ratingText.isNotEmpty() && commentsText.isNotEmpty()) { // these check if the edit text is empty
            //here we validate the rating
            val ratingValue = ratingText.toIntOrNull()
            if (ratingValue == null || ratingValue !in 1..5) { //if the rating is not between 1 and 5 then it will show an error message
                Toast.makeText(this, "Enter A Rating Of (1-5)", Toast.LENGTH_SHORT).show()
                edtRating.requestFocus()
                return
            }

            songTitlesList.add(songTitleText)
            artistNameList.add(artistNameText)
            ratingList.add(ratingValue) // adds the valid rating to the list
            commentsList.add(commentsText)

            Toast.makeText(this, "Playlist Updated", Toast.LENGTH_SHORT).show() // this will show a toast message that the playlist has been updated

            edtSongTitles.text.clear() // these will clear the edit text so that more can be added to the playlist
            edtArtistName.text.clear()
            edtRating.text.clear()
            edtComments.text.clear()
            edtSongTitles.requestFocus()
        } else {
            Toast.makeText(this, "Please Enter The Details For All Fields", Toast.LENGTH_SHORT).show()// this will show a toast message that the playlist has not been updated
            if (songTitleText.isEmpty()) edtSongTitles.error = "Needed"// these will show an error message if the edit text is empty
            if (artistNameText.isEmpty()) edtArtistName.error = "Needed"
            if (ratingText.isEmpty()) edtRating.error = "Needed"
            if (commentsText.isEmpty()) edtComments.error = "Needed"
        }
    }
}



