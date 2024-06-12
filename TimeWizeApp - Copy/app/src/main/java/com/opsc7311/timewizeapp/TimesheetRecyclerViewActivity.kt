package com.opsc7311.timewizeapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Time

class TimesheetRecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet_recycler_view)

        val recyclerview = findViewById<RecyclerView>(R.id.TimesheetRecyclerView)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val adapter = CustomAdapter(ListHandler.timesheetEntries)

        recyclerview.adapter = adapter
    }
}