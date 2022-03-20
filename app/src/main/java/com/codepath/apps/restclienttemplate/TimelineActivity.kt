package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

class TimelineActivity : AppCompatActivity() {

    lateinit var client: TwitterClient
    lateinit var rvTweets: RecyclerView
    lateinit var adapter: TweetsAdapter
    lateinit var swipeContainer: SwipeRefreshLayout
    val tweets = ArrayList<Tweet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.t_bird)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        swipeContainer = findViewById(R.id.swipeContainer)

        //swipe container for refresh
        swipeContainer.setOnRefreshListener {
            populateHomeTimeline()
        }

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

        client = TwitterApplication.getRestClient(this)

        rvTweets = findViewById(R.id.rvTweets)
        val btnTweet = findViewById<Button>(R.id.button)

        btnTweet.setOnClickListener(){
            Toast.makeText(this, "Button is reached", Toast.LENGTH_SHORT).show()
            intent = Intent(this, TweetActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        rvTweets.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
        adapter = TweetsAdapter(tweets, this)
        rvTweets.layoutManager = LinearLayoutManager(this)
        rvTweets.adapter = adapter
        populateHomeTimeline()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            val tweet = data?.getParcelableExtra("tweet") as Tweet
            tweets.add(0, tweet)
            adapter.notifyItemInserted(0)
            rvTweets.smoothScrollToPosition(0)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun populateHomeTimeline(){

        client.getTimeline(object:JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "Populate Success")
                try{
                    //clear list before refresh, otherwise will have duplicate tweets
                    adapter.clear()
                    val jsonArray = json.jsonArray
                    val listOfNewTweets = Tweet.fromJsonArray(jsonArray)
                    tweets.addAll(listOfNewTweets)
                    adapter.notifyDataSetChanged()
                    swipeContainer.setRefreshing(false)
                }catch(e: JSONException){
                    Log.e(TAG, "JSON exceptions: $e")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.i(TAG, "Timeline Failed: $statusCode")
            }

        })
    }
    companion object{
        val TAG = "TimelineActivity"
        val REQUEST_CODE = 10
    }
}