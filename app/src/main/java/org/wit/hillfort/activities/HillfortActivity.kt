package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel
import org.wit.placemark.helpers.readImage
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.helpers.showImagePicker

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    val IMAGE_REQUEST = 1
    var hillfort = HillfortModel()
    lateinit var app: MainApp

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

        }
    }
}
