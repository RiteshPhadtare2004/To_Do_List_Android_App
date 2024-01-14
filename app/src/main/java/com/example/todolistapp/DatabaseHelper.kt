package com.example.todolistapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TodoListDB"
        private const val TABLE_NAME = "tasks"
        private const val COL_ID = "id"
        private const val COL_TASK = "task"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_TASK TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertTask(task: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TASK, task)
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result
    }

    fun deleteTask(task: String): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COL_TASK = ?", arrayOf(task))
        db.close()
        return result
    }

    @SuppressLint("Range")
    fun getAllTasks(): ArrayList<String> {
        val tasksList = ArrayList<String>()
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor?.use {
            while (it.moveToNext()) {
                tasksList.add(it.getString(it.getColumnIndex(COL_TASK)))
            }
        }
        db.close()
        return tasksList
    }
}
