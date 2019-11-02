package org.wit.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.models.HillfortJSONStore
import org.wit.hillfort.models.HillfortStore
import org.wit.hillfort.models.UserJSONStore
import org.wit.hillfort.models.UserStore

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore
    lateinit var users: UserStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)
        // add default user
        //users.create(UserModel("homer@simpson.com", "secret"))
        info("Hillfort App Started")
    }
}