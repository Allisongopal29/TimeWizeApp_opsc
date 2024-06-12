package com.opsc7311.timewizeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class Goals : AppCompatActivity() {

    private lateinit var btnSave : Button
    private lateinit var etMinGoal : EditText
    private lateinit var etMaxGoal : EditText
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goals)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSave = findViewById(R.id.btnSave)
        etMinGoal = findViewById(R.id.txtMinGoal)
        etMaxGoal = findViewById(R.id.txtMaxGoal)
        btnSave.setOnClickListener(){
            val minGoalText = etMinGoal.text.toString().trim()
            val maxGoalText = etMaxGoal.text.toString().trim()
            if (minGoalText.isNotEmpty() and maxGoalText.isNotEmpty()) {
                saveCategory(minGoalText, maxGoalText)
                startActivity(Intent(this, Dashboard::class.java))

            } else {
                Toast.makeText(this, "Category name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveCategory(minGoal: String, maxGoal: String) {
        val goals = hashMapOf("minGoal" to minGoal, "maxGoal" to maxGoal)
        db.collection("goals")
            .add(goals)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Goals saved successfully", Toast.LENGTH_SHORT).show()
                etMinGoal.setText("")
                etMaxGoal.setText("")
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
                Toast.makeText(applicationContext, "Shows share icon", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Dashboard::class.java))
                return true
            }
            R.id.goals -> {
                Toast.makeText(applicationContext, "Shows share icon", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Goals::class.java))
                return true
            }
            R.id.viewTimesheets -> {
                Toast.makeText(applicationContext, "Shows share icon", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, TimesheetListActivity::class.java))
                return true
            }
            R.id.catagoryHours -> {
                Toast.makeText(applicationContext, "Shows share icon", Toast.LENGTH_SHORT).show()
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