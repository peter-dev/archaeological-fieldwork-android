package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.Location
import org.wit.placemark.helpers.readImage
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.helpers.showImagePicker

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var hillfort = HillfortModel()
    lateinit var app: MainApp

    private fun goToLoginActivity() {
        // go to LoginActivity and dismiss the current view
        app.currentUser = null
        startActivity<LoginActivity>()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        // enable the action bar (toolbar title defaults to the application name)
        setSupportActionBar(toolbar_editHillfort)
        // get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app = application as MainApp

        var editMode = false

        // retrieve the hillfort, and place its field into the view controls
        if (intent.hasExtra("hillfort_edit")) {
            editMode = true
            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            txt_hillfortName.setText(hillfort.name)
            txt_hillfortDescription.setText(hillfort.description)
            btn_EditHillfort.setText(R.string.button_editHillfort)
            // load image from image path
            img_hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
            // when image it detected, change the label (change image)
            if (hillfort.image.isNotEmpty()) {
                btn_addImage.setText(R.string.button_changeImage)
            }
        }

        // register a listener to a button click event (add / save)
        btn_EditHillfort.setOnClickListener {
            hillfort.name = txt_hillfortName.text.toString()
            hillfort.description = txt_hillfortDescription.text.toString()
            if (hillfort.name.isEmpty()) {
                toast(R.string.toast_enterHillfordName)
            } else {
                if (editMode) {
                    app.hillforts.update(hillfort.copy())
                } else {
                    app.hillforts.create(hillfort.copy())
                }
                // finish the activity and set a result code OK (-1)
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }

        }

        // register a listener to a button click event (add image)
        btn_addImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        // register a listener to a button click event (set location)
        btn_setLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (hillfort.zoom != 0f) {
                location.lat =  hillfort.lat
                location.lng = hillfort.lng
                location.zoom = hillfort.zoom
            }
            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }
    }

    // load the menu resource (inflate the menu)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // the menu event handler
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            // if the event is item_cancel, it should dismiss the HillfortActivity
            R.id.item_cancel -> finish()
            // if the event is item_delete, it should delete the item and dismiss the HillfortActivity
            R.id.item_delete -> {
                app.hillforts.delete(hillfort.copy())
                finish()
            }
            // if the event is item_logout, it should go to LoginActivity and dismiss the current view
            R.id.item_logout -> goToLoginActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    // this lifecycle event is to be triggered when an activity we have started finishes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    // recover the image reference when the IMAGE_REQUEST is seen
                    hillfort.image = data.getData().toString()
                    // display the image in the image view
                    img_hillfortImage.setImageBitmap(readImage(this, resultCode, data))
                    // when an image is loaded, change the label (change image)
                    btn_addImage.setText(R.string.button_changeImage)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    // when a result is returned, recover the location
                    val location = data.extras?.getParcelable<Location>("location")!!
                    hillfort.lat = location.lat
                    hillfort.lng = location.lng
                    hillfort.zoom = location.zoom
                }
            }

        }
    }
}
