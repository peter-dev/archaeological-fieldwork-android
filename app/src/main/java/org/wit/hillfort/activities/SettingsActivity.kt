package org.wit.hillfort.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.txt_email
import kotlinx.android.synthetic.main.activity_login.txt_password
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.UserModel

class SettingsActivity : AppCompatActivity(), AnkoLogger {

    lateinit var user: UserModel
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        // enable the action bar (toolbar title defaults to the application name, override it)
        toolbar_settings.setTitle(R.string.toolbar_settings)
        setSupportActionBar(toolbar_settings)
        // get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app = application as MainApp

        // retrieve the user, and place its field into the view controls
        user = intent.extras?.getParcelable<UserModel>("user_details")!!

        txt_email.setText(user.email)
        txt_email.isEnabled = false
        txt_password.setText(user.pasword)
        txt_password.isEnabled = false
        // make password visible
        txt_password.setTransformationMethod(null)

    }

}
