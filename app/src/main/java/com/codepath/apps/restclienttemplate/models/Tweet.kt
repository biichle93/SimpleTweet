package com.codepath.apps.restclienttemplate.models

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import org.json.JSONObject
@Parcelize
class Tweet(var body: String = "",var createdAt: String = "",var user: User? = null, var timeStamp: String = "·"):
    Parcelable {

    companion object{
        fun fromJson(jsonObject: JSONObject): Tweet{
            val tweet = Tweet()
            tweet.body = jsonObject.getString("full_text")
            tweet.createdAt =jsonObject.getString("created_at")
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"))
            tweet.timeStamp = tweet.timeStamp + getFormattedTimestamp(jsonObject.getString("created_at"))
            return tweet
        }
        fun fromJsonArray(jsonArray: JSONArray): List<Tweet> {
            val tweets: MutableList<Tweet> = mutableListOf()
            for(i in 0 until jsonArray.length()){
                tweets.add(fromJson(jsonArray.getJSONObject(i)))
            }
            return tweets
        }
        fun getFormattedTimestamp(string: String): String {
            return TimeFormatter.getTimeDifference(string)
        }
    }
    }