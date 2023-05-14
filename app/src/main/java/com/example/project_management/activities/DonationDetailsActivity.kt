package com.example.project_management.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.project_management.R
import com.example.project_management.models.DonationModel
import com.google.firebase.database.FirebaseDatabase

class DonationDetailsActivity : AppCompatActivity() {

    private lateinit var tvDonID: TextView
    private lateinit var tvDonName: TextView
    private lateinit var tvDonItem: TextView
    private lateinit var tvDonQTY: TextView
    private lateinit var tvDonNIC: TextView
    private lateinit var tvDonMobile: TextView
    private lateinit var tvDonAddress: TextView
    private lateinit var tvDonDate: TextView
    private lateinit var btnUpdate1: Button
    private lateinit var btnDelete1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation_details)

        initView1()
        setValuesToViews1()

        btnUpdate1.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("donationID").toString(),
                intent.getStringExtra("donorName").toString()
            )
        }
        btnDelete1.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("donationID").toString()
            )
        }

    }


    private fun initView1() {
        tvDonID = findViewById(R.id.tvDonationId)
        tvDonName = findViewById(R.id.tvDonName)
        tvDonNIC = findViewById(R.id.tvDonNIC)
        tvDonMobile = findViewById(R.id.tvDonMobile)
        tvDonAddress = findViewById(R.id.tvDonAddress)
        tvDonItem = findViewById(R.id.tvDonItem)
        tvDonQTY = findViewById(R.id.tvDonQTY)
        tvDonDate = findViewById(R.id.tvDonDate)


        btnUpdate1 = findViewById(R.id.btnUpdate)
        btnDelete1 = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews1() {
        tvDonID.text = intent.getStringExtra("donationID")
        tvDonName.text = intent.getStringExtra("donorName")
        tvDonNIC.text = intent.getStringExtra("donatorNic")
        tvDonMobile.text = intent.getStringExtra("donatorMobile")
        tvDonAddress.text = intent.getStringExtra("donatorAddress")
        tvDonItem.text = intent.getStringExtra("donatorItem")
        tvDonQTY.text = intent.getStringExtra("donatorQty")
        tvDonDate.text = intent.getStringExtra("donatorDate")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("donation-management").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Donation data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, Viewdonation::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        donationID: String,
        donorName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_donation, null)

        mDialog.setView(mDialogView)

        val etDonarName = mDialogView.findViewById<EditText>(R.id.up_name)
        val etNIC = mDialogView.findViewById<EditText>(R.id.up_nic)
        val etMobile = mDialogView.findViewById<EditText>(R.id.up_mobile)
        val etAddress = mDialogView.findViewById<EditText>(R.id.up_address)
        val etItem = mDialogView.findViewById<EditText>(R.id.up_item)
        val etQTY = mDialogView.findViewById<EditText>(R.id.up_quantity)
        val etDate = mDialogView.findViewById<EditText>(R.id.up_date)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.up_savebutton)

        etDonarName.setText(intent.getStringExtra("donorName").toString())
        etNIC.setText(intent.getStringExtra("donatorNic").toString())
        etMobile.setText(intent.getStringExtra("donatorMobile").toString())
        etAddress.setText(intent.getStringExtra("donatorAddress").toString())
        etItem.setText(intent.getStringExtra("donatorItem").toString())
        etQTY.setText(intent.getStringExtra("donatorQty").toString())
        etDate.setText(intent.getStringExtra("donatorDate").toString())


        mDialog.setTitle("Updating $donorName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateProjectData(
                donationID,
                etDonarName.text.toString(),
                etNIC.text.toString(),
                etMobile.text.toString(),
                etAddress.text.toString(),
                etItem.text.toString(),
                etQTY.text.toString(),
                etDate.text.toString()
            )

            //check required data updated
            Toast.makeText(applicationContext, "Donation Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvDonName.text =etDonarName.text.toString()
            tvDonNIC.text = etNIC.text.toString()
            tvDonMobile.text = etMobile.text.toString()
            tvDonAddress.text = etAddress.text.toString()
            tvDonItem.text =etItem.text.toString()
            tvDonQTY.text = etQTY.text.toString()
            tvDonDate.text = etDate.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateProjectData(
        id: String,
        name: String,
        nic: String,
        mobile: String,
        address: String,
        item: String,
        quantity: String,
        date: String,

    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("donation-management").child(id)
        val donInfo = DonationModel(id, name,nic,mobile,address, item, quantity,date)
        dbRef.setValue(donInfo)
    }



}