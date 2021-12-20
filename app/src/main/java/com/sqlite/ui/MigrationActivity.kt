package com.sqlite.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sqlite.databinding.ActivityMigrationBinding
import com.sqlite.db.DataBaseMigration

class MigrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMigrationBinding
    private lateinit var dataBaseMigration: DataBaseMigration
    private val TAG = "MigrationActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMigrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeDatabase()
    }

    private fun initializeDatabase() {

        dataBaseMigration = DataBaseMigration.getInstance(this)

        dataBaseMigration.insertPersonData(5, "Sateesh", 28.0,9686025367)
        dataBaseMigration.insertPersonData(6, "Suresh", 29.0,8618378828)

        dataBaseMigration.getPersonData().forEach {
            Log.i(TAG, it.toString())
        }
    }


}