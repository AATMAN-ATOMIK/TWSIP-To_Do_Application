package com.atomikak.todoapp.my_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.atomikak.todoapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var m_recv_category: RecyclerView
    private lateinit var m_recv_my_task: RecyclerView
    private lateinit var sortByDate: TextView
    private lateinit var m_addTask: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        m_recv_category = findViewById(R.id.m_recv_category)
        m_recv_my_task = findViewById(R.id.m_recv_my_task)
        sortByDate = findViewById(R.id.sortByDate)
        m_addTask = findViewById(R.id.m_addTask)

        m_addTask.setOnClickListener{
            val intent = Intent(this@MainActivity,AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}