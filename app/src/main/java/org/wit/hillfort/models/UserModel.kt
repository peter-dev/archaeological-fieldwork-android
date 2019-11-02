package org.wit.hillfort.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    var email: String,
    var pasword: String
) : Parcelable {
    fun comparePassword(validatePassword: String): Boolean {
        return validatePassword == pasword
    }
}