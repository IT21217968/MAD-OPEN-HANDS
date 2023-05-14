package com.example.project_management.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.project_management.R
import com.example.project_management.models.ProjectModel
import com.google.firebase.database.FirebaseDatabase

class ProjectDetailsActivity : AppCompatActivity() {

    private lateinit var tvProID: TextView
    private lateinit var tvProTitle: TextView
    private lateinit var tvProdes: TextView
    private lateinit var tvProGoal: TextView
    private lateinit var tvProDu: TextView
    private lateinit var tvProStatus: TextView
    private lateinit var tvProOrg: TextView
    private lateinit var tvProCate: TextView
    private lateinit var tvProBen: TextView

    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("projectID").toString(),
                intent.getStringExtra("ProjectTitle").toString()
            )
        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("projectID").toString()
            )
        }

        }


        private fun initView() {
            tvProID = findViewById(R.id.tvProjectId)
            tvProTitle = findViewById(R.id.tvProTitle)
            tvProdes = findViewById(R.id.tvProDes)
            tvProGoal = findViewById(R.id.tvProGoal)
            tvProDu = findViewById(R.id.tvProDu)
            tvProStatus = findViewById(R.id.tvProStatus)
            tvProOrg = findViewById(R.id.tvProOr)
            tvProCate = findViewById(R.id.tvProCategory)
            tvProBen = findViewById(R.id.tvProBen)

            btnUpdate = findViewById(R.id.btnUpdate)
            btnDelete = findViewById(R.id.btnDelete)
        }

        private fun setValuesToViews() {
            tvProID.text = intent.getStringExtra("projectID")
            tvProTitle.text = intent.getStringExtra("ProjectTitle")
            tvProdes.text = intent.getStringExtra("projectDescription")
            tvProGoal.text = intent.getStringExtra("projectGoal")
            tvProDu.text = intent.getStringExtra("projectDuration")
            tvProStatus.text = intent.getStringExtra("projectStatus")
            tvProOrg.text = intent.getStringExtra("projectOrganizer")
            tvProCate.text = intent.getStringExtra("projectCategory")
            tvProBen.text = intent.getStringExtra("projectBeneficiaries")


        }

        private fun deleteRecord(
            id: String
        ){
            val dbRef = FirebaseDatabase.getInstance().getReference("project-management").child(id)
            val mTask = dbRef.removeValue()

            mTask.addOnSuccessListener {
                Toast.makeText(this, "Project data deleted", Toast.LENGTH_LONG).show()

                val intent = Intent(this, Viewproject::class.java)
                finish()
                startActivity(intent)
            }.addOnFailureListener{ error ->
                Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }


    private fun openUpdateDialog(
        projectID: String,
        projectTitle: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_updateproject, null)

        mDialog.setView(mDialogView)

        val etProjectTitle = mDialogView.findViewById<EditText>(R.id.up_title)
        val etDescription = mDialogView.findViewById<EditText>(R.id.up_description)
        val etGoal = mDialogView.findViewById<EditText>(R.id.up_projectgoal)
        val etDuration = mDialogView.findViewById<EditText>(R.id.up_duration)
        val etStatus = mDialogView.findViewById<EditText>(R.id.up_status)
        val etOrganizer = mDialogView.findViewById<EditText>(R.id.up_organizer)
        val etCategory = mDialogView.findViewById<EditText>(R.id.up_category)
        val etAddB = mDialogView.findViewById<EditText>(R.id.up_addbeneficiaries)


        val btnUpdateData = mDialogView.findViewById<Button>(R.id.up_pro_savebutton)

        etProjectTitle.setText(intent.getStringExtra("ProjectTitle").toString())
        etDescription.setText(intent.getStringExtra("projectDescription").toString())
        etGoal.setText(intent.getStringExtra("projectGoal").toString())
        etDuration.setText(intent.getStringExtra("projectDuration").toString())
        etStatus.setText(intent.getStringExtra("projectStatus").toString())
        etOrganizer.setText(intent.getStringExtra("projectOrganizer").toString())
        etCategory.setText(intent.getStringExtra("projectCategory").toString())
        etAddB.setText(intent.getStringExtra("projectBeneficiaries").toString())


        mDialog.setTitle("Updating $projectTitle Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateProjectData(
                projectID,
                etProjectTitle.text.toString(),
                etDescription.text.toString(),
                etDuration.text.toString(),
                etGoal.text.toString(),
                etStatus.text.toString(),
                etOrganizer.text.toString(),
                etCategory.text.toString(),
                etAddB.text.toString()
            )

            //check required data updated
            Toast.makeText(applicationContext, "Project Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvProTitle.text = etProjectTitle.text.toString()
            tvProdes.text = etDescription.text.toString()
            tvProGoal.text = etGoal.text.toString()
            tvProDu.text = etDuration.text.toString()
            tvProStatus.text = etStatus.text.toString()
            tvProOrg.text = etOrganizer.text.toString()
            tvProCate.text = etCategory.text.toString()
            tvProBen.text = etAddB.text.toString()


            alertDialog.dismiss()
        }
    }

    private fun updateProjectData(
        id: String,
        title: String,
        description: String,
        duration:String,
        goal: String,
        status: String,
        organizer: String,
        category: String,
        benefi: String


    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("project-management").child(id)
        val proInfo = ProjectModel(id, title, description, duration,goal,status,organizer,category,benefi)
        dbRef.setValue(proInfo)
    }



}