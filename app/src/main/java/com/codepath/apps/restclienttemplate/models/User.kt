package com.codepath.apps.restclienttemplate.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
@Parcelize
class User(var name : String = "",var profileName: String = "", var profilePictureURL: String = ""):
Parcelable{

    companion object{
        fun fromJson(jsonObject: JSONObject): User {
            val user = User()
            user.name = jsonObject.getString("name")
            user.profileName = "@"+ jsonObject.getString("screen_name")
            user.profilePictureURL = jsonObject.getString("profile_image_url_https")
            return user
        }
    }
}