package com.atomikak.todoapp.my_activities


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.atomikak.todoapp.R
import com.atomikak.todoapp.helperClass.Category
import com.atomikak.todoapp.helperClass.SqliteHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar


class AddTaskActivity : AppCompatActivity() {

    private lateinit var at_ed_taskTitle: EditText
    private lateinit var at_ed_taskDes: EditText
    private lateinit var at_ed_taskDate: EditText
    private lateinit var at_ed_taskTime: EditText
    private lateinit var at_sp_taskCategory: Spinner
    private lateinit var at_sp_taskRemSelector: Spinner
    private lateinit var at_ch_remindMe: CheckBox
    private lateinit var at_btn_addCategory: Button
    private lateinit var at_btn_addTask: Button
    private lateinit var sqliteHelper: SqliteHelper
    private lateinit var categoryList: ArrayList<String>
    private lateinit var timeList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        sqliteHelper = SqliteHelper(context = this@AddTaskActivity)
        categoryList = Category(sqliteHelper).getCategory()
        timeList = arrayListOf("5 - Minutes Earlier","10 - Minutes Earlier","20 - Minutes Earlier","30 - Minutes Earlier")
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


        //Category Adding Function
        at_btn_addCategory.setOnClickListener {
            showAddCategoryBottomSheet()
        }

        //Task Date Assignment
        at_ed_taskDate.setOnClickListener{
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this,{
                    view, year, month, dayOfMonth ->
                        at_ed_taskDate.setText("$dayOfMonth / $month / $year")
                },
                calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }


        //Task TIme Assignment
        at_ed_taskTime.setOnClickListener{
           val timePickerDialog = TimePickerDialog(this@AddTaskActivity,
               { view, hourOfDay, minute -> at_ed_taskTime.setText("$hourOfDay : $minute") },12,10,true)

            timePickerDialog.show()
        }


        at_sp_taskCategory.adapter = ArrayAdapter(this@AddTaskActivity,android.R.layout.simple_dropdown_item_1line,categoryList)
        at_sp_taskRemSelector.adapter = ArrayAdapter(this@AddTaskActivity,android.R.layout.simple_dropdown_item_1line,timeList)

    }

    //Category Adding Function
    private fun showAddCategoryBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this@AddTaskActivity)
        val view =
            LayoutInflater.from(this@AddTaskActivity).inflate(R.layout.add_category, null, false)
        val ed_category: EditText = view.findViewById(R.id.ac_catName)
        val btn_addCategory: Button = view.findViewById(R.id.ac_addCategory)

        btn_addCategory.setOnClickListener {
            if (ed_category.text.isNotEmpty()) {
                sqliteHelper.addCategory(ed_category.text.toString())
                bottomSheetDialog.dismiss()
                Toast.makeText(this, "Category Added", Toast.LENGTH_SHORT).show()
            }
        }
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }
}