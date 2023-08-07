package com.atomikak.todoapp.my_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atomikak.todoapp.R
import com.atomikak.todoapp.adapters.CategoryAdapter
import com.atomikak.todoapp.helperClass.Category
import com.atomikak.todoapp.helperClass.SqliteHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var m_recv_category: RecyclerView
    private lateinit var m_recv_my_task: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    //    private lateinit var m_recv_my_task: TaskAdapter
    private lateinit var sortByDate: TextView
    private lateinit var m_addTask: FloatingActionButton
    private lateinit var categoryList: ArrayList<String>

    //    private lateinit var taskList: ArrayList<Task>
    private lateinit var sqliteHelper: SqliteHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        m_recv_category = findViewById(R.id.m_recv_category)
        m_recv_my_task = findViewById(R.id.m_recv_my_task)
        sortByDate = findViewById(R.id.sortByDate)
        m_addTask = findViewById(R.id.m_addTask)
        categoryList = arrayListOf()
        sqliteHelper = SqliteHelper(context = this@MainActivity)

        //Category Recyclerview initialization
        categoryList = Category(sqliteHelper).getCategory()
        categoryAdapter = CategoryAdapter(this@MainActivity,categoryList)
        m_recv_category.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
        m_recv_category.setHasFixedSize(true)
        m_recv_category.adapter = categoryAdapter



        m_addTask.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}