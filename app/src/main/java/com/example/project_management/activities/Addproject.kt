package com.example.project_management.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.project_management.models.ProjectModel
import com.example.project_management.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Addproject : AppCompatActivity() {

    //initialize views
    private lateinit var etTitle:EditText
    private lateinit var etDescription:EditText
    private lateinit var etProjectgoal:EditText
    private lateinit var etDuration:EditText
    private lateinit var etStatus:EditText
    private lateinit var etOrganizer:EditText
    private lateinit var etCategory:EditText
    private lateinit var etAddbeneficiaries:EditText
    private lateinit var btnSave:Button
    private lateinit var btnView:Button

    //database references
    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addproject)

        var viewButton = findViewById<Button>(R.id.pro_viewbutton)
        viewButton.setOnClickListener {
            val Intent = Intent(this, Viewproject::class.java)
            startActivity(Intent)
        }

        //initialize
        etTitle =findViewById(R.id.title)
        etDescription =findViewById(R.id.description)
        etProjectgoal=findViewById(R.id.projectgoal)
        etDuration =findViewById(R.id.duration)
        etStatus =findViewById(R.id.status)
        etOrganizer =findViewById(R.id.organizer)
        etCategory =findViewById(R.id.category)
        etAddbeneficiaries =findViewById(R.id.addbeneficiaries)
        btnSave=findViewById(R.id.pro_savebutton)
        btnView= findViewById(R.id.pro_viewbutton)

        dbRef =FirebaseDatabase.getInstance().getReference("project-management")

        btnSave.setOnClickListener {
           saveProjectData()
        }

    }

    private fun saveProjectData(){
        //getting values
        val pro_title = etTitle.text.toString()
        val pro_description = etDescription.text.toString()
        val pro_projectgoal = etProjectgoal.text.toString()
        val pro_duration = etDuration.text.toString()
        val pro_status = etStatus.text.toString()
        val pro_organizer = etOrganizer.text.toString()
        val pro_category = etCategory.text.toString()
        val pro_addbeneficiaries  = etAddbeneficiaries.text.toString()

        if(pro_title.isEmpty()){
            etTitle.error="Please enter title"
        }
        if(pro_description.isEmpty()){
            etDescription.error="Please enter description"
        }
        if(pro_projectgoal.isEmpty()){
            etProjectgoal.error="Please enter project goal"
        }
        if(pro_duration.isEmpty()){
            etDuration.error="Please enter time duration"
        }
        if(pro_status.isEmpty()){
            etStatus.error="Please enter status"
        }
        if(pro_organizer.isEmpty()){
            etOrganizer.error="Please enter organizer"
        }
        if(pro_category.isEmpty()){
            etCategory.error="Please enter project category"
        }
        if(pro_addbeneficiaries.isEmpty()){
            etAddbeneficiaries.error="Please enter beneficiaries"
        }

        val projectId = dbRef.push().key!!

        val project = ProjectModel(projectId,pro_title,pro_description,pro_projectgoal,pro_duration,pro_status,pro_organizer,pro_category,pro_addbeneficiaries)

        dbRef.child(projectId).setValue(project)
            .addOnCompleteListener{
                Toast.makeText(this,"Data insert successfully",Toast.LENGTH_LONG).show()

                etTitle.text.clear()
                etDescription.text.clear()
                etProjectgoal.text.clear()
                etDuration.text.clear()
                etStatus.text.clear()
                etOrganizer.text.clear()
                etCategory.text.clear()
                etAddbeneficiaries.text.clear()


            }.addOnFailureListener{ err->
                Toast.makeText(this,"Error${err.message}",Toast.LENGTH_LONG).show()
            }


    }
}