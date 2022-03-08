package com.codepath.apps.restclienttemplate.models

import org.json.JSONObject

class User {
    var name : String = ""
    var profileName: String = ""
    var profilePictureURL: String = ""

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