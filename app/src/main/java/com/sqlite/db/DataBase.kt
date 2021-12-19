package com.sqlite.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.sqlite.model.Person
import com.sqlite.model.User
import com.sqlite.utils.Constants


class DataBase(private val context: Context) :
    SQLiteOpenHelper(context, Constants.DATA_BASE_NAME, null, Constants.DATA_BASE_VERSION) {

    override fun onCreate(dataBase: SQLiteDatabase?) {

        val CREATE_PERSON_TABLE = "CREATE TABLE " + Constants.PERSON_TABLE + " (" +
                Constants.PERSON_ID + " INTEGER PRIMARY KEY," +
                Constants.PERSON_NAME + " TEXT," +
                Constants.PERSON_AGE + " TEXT)"

        val CREATE_USER_TABLE = "CREATE TABLE " + Constants.USER_TABLE + " (" +
                Constants.USER_ID + " INTEGER PRIMARY KEY," +
                Constants.USER_NAME + " TEXT," +
                Constants.USER_AGE + " TEXT)"

        dataBase?.execSQL(CREATE_PERSON_TABLE)
        dataBase?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    companion object {

        private var dataBase: DataBase? = null

        fun getInstance(context: Context): DataBase {
            if (dataBase == null) {
                synchronized(this) {
                    dataBase = DataBase(context)
                }
            }
            return dataBase!!
        }
    }

    fun insertPersonData(id: Int, name: String, age: Double) {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.PERSON_ID, id)
        contentValues.put(Constants.PERSON_NAME, name)
        contentValues.put(Constants.PERSON_AGE, age)
        db.insert(Constants.PERSON_TABLE, null, contentValues)
        db.close()
    }

    fun insertUserData(id: Int, name: String, age: Double) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.USER_ID, id)
        contentValues.put(Constants.USER_NAME, name)
        contentValues.put(Constants.USER_AGE, age)
        database.insert(Constants.USER_TABLE, null, contentValues)
        database.close()
    }

    fun getPersonData(): List<Person> {
        val personList = ArrayList<Person>()
        val selectQuery = "SELECT * FROM ${Constants.PERSON_TABLE}"
        val database = readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = database.rawQuery(selectQuery, null)
        } catch (exception: SQLiteException) {
            Log.i("DataBase", exception.message.toString())
        }
        var id: Int
        var name: String
        var age: Double

        if (cursor!!.moveToFirst()) {
            do {
                id = cursor.getInt(0)
                name = cursor.getString(1)
                age = cursor.getDouble(2)
                personList.add(Person(id, name, age))
            } while (cursor.moveToNext())
        }
        return personList
    }

    fun getUserData(): List<User> {
        val userList = ArrayList<User>()
        val selectQuery = "SELECT * FROM ${Constants.USER_TABLE}"
        val database = readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = database.rawQuery(selectQuery, null)
        } catch (exception: SQLiteException) {
            Log.i("DataBase", exception.message.toString())
        }
        var id: Int
        var name: String
        var age: Double

        if (cursor!!.moveToFirst()) {
            do {
                id = cursor.getInt(0)
                name = cursor.getString(1)
                age = cursor.getDouble(2)
                userList.add(User(id, name, age))
            } while (cursor.moveToNext())
        }
        return userList
    }

    fun deleteAllPersonData() {
        val database = writableDatabase
        val deleteQuery = "delete from ${Constants.PERSON_TABLE}"
        database.execSQL(deleteQuery)
        database.close()

    }

    fun deleteAllUserData() {
        val database = writableDatabase
        val deleteQuery = "delete from ${Constants.USER_TABLE}"
        database.execSQL(deleteQuery)
        database.close()
    }

    fun updatePerson(id: Int, name: String, age: Double) {
        val database = writableDatabase
        //val updateQuery = "UPDATE ${Constants.PERSON_TABLE} SET ${Constants.PERSON_ID}='$id' WHERE id=1"
        //val updateQuery = "UPDATE ${Constants.PERSON_TABLE} SET ${Constants.PERSON_NAME}='$name' WHERE id=1"
        val updateQuery = "UPDATE ${Constants.PERSON_TABLE} SET ${Constants.PERSON_AGE}='$age' WHERE id=1"
        database.execSQL(updateQuery)

    }
}
