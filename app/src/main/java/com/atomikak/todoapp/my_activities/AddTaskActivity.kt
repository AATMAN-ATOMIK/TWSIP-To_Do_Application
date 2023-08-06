package com.atomikak.todoapp.my_activities

import android.app.DatePickerDialog
import android.content.res.Resources.Theme
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.atomikak.todoapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddTaskActivity : AppCompatActivity() {

    private lateinit var at_ed_taskTitle:EditText
    private lateinit var at_ed_taskDes:EditText
    private lateinit var at_ed_taskDate:EditText
    private lateinit var at_ed_taskTime:EditText
    private lateinit var at_sp_taskCategory:Spinner
    private lateinit var at_sp_taskRemSelector:Spinner
    private lateinit var at_ch_remindMe:CheckBox
    private lateinit var at_btn_addCategory:Button
    private lateinit var at_btn_addTask: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        init()
    }

    private fun init(){
        at_ed_taskTitle = findViewById(R.id.at_ed_taskTitle)
        at_ed_taskDes = findViewById(R.id.at_ed_taskDes)
        at_ed_taskDate = findViewById(R.id.at_ed_taskDate)
        at_ed_taskTime = findViewById(R.id.at_ed_taskTime)
        at_sp_taskCategory = findViewById(R.id.at_sp_taskCategory)
        at_sp_taskRemSelector = findViewById(R.id.at_sp_taskRemSelector)
        at_ch_remindMe = findViewById(R.id.at_ch_remindMe)
        at_btn_addCategory = findViewById(R.id.at_btn_addCategory)
        at_btn_addTask = findViewById(R.id.at_btn_addTask)
        at_btn_addTask = findViewById(R.id.at_btn_addTask)


        at_btn_addCategory.setOnClickListener{
            val view = LayoutInflater.from(this@AddTaskActivity).inflate(R.layout.add_category,null,false)
            val ed_category:EditText = view.findViewById(R.id.ac_catName)
            val btn_addCategory:Button = view.findViewById(R.id.ac_addCategory)

            btn_addCategory.setOnClickListener{
                Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show()
            }
            val bottomSheetDialog = BottomSheetDialog(baseContext)
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }

    }
}