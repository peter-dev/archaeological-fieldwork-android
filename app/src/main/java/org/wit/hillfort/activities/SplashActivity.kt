package org.wit.hillfort.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.startActivity
import org.wit.hillfort.R

class SplashActivity : AppCompatActivity() {

    private val splashTime = 3000L // 3 seconds
    private lateinit var myHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // use a Handler to add a small delay and then go to the LoginActivity
        myHandler = Handler()

        myHandler.postDelayed({
            goToLoginActivity()
        }, splashTime)
    }

    private fun goToLoginActivity() {
        // go to LoginActivity and dismiss the current view
        startActivity<LoginActivity>()
        finish()
    }
}