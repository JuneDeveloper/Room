package com.example.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {

    @Insert
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contacts_table ORDER BY id ASC")
    fun getAllProduct():List<Contact>

    @Query("DELETE FROM contacts_table")
    fun deleteAll()
}