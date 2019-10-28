package org.wit.hillfort.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.intentFor
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel


class HillfortListActivity : AppCompatActivity(), HillfortListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        // enable the action bar (toolbar title defaults to the application name)
        setSupportActionBar(toolbar_listHillfort)

        app = application as MainApp

        // include the RecyclerView + Adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = HillfortAdapter(app.hillforts.findAll(), this)
    }

    // load the menu resource
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillforts_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // implementation of the HillfortListener
    override fun onHillfortClick(hillfort: HillfortModel) {
        // pass the selected hillfort to the activity, this is enabled via the parcelable mechanism
        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
    }

}
