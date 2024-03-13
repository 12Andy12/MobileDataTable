package com.example.bdlistener
import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException

class DBHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "StudentsDB"
        private const val TABLE_CONTACTS = "Students"
        private const val KEY_NAME = "name"
        private const val KEY_WEIGHT = "weight"
        private const val KEY_HEIGHT = "height"
        private const val KEY_AGE = "age"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_NAME + " TEXT," + KEY_WEIGHT + " REAL,"
                + KEY_HEIGHT + " REAL," + KEY_AGE + " REAL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }
    //method to insert data
    public fun addStudent(student: DBObjectStudent):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, student.name)
        contentValues.put(KEY_HEIGHT, student.height)
        contentValues.put(KEY_WEIGHT, student.weight)
        contentValues.put(KEY_AGE, student.age)
        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to read data
    @SuppressLint("Range")
    fun viewStudent():ArrayList<DBObjectStudent>{
        val studentsList = ArrayList<DBObjectStudent>()
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch(e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        if(cursor.moveToFirst()) {
            do {
                var student = DBObjectStudent()
                student.name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                student.height = cursor.getDouble(cursor.getColumnIndex(KEY_WEIGHT))
                student.weight = cursor.getDouble(cursor.getColumnIndex(KEY_HEIGHT))
                student.age = cursor.getDouble(cursor.getColumnIndex(KEY_AGE))
                studentsList.add(student)
            } while(cursor.moveToNext())
        }
        return studentsList
    }
    //method to update data
    fun updateStudent(oldStudent: DBObjectStudent, newStudent: DBObjectStudent):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, newStudent.name)
        contentValues.put(KEY_WEIGHT, newStudent.weight)
        contentValues.put(KEY_HEIGHT, newStudent.height)
        contentValues.put(KEY_AGE, newStudent.age)

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"name = "+oldStudent.name,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to delete data
    fun deleteStudent(student: DBObjectStudent):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, student.name) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"name="+student.name,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun deleteAllStudents():Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE_CONTACTS,null,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}