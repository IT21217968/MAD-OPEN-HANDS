package com.example.project_management.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_management.models.ProjectModel
import com.example.project_management.R
import com.example.project_management.adapters.ProjectAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Viewproject : AppCompatActivity() {

    private lateinit var projectRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var projectList: ArrayList<ProjectModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewproject)

        projectRecyclerView = findViewById(R.id.rvProject)
        projectRecyclerView.layoutManager=LinearLayoutManager(this)
        projectRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        projectList = arrayListOf<ProjectModel>()

        getProjectData()

    }

    //get data from firebase
    private fun getProjectData() {

        //projectRecyclerView.visibility = android.view.View.GONE
        tvLoadingData.visibility = android.view.View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("project-management")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                projectList.clear()
                if(snapshot.exists()){
                    //getting all data in DB
                    for (projectSnap in snapshot.children){
                        val ProjectData=projectSnap.getValue(ProjectModel::class.java)
                        projectList.add(ProjectData!!)
                    }
                    val mAdapter = ProjectAdapter(projectList)
                    projectRecyclerView.adapter=mAdapter

                    mAdapter.setOnItemClickListener(object : ProjectAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@Viewproject, ProjectDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("projectID", projectList[position].proID)
                            intent.putExtra("ProjectTitle", projectList[position].proTitle)
                            intent.putExtra("projectDescription", projectList[position].proDescription)
                            intent.putExtra("projectGoal", projectList[position].proProjectgoal)
                            intent.putExtra("projectDuration", projectList[position].proDuration)
                            intent.putExtra("projectStatus", projectList[position].proStatus)
                            intent.putExtra("projectOrganizer", projectList[position].proOrganizer)
                            intent.putExtra("projectCategory", projectList[position].proCategory)
                            intent.putExtra("projectBeneficiaries", projectList[position].proAddbeneficiaries)
                            startActivity(intent)
                        }

                    })

                    projectRecyclerView.visibility=android.view.View.VISIBLE
                    //tvLoadingData.visibility= android.view.View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }
}