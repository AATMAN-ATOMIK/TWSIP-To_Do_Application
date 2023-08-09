package com.atomikak.todoapp.helperClass

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.lang.Exception

class SqliteHelper(val context: Context) : SQLiteOpenHelper(context, "MyTasks", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db!!.execSQL("CREATE TABLE Category ( c_id INTEGER PRIMARY KEY AUTOINCREMENT , c_name TEXT )")
            db!!.execSQL("CREATE TABLE Tasks ( t_id INTEGER PRIMARY KEY AUTOINCREMENT ,c_name TEXT ,t_title TEXT ,t_des TEXT ,t_time TEXT ,t_date Date ,t_status TEXT,t_priority TEXT)")
//            val cv = ContentValues()
//            cv.put("c_name", "Daily")
//            db.insert("Category", null, cv)

        } catch (e: Exception) {
            Log.d("ERR: ",e.message.toString())
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            db!!.execSQL("DROP TABLE IF EXISTS Tasks")
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    //Category Functions

    fun addCategory(category: String): Boolean {
        val db = this.writableDatabase
        var contentValue: ContentValues = ContentValues()
        contentValue.put("c_name", category)
        val res = db.insert("Category", null, contentValue)
        return res.toInt() != -1
    }

    fun showCategory(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM Category", null)
    }


    //Task Functions

    fun addTask(task: Task): Boolean {
        val db = this.writableDatabase
        var contentValue: ContentValues = ContentValues()
        contentValue.put("c_name", task.c_name.toString())
        contentValue.put("t_title", task.t_title.toString())
        contentValue.put("t_des", task.t_des.toString())
        contentValue.put("t_time", task.t_time.toString())
        contentValue.put("t_date", task.t_date.toString())
        contentValue.put("t_status", task.t_status.toString())
        contentValue.put("t_priority", task.t_priority.toString())
        val res = db.insert("Tasks", null, contentValue)
        return res.toInt() != -1
    }


    fun updateTask(task: Task, t_id: Int): Boolean {
        val db = this.writableDatabase
        var contentValue: ContentValues = ContentValues()
        contentValue.put("c_name", task.c_name.toString())
        contentValue.put("t_title", task.t_title.toString())
        contentValue.put("t_des", task.t_des.toString())
        contentValue.put("t_time", task.t_time.toString())
        contentValue.put("t_date", task.t_date.toString())
        contentValue.put("t_status", task.t_status.toString())
        contentValue.put("t_priority", task.t_priority.toString())
        val res = db.update("Tasks", contentValue, "t_id = ", arrayOf(t_id.toString()))
        return res != -1
    }

    fun deleteTask(t_id: Int): Boolean {
        val db = this.writableDatabase
        val res = db.delete("Tasks", "t_id = ", arrayOf(t_id.toString()))
        return res != -1
    }

    fun completeTask(t_id: Int): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("t_status", "Complete")
        val res = db.update("Tasks", contentValues, "t_id = ", arrayOf(t_id.toString()))
        return res != -1
    }

    fun showTask(sort: String? = null, status: String? = null, sortStyle: String? = null): Cursor {
        val db = this.readableDatabase
        return if (sort == null && status == null && sortStyle == null) {
            db.rawQuery("SELECT * FROM Tasks", null)
        } else {
            if (sort == null) {
                db.rawQuery("SELECT * FROM Tasks WHERE t_status = ?", arrayOf(status))
            } else if (status == null) {
                db.rawQuery("SELECT * FROM Tasks ORDER BY $sort $sortStyle", null)
            } else {
                db.rawQuery(
                    "SELECT * FROM Tasks ORDER BY $sort $sortStyle WHERE t_status = ?",
                    arrayOf(status)
                )
            }
        }
    }
}