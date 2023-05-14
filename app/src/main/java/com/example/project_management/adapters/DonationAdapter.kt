package com.example.project_management.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_management.R
import com.example.project_management.models.DonationModel


class DonationAdapter (private val donationList: ArrayList<DonationModel>) :
    RecyclerView.Adapter<DonationAdapter.ViewHolder>() {

    private var mListener: onItemClickListener = object : onItemClickListener {
        override fun onItemClick(position: Int) {
            // do nothing by default
        }
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.donation_list, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDonation = donationList[position]
        holder.tvDonorName.text = currentDonation.donName
        holder.tvDonItem.text=currentDonation.donItem
    }

    override fun getItemCount(): Int {
        return donationList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvDonorName : TextView = itemView.findViewById(R.id.tvDonorName)
        val tvDonItem:TextView=itemView.findViewById(R.id.tvItem)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}


