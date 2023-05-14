package com.example.project_management.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_management.R
import com.example.project_management.adapters.DonationAdapter
import com.example.project_management.models.DonationModel
import com.google.firebase.database.*

class Viewdonation : AppCompatActivity() {

    private lateinit var donationRecyclerView: RecyclerView
    private lateinit var tvLoadingData1: TextView
    private lateinit var donationList: ArrayList<DonationModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewdonation)

        donationRecyclerView = findViewById(R.id.rvDonation)
        donationRecyclerView.layoutManager=LinearLayoutManager(this)
        donationRecyclerView.setHasFixedSize(true)
        tvLoadingData1 = findViewById(R.id.tvLoadingData1)

        donationList = arrayListOf<DonationModel>()

        getDonationData()

    }

    //get data from firebase
    private fun getDonationData() {

        tvLoadingData1.visibility = android.view.View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("donation-management")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                donationList.clear()
                if(snapshot.exists()){
                    //getting all data in DB
                    for (donationSnap in snapshot.children){
                        val DonationData=donationSnap.getValue(DonationModel::class.java)
                        donationList.add(DonationData!!)
                    }
                    val mAdapter = DonationAdapter(donationList)
                    donationRecyclerView.adapter=mAdapter

                    mAdapter.setOnItemClickListener(object : DonationAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@Viewdonation, DonationDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("donationID", donationList[position].donID)
                            intent.putExtra("donorName", donationList[position].donName)
                            intent.putExtra("donatorNic", donationList[position].donNIC)
                            intent.putExtra("donatorMobile", donationList[position].donMibile)
                            intent.putExtra("donatorAddress", donationList[position].donAddress)
                            intent.putExtra("donatorItem",donationList[position].donItem)
                            intent.putExtra("donatorQty", donationList[position].donQut)
                            intent.putExtra("donatorDate", donationList[position].donDate)
                            startActivity(intent)
                        }

                    })

                    donationRecyclerView.visibility=android.view.View.VISIBLE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }
}