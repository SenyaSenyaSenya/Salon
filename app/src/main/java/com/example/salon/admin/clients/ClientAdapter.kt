package com.example.salon.admin.clients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.R
import com.example.salon.util.User

class ClientAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_client_for_admin, parent, false)
        return ClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ClientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewFirstName: TextView = itemView.findViewById(R.id.textViewFirstName)
        private val textViewLastName: TextView = itemView.findViewById(R.id.textViewLastName)
        private val textViewDateOfBirth: TextView = itemView.findViewById(R.id.textViewDateOfBirth)
        private val textViewPhoneNumber: TextView = itemView.findViewById(R.id.textViewPhoneNumber)


        fun bind(user: User) {
            textViewFirstName.text = user.firstName
            textViewLastName.text = user.lastName
            textViewDateOfBirth.text = user.dateOfBirth
            textViewPhoneNumber.text = user.phoneNumber

        }
    }
}