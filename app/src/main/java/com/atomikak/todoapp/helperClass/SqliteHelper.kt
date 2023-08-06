package com.atomikak.todoapp.helperClass

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.lang.Exception

class SqliteHelper(val context: Context) : SQLiteOpenHelper(context, "MyTasks", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db!!.execSQL("CREATE TABLE IF NOT EXISTS Category ( c_id INTEGER PRIMARY KEY AUTOINCREMENT , c_name TEXT )")
            db.execSQL("CREATE TABLE IF NOT EXISTS Tasks ( t_id INTEGER PRIMARY KEY AUTOINCREMENT ,c_id INTEGER ,  t_title TEXT , t_des TEXT , t_time TEXT , t_date Date , t_status TEXT , FOREIGN KEY (c_id) REFERENCES Category (c_id) )")
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
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

    fun showCategory():Cursor{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM Category",null)
    }



    //Task Functions

    fun addTask(task: Task): Boolean {
        val db = this.writableDatabase
        var contentValue: ContentValues = ContentValues()
        contentValue.put("c_id", task.c_id.toString())
        contentValue.put("t_title", task.t_title.toString())
        contentValue.put("t_des", task.t_des.toString())
        contentValue.put("t_time", task.t_time.toString())
        contentValue.put("t_date", task.t_date.toString())
        val res = db.insert("Tasks", null, contentValue)
        return res.toInt() != -1
    }


    fun updateTask(task: Task, t_id: Int): Boolean {
        val db = this.writableDatabase
        var contentValue: ContentValues = ContentValues()
        contentValue.put("c_id", task.c_id.toString())
        contentValue.put("t_title", task.t_title.toString())
        contentValue.put("t_des", task.t_des.toString())
        contentValue.put("t_time", task.t_time.toString())
        contentValue.put("t_date", task.t_date.toString())
        contentValue.put("t_status", task.t_status.toString())
        val res = db.update("Tasks", contentValue, "t_id = ", arrayOf(t_id.toString()))
        return res != -1
    }

    fun deleteTask(t_id: Int): Boolean {
        val db = this.writableDatabase
        val res = db.delete("Tasks", "t_id = ", arrayOf(t_id.toString()))
        return res != -1
    }

    fun completeTask(t_status: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("t_status", t_status)
        val res = db.update("Tasks", contentValues, "t_id = ", arrayOf(t_status))
        return res != -1
    }

    fun showTask(sort: String? = null, status: String? = null, sortStyle: String): Cursor {
        val db = this.readableDatabase
        return if (sort == null && status == null) {
            db.rawQuery("SELECT * FROM Tasks", null)
        } else {
            if (sort == null) {
                db.rawQuery("SELECT * FROM Tasks WHERE t_status = ?", arrayOf(status))
            } else if (status == null) {
                db.rawQuery("SELECT * FROM Tasks ORDER BY $sort $sortStyle", null)
            }else{
                db.rawQuery("SELECT * FROM Tasks ORDER BY $sort $sortStyle WHERE t_status = ?",
                    arrayOf(status))
            }
        }
    }
}