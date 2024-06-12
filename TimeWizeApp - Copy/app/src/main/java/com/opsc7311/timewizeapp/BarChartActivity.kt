package com.opsc7311.timewizeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.opsc7311.timewizeapp.databinding.ActivityBarChartBinding
import java.text.SimpleDateFormat
import java.util.Locale

class BarChartActivity : AppCompatActivity() {

    private var _binding: ActivityBarChartBinding? = null
    private lateinit var db: FirebaseFirestore
    private val binding get() = _binding!!

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBarChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Fetch data from Firestore
        fetchDataFromFirestore()
    }

    private fun fetchDataFromFirestore() {
        // Fetch data from Firestore hoursData collection
        db.collection("hoursData")
            .get()
            .addOnSuccessListener { hoursDataDocuments ->
                val dataList = mutableListOf<Triple<String, Float, Float>>()
                for (document in hoursDataDocuments) {
                    val totalHours = document.getDouble("totalHours")?.toFloat() ?: 0f
                    val minGoal = document.getString("minGoal")?.toFloatOrNull() ?: 0f
                    val maxGoal = document.getString("maxGoal")?.toFloatOrNull() ?: 0f
                    dataList.add(Triple("Total Hours", totalHours, 0f))
                    dataList.add(Triple("Min Goal", minGoal, 0f))
                    dataList.add(Triple("Max Goal", maxGoal, 0f))
                }

                updateChart(dataList)
            }
            .addOnFailureListener { exception ->
                // Handle failure
                println("Error getting documents: $exception")
            }
    }

    private fun updateChart(data: List<Triple<String, Float, Float>>) {
        val chartData = data.map { Pair(it.first, it.second) }
        binding.barchartid.apply {
            animation.duration = animationDuration
            animate(chartData)
        }
    }

    companion object {
        private const val animationDuration = 1000L
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
