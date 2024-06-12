package com.opsc7311.timewizeapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class TimesheetListActivity : AppCompatActivity() {

    // private lateinit var listViewTimesheetEntries: ListView
    private lateinit var datePickerStart: DatePicker
    private lateinit var datePickerEnd: DatePicker
    private lateinit var txt_StartDate: EditText
    private lateinit var txt_EndDate: EditText
    private val db = FirebaseFirestore.getInstance()
    private var categoriesSpinnerNum: Spinner? = null
    private lateinit var btnStartDate: Button
    private lateinit var btnEndDate: Button
    private lateinit var btnShowHours: Button

    var categoryArrayList: ArrayList<String>? = null
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timesheet_list_activity)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        btnStartDate = findViewById(R.id.btnStartDate)
        btnEndDate = findViewById(R.id.btnEndDate)
        btnShowHours = findViewById(R.id.buttonShowHours)

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

        btnShowHours.setOnClickListener{
            onConfirmDatesButtonClick(it)
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


    fun onConfirmDatesButtonClick(view: View) {
        // Get selected start and end dates
        // val startDate = getDateFromDatePicker(datePickerStart)
        // val endDate = getDateFromDatePicker(datePickerEnd)

        ListHandler.timesheetEntries.clear()

        val db = FirebaseFirestore.getInstance()
        val timesheetRef = db.collection("timesheets")

        timesheetRef.whereEqualTo("category", categoriesSpinnerNum?.selectedItem.toString()).
        get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result) {
                    val date = document.getString("date") ?: ""
                    val startTime = document.getString("startTime") ?: ""
                    val endTime = document.getString("endTime") ?: ""
                    val description = document.getString("description") ?: ""
                    val category = document.getString("category") ?: ""
                    val imageUrl = document.getString("image")

                    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    try {
                        var formattedDate = sdf.parse(date)
                        var formattedStart = sdf.parse(txt_StartDate.text.toString())
                        var formattedEnd = sdf.parse(txt_EndDate.text.toString())

                        val currentEntry = TimesheetEntry(date, startTime, endTime, description, category, imageUrl)

                        if (formattedDate >= formattedStart && formattedDate <= formattedEnd)
                        {
                            ListHandler.timesheetEntries.add(currentEntry)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                val intent = Intent(this, TimesheetRecyclerViewActivity::class.java)
                startActivity(intent)
            } else {
                Log.i("FirebaseDB","Failed to fetch timesheet entries")
            }
        }
    }

    fun onGoToNumberOfHoursButtonClick(view: View?) {
        val intent = Intent(this, NumOfHoursSpentActivity::class.java)
        startActivity(intent)
    }

    private fun getDateFromDatePicker(datePicker: DatePicker): String {
        val day = datePicker.dayOfMonth
        val month = datePicker.month + 1 // Month is zero-based, so add 1
        val year = datePicker.year
        return "$year-$month-$day" // Change the date format as needed
    }

    private fun showToast(message: String) {
        // Implement your toast message display logic here
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