package com.opsc7311.timewizeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.firestore.FirebaseFirestore
import com.opsc7311.timewizeapp.R.id
import com.opsc7311.timewizeapp.R.layout

class Category : AppCompatActivity() {

    private lateinit var categoryName: EditText
    private lateinit var saveCategoryButton: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_category)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        categoryName = findViewById(id.categoryName)
        saveCategoryButton = findViewById(id.saveCategoryButton)

        saveCategoryButton.setOnClickListener {
            val categoryNameText = categoryName.text.toString().trim()
            if (categoryNameText.isNotEmpty()) {
                saveCategory(categoryNameText)
                startActivity(Intent(this, Dashboard::class.java))

            } else {
                Toast.makeText(this, "Category name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveCategory(name: String) {
        val category = hashMapOf("name" to name)
        db.collection("categories")
            .add(category)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Category saved successfully", Toast.LENGTH_SHORT).show()
                categoryName.setText("")
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to save category: $exception", Toast.LENGTH_SHORT).show()
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
