package org.wit.hillfort.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.UserModel

class LoginActivity : AppCompatActivity(), AnkoLogger {

    val emailPattern: Regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    lateinit var user: UserModel
    lateinit var app: MainApp

    private fun goToHillfortListActivity() {
        // go to HillfortListActivity and dismiss the current view
        app.currentUser = user
        startActivity<HillfortListActivity>()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // enable the action bar (toolbar title defaults to the application name, override it)
        toolbar_authenticate.setTitle(R.string.toolbar_login)
        setSupportActionBar(toolbar_authenticate)

        app = application as MainApp

        // register a listener to a button click event (sign up / login)
        btn_authenticate.setOnClickListener {
            val email = txt_email.text.toString().trim()
            val password = txt_password.text.toString().trim()
            // validate fields
            if (email.isEmpty() || password.isEmpty()) {
                toast(R.string.toast_enterLoginDetails)
            } else if (!email.matches(emailPattern)) {
                toast(R.string.toast_InvalidEmail)
            } else {
                // authenticate
                val userFound = app.users.findOneByEmail(email)
                // user found
                if (userFound != null) {
                    // email address and password match
                    if (userFound.comparePassword(password)) {
                        user = UserModel(email, password)
                        toast(R.string.toast_UserLoggedIn)
                        goToHillfortListActivity()
                    } else {
                        // password mismatch
                        toast(R.string.toast_InvalidPassword)
                        txt_password.setText("")
                    }
                } else {
                    // user not found, add new user
                    user = UserModel(email, password)
                    app.users.create(user)
                    toast(R.string.toast_UserSingedUp)
                    goToHillfortListActivity()
                }
            }
        }
    }

}
