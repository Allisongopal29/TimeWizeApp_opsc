package com.opsc7311.timewizeapp


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar


class Timesheet : AppCompatActivity(), View.OnClickListener  {


    private lateinit var btnDatePicker: Button
    private lateinit var btnStartTimePicker: Button
    private lateinit var btnEndTimePicker: Button
    private lateinit var txtDate: EditText
    private lateinit var txtStartTime: EditText
    private lateinit var txtEndTime: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnAddImage : Button
    private lateinit var btnSave : Button
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0
    private var categoriesSpinner: Spinner? = null
    private var imageUri: Uri? = null
    var categoryArrayList: ArrayList<String>? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        btnDatePicker = findViewById(R.id.btn_date)
        btnStartTimePicker = findViewById(R.id.btn_startTime)
        btnSave = findViewById(R.id.btnSave)
        btnEndTimePicker = findViewById(R.id.btn_endTime)
        txtDate = findViewById(R.id.txt_date)
        txtStartTime = findViewById(R.id.txt_startTime)
        txtEndTime = findViewById(R.id.txt_endTime)
        etDescription = findViewById(R.id.etDescription)
        categoriesSpinner = findViewById(R.id.categoriesSpinner)


        btnDatePicker.setOnClickListener(this)
        btnStartTimePicker.setOnClickListener(this)
        btnEndTimePicker.setOnClickListener(this)


        categoriesSpinner = findViewById<Spinner>(R.id.categoriesSpinner)
        categoryArrayList = ArrayList()
        initializeSpinner()

        btnAddImage = findViewById(R.id.btnAddImage)

        registerResult()
        btnAddImage.setOnClickListener { pickImage() }

        btnSave.setOnClickListener {
            val startTimeText = txtStartTime.text.toString().trim()
            val endTimeText = txtEndTime.text.toString().trim()
            val dateText = txtDate.text.toString().trim()
            val descriptionText = etDescription.text.toString().trim()
           // val imageText = imageUri.toString().trim()
            val spinner = categoriesSpinner
            val categoryText = spinner?.selectedItem.toString()
            if (startTimeText.isNotEmpty() and endTimeText.isNotEmpty() and dateText.isNotEmpty() and descriptionText.isNotEmpty()
                and categoryText.isNotEmpty()) {
                saveTimesheet(startTimeText, endTimeText, dateText, descriptionText, imageUri, categoryText)
                startActivity(Intent(this, Dashboard::class.java))
            } else {
                Toast.makeText(this, "Category name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun saveTimesheet(startTime: String, endTime: String, date: String, description: String, image: Uri?, category: String) {
        val timesheetData = hashMapOf(
            "startTime" to startTime,
            "endTime" to endTime,
            "date" to date,
            "description" to description,
            "image" to image,
            "category" to category
        )

        db.collection("timesheets")
            .add(timesheetData)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Category saved successfully", Toast.LENGTH_SHORT).show()
                txtDate.setText("")
                txtStartTime.setText("")
                txtEndTime.setText("")
                txtDate.setText("")
                etDescription.setText("")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding timesheet", e)
            }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    private fun registerResult() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            try {
                imageUri = result.data?.data
               // imageView.setImageURI(imageUri)
            } catch (e: Exception) {

            }
        }
    }

    override fun onClick(v: View) {
        when (v) {
            btnDatePicker -> {
                // Get Current Date
                val c = Calendar.getInstance()
                mYear = c.get(Calendar.YEAR)
                mMonth = c.get(Calendar.MONTH)
                mDay = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                        txtDate.setText("$dayOfMonth-${monthOfYear + 1}-$year")
                    },
                    mYear, mMonth, mDay
                )
                datePickerDialog.show()
            }
            btnStartTimePicker -> {
                // Get Current Time
                val c = Calendar.getInstance()
                mHour = c.get(Calendar.HOUR_OF_DAY)
                mMinute = c.get(Calendar.MINUTE)

                // Launch Time Picker Dialog
                val timePickerDialog = TimePickerDialog(
                    this,
                    TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDay: Int, minute: Int ->
                        txtStartTime.setText("$hourOfDay:$minute")
                    },
                    mHour, mMinute, false
                )
                timePickerDialog.show()
            }
            btnEndTimePicker -> {
                // Get Current Time
                val c = Calendar.getInstance()
                mHour = c.get(Calendar.HOUR_OF_DAY)
                mMinute = c.get(Calendar.MINUTE)

                // Launch Time Picker Dialog
                val timePickerDialog = TimePickerDialog(
                    this,
                    TimePickerDialog.OnTimeSetListener { view: TimePicker, hourOfDay: Int, minute: Int ->
                        txtEndTime.setText("$hourOfDay:$minute")
                    },
                    mHour, mMinute, false
                )
                timePickerDialog.show()
            }
        }
    }

    private fun initializeSpinner() {
        val db = FirebaseFirestore.getInstance()
        val categoriesRef = db.collection("categories")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoriesSpinner?.adapter = adapter
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
