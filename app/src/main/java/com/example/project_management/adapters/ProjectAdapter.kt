package com.example.project_management.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_management.R
import com.example.project_management.models.ProjectModel

class ProjectAdapter (private val projectList: ArrayList<ProjectModel>) :
    RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    //private lateinit var mListener:onItemClickListener
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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.project_list, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProject = projectList[position]
        holder.tvProjectTitle.text = currentProject.proTitle
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvProjectTitle : TextView = itemView.findViewById(R.id.tvProjectTitle)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}


