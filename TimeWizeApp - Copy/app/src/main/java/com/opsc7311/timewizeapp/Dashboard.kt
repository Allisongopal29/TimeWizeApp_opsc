package com.opsc7311.timewizeapp


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore


class Dashboard : AppCompatActivity() {

    private var categoriesList: ListView? = null
    var categoryArrayList: ArrayList<String>? = null
    private lateinit var btnCategory: Button
    private lateinit var btnTimesheet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val calendar = findViewById<CalendarView>(R.id.calendar)
        btnCategory = findViewById(R.id.btnCatagory)
        btnTimesheet = findViewById(R.id.btnTimesheet)

        btnCategory.setOnClickListener {
            startActivity(Intent(this, Category::class.java))
        }

        btnTimesheet.setOnClickListener {
            startActivity(Intent(this, Timesheet::class.java))
        }


        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "$dayOfMonth-${month + 1}-$year"

        }

        categoriesList = findViewById<ListView>(R.id.idCategoriesList)

        categoryArrayList = ArrayList()

        initializeListView()
    }


    companion object {
        private const val TAG = "Time Wize App"
    }


    private fun initializeListView() {
        val db = FirebaseFirestore.getInstance()

        val categoryArrayList = ArrayList<String>()

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryArrayList)
        categoriesList!!.adapter = adapter

        db.collection("categories")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val categoryName = document.getString("name")
                    categoryName?.let {
                        categoryArrayList.add(it)
                    }
                }

                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
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




