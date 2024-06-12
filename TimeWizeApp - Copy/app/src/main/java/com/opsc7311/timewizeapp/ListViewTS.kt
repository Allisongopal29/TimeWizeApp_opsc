package com.opsc7311.timewizeapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ListViewTS : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_ts)
        val listView = findViewById<ListView>(R.id.listView)

        // Retrieve project list data from intent
        val projectList = intent.getStringArrayListExtra("projectList")

        if (projectList != null) {
            // Display project list in ListView
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, projectList)
            listView.adapter = adapter
        } else {
            // Show a toast message indicating that the project list is empty
            showToast("Project list is empty.")
        }





    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}