package vcmsa.jean.musicplaylistmanagerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailedViewScreen : AppCompatActivity() {
    private lateinit var tvResults: TextView
    private lateinit var btnDisplay: Button
    private lateinit var btnAverage: Button
    private lateinit var btnReturn: Button

    // Keep these as class properties to store the data received from the Intent
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

        tvResults = findViewById(R.id.tvResults)
        btnDisplay = findViewById(R.id.btnDisplay)
        btnAverage = findViewById(R.id.btnAverage)
        btnReturn = findViewById(R.id.btnReturn)

        // Retrieve data from Intent and populate the lists
        // Using ?: emptyList() is a good safe way to handle potential nulls
        songTitlesList.addAll(intent.getStringArrayListExtra("songTitles") ?: emptyList())
        artistNameList.addAll(intent.getStringArrayListExtra("artistName") ?: emptyList())
        ratingList.addAll(intent.getIntegerArrayListExtra("rating") ?: emptyList())
        commentsList.addAll(intent.getStringArrayListExtra("comments") ?: emptyList())

        btnDisplay.setOnClickListener {
            displayResults()
        }

        btnAverage.setOnClickListener {
            calculateAverage()
        }

        btnReturn.setOnClickListener {
            // Intent to go back to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            // Optional: If you want MainActivity to be a fresh instance and clear others above it
            // intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            // finish() // Optional: Call finish if you want DetailedViewScreen to be removed from back stack
        }
    } // End of onCreate

    // Function to display all song details
    private fun displayResults() {
        if (songTitlesList.isEmpty()) {
            tvResults.text = "No songs in the playlist."
            return
        }

        val resultsBuilder = StringBuilder() // More efficient for building strings in a loop
        for (i in songTitlesList.indices) {
            resultsBuilder.append("Song Title: ${songTitlesList[i]}\n")
            resultsBuilder.append("Artist Name: ${artistNameList[i]}\n")
            resultsBuilder.append("Rating: ${ratingList[i]}\n")
            resultsBuilder.append("Comments: ${commentsList.getOrElse(i) { "" }}\n\n") // Added newline for spacing
        }
        tvResults.text = resultsBuilder.toString()
    }

    // Function to calculate and display the average rating
    private fun calculateAverage() {
        if (ratingList.isEmpty()) {
            tvResults.text = "No ratings available to calculate average."
            return
        }

        var totalRating = 0
        for (rating in ratingList) {
            totalRating += rating
        }
        // Use toDouble() for division to get a floating-point average, then format
        val averageRatingValue = if (ratingList.isNotEmpty()) totalRating.toDouble() / ratingList.size else 0.0
        tvResults.text = "Average Rating: %.1f".format(averageRatingValue) // Format to one decimal place
    }
}