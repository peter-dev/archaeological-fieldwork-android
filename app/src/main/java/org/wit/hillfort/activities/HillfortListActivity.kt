package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel


class HillfortListActivity : AppCompatActivity(), HillfortListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        // enable the action bar (toolbar title defaults to the application name, override it)
        toolbar_listHillfort.setTitle(R.string.toolbar_hilforts)
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

    // the menu event handler
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            // if the event is item_add, it starts the HillfortActivity
            R.id.item_add -> startActivityForResult<HillfortActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    // implementation of the HillfortListener
    override fun onHillfortClick(hillfort: HillfortModel) {
        // pass the selected hillfort to the activity, this is enabled via the parcelable mechanism
        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
    }

    // this lifecycle event is to be triggered when an activity we have started finishes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // instruct the recyclerView's adapter that the model has been updated
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

}
