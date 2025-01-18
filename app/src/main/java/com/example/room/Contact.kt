package com.example.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class Contact (
        @ColumnInfo(name = "name")var name:String,
        @ColumnInfo(name = "number")var number:String
     )
{
    @PrimaryKey(autoGenerate = true)
    var id = 0
}