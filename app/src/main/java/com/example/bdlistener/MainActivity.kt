package com.example.bdlistener

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.bdlistener.R.id.layout

fun rand(left: Int, right: Int): Int {
    return (left..right).random()
}
fun rand(arr : Array<String>): String {
    return arr[(0..arr.size-1).random()]
}

class MainActivity : AppCompatActivity(){
    lateinit var table : TableLayout
    lateinit var database: DBHandler
    lateinit var layout : ConstraintLayout
    lateinit var btnAdd : Button
    lateinit var btnDel : Button
    private val names = arrayOf("Иван", "Александр", "Никита", "Андрей", "Олег", "Евгений", "Михаил", "Мухамед" )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.layout)
        table = findViewById(R.id.table)
        btnDel = findViewById(R.id.btnDel)
        btnAdd = findViewById(R.id.btnAdd)
        database= DBHandler(this)
        val tableRow = TableRow(this)
        updateTable()
        btnAdd.setOnClickListener {
            var weight :Double = rand(40,150).toDouble() + rand(0,99).toDouble()/100
            var height :Double = rand(140,250).toDouble() + rand(0,99).toDouble()/100
            var age :Double = rand(7,99).toDouble() + rand(0,99).toDouble()/100

            database.addStudent(DBObjectStudent(rand(names), weight,height,age))
            updateTable()
        }
        btnDel.setOnClickListener {
            database.deleteAllStudents()
            updateTable()
        }
    }

    fun randomName()
    {

    }

    fun updateTable()
    {
        var data = database.viewStudent()
        data.sortBy{ it.age  }
        table.removeAllViews()
        for(i in data)
        {
            val tableRow = TableRow(this)
            val textName = TextView(this)
            textName.text = i.name
            textName.setTextColor(Color.parseColor("#FF000000"))
            textName.textSize = 20F
            textName.gravity = Gravity.CENTER
            val textWeight = TextView(this)
            textWeight.text = i.weight.toString()
            textWeight.setTextColor(Color.parseColor("#FF000000"))
            textWeight.textSize = 20F
            textWeight.gravity = Gravity.CENTER
            val textHeight = TextView(this)
            textHeight.text = i.height.toString()
            textHeight.setTextColor(Color.parseColor("#FF000000"))
            textHeight.textSize = 20F
            textHeight.gravity = Gravity.CENTER
            val textAge = TextView(this)
            textAge.text = i.age.toString()
            textAge.setTextColor(Color.parseColor("#FF000000"))
            textAge.textSize = 20F
            textAge.gravity = Gravity.CENTER


            tableRow.addView(textName, TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0F))
            tableRow.addView(textHeight, TableRow.LayoutParams(100, 100, 1.0F))
            tableRow.addView(textWeight, TableRow.LayoutParams(100, 100, 1.0F))
            tableRow.addView(textAge, TableRow.LayoutParams(100, 100, 1.0F))
            table.addView(tableRow)
        }
    }
}