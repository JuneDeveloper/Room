package com.example.room

import androidx.lifecycle.LiveData

class ContactRepository(private val contactDao: ContactDao) {

    val contacts: LiveData<List<Contact>> = contactDao.getAllProduct()

    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }
    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }
}