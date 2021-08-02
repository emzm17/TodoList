package com.example.todolist

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.todolist.model.AppData
import com.example.todolist.model.Todo
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

const val DB="todo.db"

class TaskActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var myCalendar:Calendar
    lateinit var dateSetListener:DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener

    private val labels= arrayListOf("Class","Lab","Assignment","Project")

    var fDate=0L
    var fTime=0L

    private val db by lazy {
          AppData.getDatabase(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        dateEdt.setOnClickListener(this)
        timeEdt.setOnClickListener(this)
        createBtn.setOnClickListener(this)
        spinner()
    }

    private fun spinner() {
     val adapter=ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,labels)
        labels.sort()
        spinnerCategory.adapter=adapter
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.dateEdt->{
                    setListener()
                }
                R.id.timeEdt->{
                     setTimeListener()
                }
                R.id.createBtn->{
                    createTask()
                }
            }
        }
    }

    private fun createTask() {
        val category = spinnerCategory.selectedItem.toString()
        val title = titleInpLay.editText?.text.toString()
        val description = taskInpLay.editText?.text.toString()
         GlobalScope.launch(Dispatchers.Main){
              val id= withContext(Dispatchers.IO){
                   return@withContext db.todoDAO().insertTask(
                           Todo(
                                   title,
                                   description,
                                   category,
                                   fDate,
                                   fTime
                           )
                   )
              }
             finish()
         }


    }


    private fun setTimeListener() {
       myCalendar= Calendar.getInstance()
        timeSetListener=TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay:Int, minute:Int ->
            myCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
            myCalendar.set(Calendar.MINUTE,minute)
            updateTime()

        }
        val timePickerDialog = TimePickerDialog(
                this, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),false)

        timePickerDialog.show()
    }

    private fun updateTime() {
        val myformat="H:mm a"

        val simpleTimeformat=SimpleDateFormat(myformat)
        fTime=myCalendar.time.time
        timeEdt.setText(simpleTimeformat.format(myCalendar.time))
    }

    private fun setListener() {
        myCalendar = Calendar.getInstance()
        dateSetListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            myCalendar.set(Calendar.YEAR,year)
            myCalendar.set(Calendar.MONTH,month)
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateDate()
        }

        val datePickerDialog = DatePickerDialog(
                this, dateSetListener, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.datePicker.minDate=System.currentTimeMillis()
       datePickerDialog.show()
    }

    private fun updateDate() {
       val myformat="EEE,d MMM YYYY"
        val simpleDateformat=SimpleDateFormat(myformat)
        dateEdt.setText(simpleDateformat.format(myCalendar.time))
        fDate=myCalendar.time.time
        timeInptLay.visibility=View.VISIBLE
    }
}
