package org.wit.placemark.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.io.IOException

// helper function to show an image picker dialog
//fun showImagePicker(parent: Activity, id: Int) {
//    val intent = Intent()
//    intent.type = "image/*"
//    intent.action = Intent.ACTION_OPEN_DOCUMENT
//    intent.addCategory(Intent.CATEGORY_OPENABLE)
//    val chooser = Intent.createChooser(intent, R.string.select_placemark_image.toString())
//    parent.startActivityForResult(chooser, id)
//}

// helper function to display the image
fun readImage(activity: Activity, resultCode: Int, data: Intent?): Bitmap? {
    var bitmap: Bitmap? = null
    if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
        try {
            bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, data.data)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return bitmap
}

// helper function to load the image given its file name
fun readImageFromPath(context: Context, path : String) : Bitmap? {
    var bitmap : Bitmap? = null
    val uri = Uri.parse(path)
    if (uri != null) {
        try {
            val parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor?.getFileDescriptor()
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor?.close()
        } catch (e: Exception) {
        }
    }
    return bitmap
}