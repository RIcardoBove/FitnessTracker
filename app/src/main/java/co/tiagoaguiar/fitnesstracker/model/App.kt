package co.tiagoaguiar.fitnesstracker.model

import android.app.Application

class App : Application() {

    lateinit var db: AppDataBase

    override fun onCreate() {
        super.onCreate()
        db = AppDataBase.getDataBase(this)
    }
}