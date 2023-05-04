package com.example.project_management

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)

        val appBarConfig = AppBarConfiguration(setOf(R.id.home_fragment,R.id.goal_fragment,R.id.notification_fragment,R.id.myactivity_fragment))
        setupActionBarWithNavController(navController,appBarConfig)
        bottomNavigationView.setupWithNavController(navController)

    }
}