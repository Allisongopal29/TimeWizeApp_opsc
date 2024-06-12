package com.opsc7311.timewizeapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class NumOfHoursSpentActivity : AppCompatActivity() {

    private lateinit var datePickerStart: DatePicker
    private lateinit var datePickerEnd: DatePicker
    private lateinit var textViewHoursSpent: TextView
    private lateinit var txt_StartDate: EditText
    private lateinit var txt_EndDate: EditText
    private val db = FirebaseFirestore.getInstance()
    private var categoriesSpinnerNum: Spinner? = null
    private lateinit var btnStartDate: Button
    private lateinit var btnEndDate: Button
    var categoryArrayList: ArrayList<String>? = null
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_num_of_hours_spent)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        btnStartDate = findViewById(R.id.btnStartDate)
        btnEndDate = findViewById(R.id.btnEndDate)
        txt_StartDate = findViewById(R.id.txt_StartDate)
        txt_EndDate = findViewById(R.id.txt_EndDate)

        categoriesSpinnerNum = findViewById<Spinner>(R.id.categoriesSpinnerNum)
        categoryArrayList = ArrayList()
        initializeSpinner()

        btnStartDate.setOnClickListener{
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    txt_StartDate.setText("$dayOfMonth-${monthOfYear + 1}-$year")
                },
                mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }
        btnEndDate.setOnClickListener{
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    txt_EndDate.setText("$dayOfMonth-${monthOfYear + 1}-$year")
                },
                mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

        val buttonShowHours = findViewById<Button>(R.id.buttonShowHours)
        textViewHoursSpent = findViewById(R.id.textViewHoursSpent)

        buttonShowHours.setOnClickListener {
            val startDate = txt_StartDate.text.toString()
            val endDate = txt_EndDate.text.toString()
            val category = categoriesSpinnerNum?.selectedItem.toString()

            calculateTotalHours(startDate, endDate, category)
        }
    }

    private fun initializeSpinner() {
        val db = FirebaseFirestore.getInstance()
        val categoriesRef = db.collection("categories")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoriesSpinnerNum?.adapter = adapter
        categoriesRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result) {
                    val subject = document.getString("name")
                    if (subject != null) {
                        adapter.add(subject)
                    }
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun calculateTotalHours(startDate: String, endDate: String, category: String) {
        val db = FirebaseFirestore.getInstance()
        val timesheetsRef = db.collection("timesheets")

        timesheetsRef.whereEqualTo("category", category)
            .get()
            .addOnSuccessListener { documents ->
                var totalHours = 0L
                var dateText = "Dates: \n"
                for (document in documents) {
                    val startTime = document.getString("startTime") ?: continue
                    val endTime = document.getString("endTime") ?: continue
                    val hours = calculateHours(startTime, endTime)
                    val date = document.getString("date")

                    // Check if the date falls within the specified range
                    if (date != null && date >= startDate && date <= endDate) {
                        totalHours += hours
                    }
                }

                textViewHoursSpent.text = "Total Hours Spent: $totalHours hours"
            }
            .addOnFailureListener { exception ->
                textViewHoursSpent.text = "Total Hours Spent: 0 hours"
            }
    }



    fun calculateHours(startTime: String, endTime: String): Long {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        try {
            val startDate = sdf.parse(startTime)
            val endDate = sdf.parse(endTime)
            if (startDate != null && endDate != null) {
                val diffInMillis = endDate.time - startDate.time
                return TimeUnit.MILLISECONDS.toHours(diffInMillis)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dashboard -> {
                startActivity(Intent(this, Dashboard::class.java))
                return true
            }
            R.id.goals -> {
                startActivity(Intent(this, Goals::class.java))
                return true
            }
            R.id.viewTimesheets -> {
                startActivity(Intent(this, TimesheetListActivity::class.java))
                return true
            }
            R.id.catagoryHours -> {
                startActivity(Intent(this, NumOfHoursSpentActivity::class.java))
                return true
            }
            R.id.exit -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
