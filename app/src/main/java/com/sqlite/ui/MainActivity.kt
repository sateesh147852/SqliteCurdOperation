package com.sqlite.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sqlite.R
import com.sqlite.db.DataBase

class MainActivity : AppCompatActivity() {

    private lateinit var dataBase: DataBase
    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
        addData()
        //updatePersonData()
        readPersonData()
        readUserData()
    }

    private fun initialize() {
        dataBase = DataBase.getInstance(this)
    }

    private fun addData() {
        dataBase.insertPersonData(1, "Sateesh", 28.0)
        dataBase.insertPersonData(2, "Ramesh", 30.0)
        dataBase.insertPersonData(3, "Kartik", 32.0)

        dataBase.insertUserData(1, "Alien", 25.0)
        dataBase.insertUserData(2, "PK", 27.0)
    }

    private fun deletePersonData() {
        dataBase.deleteAllPersonData()
    }

    private fun updatePersonData() {
        dataBase.updatePerson(1, "SATEESH", 20.0)
    }

    private fun readPersonData() {
        dataBase.getPersonData().forEach {
            Log.i(TAG, it.toString())
        }
    }

    private fun readUserData() {
        dataBase.getUserData().forEach {
            Log.i(TAG, it.toString())
        }
    }

}