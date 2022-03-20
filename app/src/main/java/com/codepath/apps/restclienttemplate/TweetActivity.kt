package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class TweetActivity : AppCompatActivity() {

    lateinit var client: TwitterClient
    lateinit var btnTweet: Button
    lateinit var etTweet: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.t_bird)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        btnTweet = findViewById<Button>(R.id.btnTweet)
        etTweet = findViewById<EditText>(R.id.etTweet)

        client = TwitterApplication.getRestClient(this)

        btnTweet.setOnClickListener(){
            val tweetBody = etTweet.text.toString()
            if(tweetBody.isEmpty()){
                Toast.makeText(this,"Tweet cannot be empty", Toast.LENGTH_SHORT).show()
            }else if(tweetBody.length > 140){
                Toast.makeText(this,"Too many characters(Max: 140)", Toast.LENGTH_SHORT).show()
            }else{
                client.publishTweet(tweetBody,object: JsonHttpResponseHandler(){

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                        Log.i(TAG,"Successfully published tweet!")
                        val tweet = Tweet.fromJson(json.jsonObject)
                        val intent = Intent()
                        intent.putExtra("tweet", tweet)
                        setResult(RESULT_OK, intent)
                        finish()
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.e(TAG, "failed to publish tweet")
                    }

                })
            }
        }
    }companion object{
        const val TAG = "TweetActivity"
    }
}