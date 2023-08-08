package com.atomikak.todoapp.my_activities

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atomikak.todoapp.R
import com.atomikak.todoapp.adapters.CategoryAdapter
import com.atomikak.todoapp.adapters.TaskAdapter
import com.atomikak.todoapp.helperClass.Category
import com.atomikak.todoapp.helperClass.SqliteHelper
import com.atomikak.todoapp.helperClass.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.security.Permission

class MainActivity : AppCompatActivity() {

    //widgets
    private lateinit var m_recv_category: RecyclerView
    private lateinit var m_recv_my_task: RecyclerView
    private lateinit var sortByDate: TextView
    private lateinit var m_addTask: FloatingActionButton


    //adapters
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var taskAdapter: TaskAdapter

    //Helper
    private lateinit var categoryList: ArrayList<String>
    private lateinit var taskList: ArrayList<Task>
    private lateinit var sqliteHelper: SqliteHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        getPermissions()


        m_recv_category = findViewById(R.id.m_recv_category)
        m_recv_my_task = findViewById(R.id.m_recv_my_task)
        sortByDate = findViewById(R.id.sortByDate)
        m_addTask = findViewById(R.id.m_addTask)
        categoryList = arrayListOf()
        sqliteHelper = SqliteHelper(context = this@MainActivity)

        //Category Recyclerview initialization
        categoryList = Category(sqliteHelper).getCategory()
        categoryAdapter = CategoryAdapter(this@MainActivity, categoryList)
        m_recv_category.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        m_recv_category.setHasFixedSize(true)
        m_recv_category.adapter = categoryAdapter

        taskList = ArrayList()
        getTaskList()
        taskAdapter = TaskAdapter(this@MainActivity, taskList)
        m_recv_my_task.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        m_recv_my_task.setHasFixedSize(true)
        m_recv_my_task.adapter = taskAdapter





        m_addTask.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    private fun getPermissions() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                val alertDialog = AlertDialog.Builder(this@MainActivity)
                    .setTitle("Permission Required")
                    .setMessage("Notification Permission is required in order to get notification of your tasks.")
                    .setPositiveButton("Allow", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            ActivityCompat.requestPermissions(
                                this@MainActivity,
                                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                                200
                            )
                        }
                    })
                    .setNegativeButton("Deny", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog!!.dismiss()
                        }
                    })
                    .create()

                alertDialog.show()
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    200
                )
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.USE_EXACT_ALARM
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.USE_EXACT_ALARM
                )
            ) {
                val alertDialog = AlertDialog.Builder(this@MainActivity)
                    .setTitle("Permission Required")
                    .setMessage("Notification Permission is required in order to get notification of your tasks.")
                    .setPositiveButton("Allow", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            ActivityCompat.requestPermissions(
                                this@MainActivity,
                                arrayOf(Manifest.permission.USE_EXACT_ALARM),
                                200
                            )
                        }
                    })
                    .setNegativeButton("Deny", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog!!.dismiss()
                        }
                    })
                    .create()

                alertDialog.show()
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.USE_EXACT_ALARM),
                    200
                )
            }
        }
    }
        private fun getTaskList() {
            val cr = sqliteHelper.showTask()
            while (cr.moveToNext()) {
                taskList.add(
                    Task(
                        c_name = cr.getString(1).toString(),
                        t_title = cr.getString(2).toString(),
                        t_des = cr.getString(3).toString(),
                        t_time = cr.getString(4).toString(),
                        t_date = cr.getString(5).toString(),
                        t_status = cr.getString(6).toString(),
                        t_priority = cr.getString(7).toString()
                    )
                )
            }
        }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
