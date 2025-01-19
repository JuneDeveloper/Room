package com.example.room

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class MainActivity : AppCompatActivity(),ContactAdapter.ContactClickListener {

    private lateinit var viewModel: ContactViewModel

    private lateinit var toolbarTB:Toolbar
    private lateinit var inputSurnameET:EditText
    private lateinit var inputNameET:EditText
    private lateinit var inputPhoneET:EditText
    private lateinit var saveBTN:Button
    private lateinit var recycleViewTV:RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        recycleViewTV.layoutManager = LinearLayoutManager(this)
        val adapter = ContactAdapter(this,this)
        recycleViewTV.adapter = adapter

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
            .getInstance(application))[ContactViewModel::class.java]
        viewModel.contacts.observe(this,{list -> list?.let { adapter.updateList(it)}})
    }

    private fun init() {
        inputSurnameET = findViewById(R.id.inputSurnameET)
        inputNameET = findViewById(R.id.inputNameET)
        inputPhoneET = findViewById(R.id.inputPhoneET)
        saveBTN = findViewById(R.id.saveBTN)
        recycleViewTV = findViewById(R.id.recycleViewTV)
        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.exitItem -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(contact: Contact) {
        viewModel.deleteContact(contact)
        Toast.makeText(this,"Контакт ${contact.name} удален.",Toast.LENGTH_SHORT).show()
    }
    fun saveData(view: View){
        val contactSurname = inputSurnameET.text.toString()
        val contactName = inputNameET.text.toString()
        val contactPhone = inputPhoneET.text.toString()
        val contactTime = formatMilliseconds(Date().time)
        if (contactSurname.isNotEmpty() && contactName.isNotEmpty() && contactPhone.isNotEmpty()){
            viewModel.insertContact(Contact(contactSurname,contactName,contactPhone,contactTime))
            Toast.makeText(this,"Контакт добавлен.",Toast.LENGTH_SHORT).show()
        }
        inputClear()
    }
    @SuppressLint("SimpleDateFormat")
    private fun formatMilliseconds(time: Long): String {
        val timeFormat = SimpleDateFormat("EEE,  HH:mm")
        timeFormat.timeZone = TimeZone.getTimeZone("GMT+08")
        return timeFormat.format(Date(time))
    }

    private fun inputClear() {
        inputSurnameET.text.clear()
        inputNameET.text.clear()
        inputPhoneET.text.clear()
    }
}