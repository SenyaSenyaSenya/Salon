package com.example.salon.admin.masters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.R

class MasterAdapter : RecyclerView.Adapter<MasterAdapter.MasterViewHolder>() {
    private val masters = mutableListOf<Master>()

    fun setMasters(masters: List<Master>) {
        this.masters.clear()
        this.masters.addAll(masters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_master_for_admin, parent, false)
        return MasterViewHolder(view)
    }

    override fun onBindViewHolder(holder: MasterViewHolder, position: Int) {
        val master = masters[position]
        holder.bind(master)
    }

    override fun getItemCount(): Int {
        return masters.size
    }

    inner class MasterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.masterNameTextView)
        private val specialtyTextView: TextView = itemView.findViewById(R.id.masterSpecialtyTextView)
        private val experienceTextView: TextView = itemView.findViewById(R.id.masterExperienceTextView)
        private val aboutTextView: TextView = itemView.findViewById(R.id.masterAboutTextView)

        fun bind(master: Master) {
            nameTextView.text = master.name
            specialtyTextView.text = master.specialty
            experienceTextView.text = master.experience
            aboutTextView.text = master.about
        }
    }
}