package com.sqlite.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.sqlite.model.NewPerson
import com.sqlite.utils.Constants

class DataBaseMigration(private val context: Context) :
    SQLiteOpenHelper(
        context,
        Constants.DATA_BASE_MIGRATION_NAME,
        null,
        Constants.DATA_BASE_VERSION_TWO
    ) {

    override fun onCreate(database: SQLiteDatabase?) {
        val CREATE_PERSON_TABLE = "CREATE TABLE " + Constants.PERSON_TABLE + " (" +
                Constants.PERSON_ID + " INTEGER PRIMARY KEY," +
                Constants.PERSON_NAME + " TEXT," +
                Constants.PERSON_AGE + " TEXT)"

        database?.execSQL(CREATE_PERSON_TABLE)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.i("DataBaseMigration", "Migration method is called")
        if (newVersion > oldVersion) {
            database?.execSQL("ALTER TABLE ${Constants.PERSON_TABLE} ADD COLUMN ${Constants.PERSON_NUMBER} INTEGER default 0")
        }
    }

    companion object {

        private var dataBaseMigration: DataBaseMigration? = null

        fun getInstance(context: Context): DataBaseMigration {
            if (dataBaseMigration == null) {
                dataBaseMigration = DataBaseMigration(context)
            }
            return dataBaseMigration!!
        }
    }

    fun insertPersonData(id: Int, name: String, age: Double, number: Long) {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.PERSON_ID, id)
        contentValues.put(Constants.PERSON_NAME, name)
        contentValues.put(Constants.PERSON_AGE, age)
        contentValues.put(Constants.PERSON_NUMBER, number)
        db.insert(Constants.PERSON_TABLE, null, contentValues)
        db.close()
    }

    fun getPersonData(): List<NewPerson> {
        val personList = ArrayList<NewPerson>()
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
        var number: Long

        if (cursor!!.moveToFirst()) {
            do {
                id = cursor.getInt(0)
                name = cursor.getString(1)
                age = cursor.getDouble(2)
                number = cursor.getLong(3)
                personList.add(NewPerson(id, name, age, number))
            } while (cursor.moveToNext())
        }
        return personList
    }
}