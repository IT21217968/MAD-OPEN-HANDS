package com.example.project_management

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Viewproject : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewproject)

        var addButton = findViewById<Button>(R.id.addbutton)
        addButton.setOnClickListener {
            val Intent = Intent(this,Addproject::class.java)
            startActivity(Intent)
        }

        var updateButton = findViewById<Button>(R.id.update)
        updateButton.setOnClickListener {
            val Intent = Intent(this,Updateproject::class.java)
            startActivity(Intent)
        }


    }
}