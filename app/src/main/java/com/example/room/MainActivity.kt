package com.example.room

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var db:ContactDatabase? = null

    private lateinit var toolbarTB:Toolbar
    private lateinit var inputNameET:EditText
    private lateinit var inputPhoneET:EditText
    private lateinit var saveBTN:Button
    private lateinit var outputTextTV:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        db = ContactDatabase.getDatabase(this)
        readDatabase(db!!)

    }

    override fun onResume() {
        super.onResume()
        saveBTN.setOnClickListener {
            val contact = Contact(inputNameET.text.toString(),inputPhoneET.text.toString())
            addContact(db!!,contact)
            readDatabase(db!!)
        }
    }

    private fun addContact(db:ContactDatabase, contact: Contact) = GlobalScope.async {
        db.getContactDao().insert(contact)
    }
    private fun readDatabase(db: ContactDatabase) = GlobalScope.async {
        outputTextTV.text = ""
        val list = db.getContactDao().getAllProduct()
        list.forEach { i -> outputTextTV.append(i.name + " " + i.number + "\n") }
    }

    private fun init() {
        inputNameET = findViewById(R.id.inputNameET)
        inputPhoneET = findViewById(R.id.inputPhoneET)
        saveBTN = findViewById(R.id.saveBTN)
        outputTextTV = findViewById(R.id.outputTextTV)
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
}