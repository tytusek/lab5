package com.example.lab5

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT )")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // below is the method for updating our courses
    fun updateCourse(
        id: Int, name: String
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_COl, name)

        db.update(TABLE_NAME, values, ID_COL+"=?", arrayOf(id.toString()))
        db.close()
    }

    // This method is for adding data in our database
    fun addName(name : String){
        val values = ContentValues()

        values.put(NAME_COl, name)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    // below method is to get
    // all data from our database
    fun selectAll(): Cursor {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    companion object{
        private val DATABASE_NAME = "lab5"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "students"
        val ID_COL = "id"
        val NAME_COl = "name"
    }
}