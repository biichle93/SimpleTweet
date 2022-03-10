package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class DetailedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        supportActionBar?.title = "Tweet"

        val tweet = findViewById<TextView>(R.id.tvTweet)
        val name = findViewById<TextView>(R.id.tvUsername)
        val thandle = findViewById<TextView>(R.id.tvTwitterHandle)
        val userImage = findViewById<ImageView>(R.id.ivProfileImage)

        val username = intent.getStringExtra("USERNAME")
        val handle = intent.getStringExtra("HANDLE")
        val tweetbody = intent.getStringExtra("TWEET")
        val profileimage = intent.getStringExtra("PROFILE_IMAGE")

        tweet.text = tweetbody
        name.text = username
        thandle.text = handle
        Glide.with(this).load(profileimage).transform(CircleCrop()).into(userImage)


    }
}