package vcmsa.jean.musicplaylistmanagerapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log as log

class DetailedViewScreen : AppCompatActivity() {
    private lateinit var tvResults: TextView
    private lateinit var btnDisplay: Button
    private lateinit var btnAverage: Button
    private lateinit var btnReturn: Button

    // these store the data of the playlist
    private val songTitlesList = ArrayList<String>()
    private val artistNameList = ArrayList<String>()
    private val ratingList = ArrayList<Int>()
    private val commentsList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detailed_view_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //these initialize the views
        tvResults = findViewById(R.id.tvResults)
        btnDisplay = findViewById(R.id.btnDisplay)
        btnAverage = findViewById(R.id.btnAverage)
        btnReturn = findViewById(R.id.btnReturn)

        // retrieve data from the intent on MainActivity
        songTitlesList.addAll(intent.getStringArrayListExtra("songTitles") ?: emptyList())
        artistNameList.addAll(intent.getStringArrayListExtra("artistName") ?: emptyList())
        ratingList.addAll(intent.getIntegerArrayListExtra("rating") ?: emptyList())
        commentsList.addAll(intent.getStringArrayListExtra("comments") ?: emptyList())

        //sets the click listener on the button
        btnDisplay.setOnClickListener {
            displayResults()
            log.d("button click", "button clicked")
        }

        //sets the click listener on the button
        btnAverage.setOnClickListener {
            calculateAverage()
            log.d("button click", "button clicked")
        }

        //sets the click listener on the button
        btnReturn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)//this will return to the main activity
            startActivity(intent)
            log.d("button click", "button clicked")
        }
    } // end of onCreate

    //this will display the results on the screen
    @SuppressLint("SetTextI18n")
    private fun displayResults() {
        if (songTitlesList.isEmpty()) {
            tvResults.text = "There Is No Data To Display."
            return
        }

        val resultsBuilder = StringBuilder() //this will build the results and display them on the screen
        for (i in songTitlesList.indices) {
            resultsBuilder.append("Song Title: ${songTitlesList[i]}\n") // this will get the song title if there is no song title then it will display an empty string
            resultsBuilder.append("Artist Name: ${artistNameList[i]}\n") // this will get the artist name if there is no artist name then it will display an empty string
            resultsBuilder.append("Rating: ${ratingList[i]}\n") // this will get the rating if there is no rating then it will display an empty string
            resultsBuilder.append("Comments: ${commentsList.getOrElse(i) { "" }}\n\n") // this will get the comments if there is no comments then it will display an empty string
        }
        tvResults.text = resultsBuilder.toString()
        //this will display the results on the screen
    }

    // this function will calculate the average of the ratings
    @SuppressLint("SetTextI18n")
    private fun calculateAverage() {
        if (ratingList.isEmpty()) {
            tvResults.text = "There Is No Data To Display."
            return
        }

        var totalRating = 0 //this will store the total rating
        for (rating in ratingList) {//this will get the rating
            totalRating += rating//this will add the rating to the total rating
        }
        //this will calculate the average rating and display it on the screen within one decimal place
        val averageRatingValue = if (ratingList.isNotEmpty()) totalRating.toDouble() / ratingList.size else 0.0
        tvResults.text = "Average Rating: %.1f".format(averageRatingValue)
    }
}