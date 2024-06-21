package com.dicoding.yieldsage.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserModel(
    var userId: String,
    var name: String,
    var email: String,
    var token: String,
    var isLogin: Boolean = false
):Parcelable