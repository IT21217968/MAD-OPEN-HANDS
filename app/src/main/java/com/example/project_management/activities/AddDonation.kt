package com.example.project_management.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.project_management.models.DonationModel
import com.example.project_management.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddDonation : AppCompatActivity() {

    //initialize views
    private lateinit var etName: EditText
    private lateinit var etNIC: EditText
    private lateinit var etMobile: EditText
    private lateinit var etAddress: EditText
    private lateinit var etItem: EditText
    private lateinit var etQut: EditText
    private lateinit var etDate: EditText
    private lateinit var btnSave:Button
    private lateinit var btnView:Button

    //database references
    private lateinit var dbRefDon: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_donation)

        var viewButton = findViewById<Button>(R.id.viewbutton)
        viewButton.setOnClickListener {
            val Intent = Intent(this, Viewdonation::class.java)
            startActivity(Intent)
        }

        //initialize
        etName =findViewById(R.id.name)
        etNIC =findViewById(R.id.nic)
        etMobile=findViewById(R.id.mobile)
        etAddress =findViewById(R.id.address)
        etItem =findViewById(R.id.item)
        etQut= findViewById(R.id.quantity)
        etDate =findViewById(R.id.date)
        btnSave= findViewById(R.id.savebutton)
        btnView= findViewById(R.id.viewbutton)


        dbRefDon = FirebaseDatabase.getInstance().getReference("donation-management")

        btnSave.setOnClickListener {
            saveDonationData()
        }
    }
    private fun saveDonationData(){
        //getting values
        val don_name = etName.text.toString()
        val don_nic = etNIC.text.toString()
        val don_mobile = etMobile.text.toString()
        val don_address= etAddress.text.toString()
        val don_item = etItem.text.toString()
        val don_qut = etQut.text.toString()
        val don_date = etDate.text.toString()


        if(don_name.isEmpty()){
            etName.error="Please enter Donor name"
        }
        if(don_nic.isEmpty()){
            etNIC.error="Please enter Donor NIC"
        }
        if(don_mobile.isEmpty()){
            etMobile.error="Please enter Donor mobile no"
        }
        if(don_address.isEmpty()){
            etAddress.error="Please enter donor address"
        }
        if(don_item.isEmpty()){
            etItem.error="Please enter donation items"
        }
        if(don_qut.isEmpty()){
            etQut.error="Please enter donation items"
        }
        if(don_date.isEmpty()){
            etDate.error="Please enter item submission date"
        }
        val donationId = dbRefDon.push().key!!

        val donation = DonationModel(donationId,don_name,don_nic,don_mobile,don_address,don_item,don_qut,don_date)

        dbRefDon.child(donationId ).setValue(donation)
            .addOnCompleteListener{
                Toast.makeText(this,"Data insert successfully", Toast.LENGTH_LONG).show()

                etName.text.clear()
                etNIC.text.clear()
                etMobile.text.clear()
                etAddress.text.clear()
                etItem.text.clear()
                etQut.text.clear()
                etDate.text.clear()

            }.addOnFailureListener{ err->
                Toast.makeText(this,"Error${err.message}", Toast.LENGTH_LONG).show()
            }


    }
}