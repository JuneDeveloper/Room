package com.example.room

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val context: Context,private val listener:ContactClickListener)
    :RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val contacts = ArrayList<Contact>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList:List<Contact>){
        contacts.clear()
        contacts.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        private val surnameCardTV:TextView = itemView.findViewById(R.id.surnameCardTV)
        private val nameCardTV:TextView = itemView.findViewById(R.id.nameCardTV)
        private val phoneCardTV:TextView = itemView.findViewById(R.id.phoneCardTV)
        private val timeCardTV:TextView = itemView.findViewById(R.id.timeCardTV)
        val imageDeleteCard:ImageView = itemView.findViewById(R.id.imageDeleteCard)

        @SuppressLint("SetTextI18n")
        fun bind(contact: Contact){
            surnameCardTV.text = contact.surname
            nameCardTV.text = contact.name
            phoneCardTV.text = contact.phone
            timeCardTV.text = contact.time.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {

        val viewHolder =
            ContactViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item,parent,false))
            viewHolder.imageDeleteCard.setOnClickListener {
                listener.onItemClicked(contacts[viewHolder.adapterPosition])
            }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = contacts[position]
        holder.bind(currentContact)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    interface ContactClickListener{
        fun onItemClicked(contact: Contact)
    }
}