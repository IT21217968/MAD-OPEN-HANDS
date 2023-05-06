package com.example.project_management

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Viewdonation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewdonation)

        var updateDonButton = findViewById<Button>(R.id.don_update)
        updateDonButton.setOnClickListener {
            val Intent = Intent(this,UpdateDonation::class.java)
            startActivity(Intent)
        }
    }
}