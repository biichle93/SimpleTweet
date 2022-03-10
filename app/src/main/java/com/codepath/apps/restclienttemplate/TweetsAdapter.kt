package com.codepath.apps.restclienttemplate

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.codepath.apps.restclienttemplate.models.Tweet

const val TWEET_EXTRA = "TWEET_EXTRA"
class TweetsAdapter(private val tweets: MutableList<Tweet>,
                    private val context: Context)
    : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_tweet, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetsAdapter.ViewHolder, position: Int) {
        val tweet: Tweet = tweets[position]

        holder.tweetBody.text = tweet.body
        holder.profileName.text = tweet.user?.profileName
        holder.name.text = tweet.user?.name
        holder.timestamp.text = tweet.timeStamp
        Glide.with(holder.itemView).load(tweet.user?.profilePictureURL).transform(CircleCrop()).into(holder.profileImage)
        //Glide.with(holder.itemView).load(tweet.user?.profilePictureURL).into(holder.profileImage)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val profileImage = itemView.findViewById<ImageView>(R.id.ivProfileImage)
        val tweetBody = itemView.findViewById<TextView>(R.id.tvTweet)
        val profileName = itemView.findViewById<TextView>(R.id.tvTwitterHandle)
        val name = itemView.findViewById<TextView>(R.id.tvUsername)
        val timestamp = itemView.findViewById<TextView>(R.id.tvTime)

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val tweetClicked = tweets[bindingAdapterPosition]
            val intent = Intent(context, DetailedActivity::class.java)
            intent.putExtra("USERNAME", tweetClicked.user?.name)
            intent.putExtra("HANDLE", tweetClicked.user?.profileName)
            intent.putExtra("TWEET", tweetClicked.body)
            intent.putExtra("PROFILE_IMAGE", tweetClicked.user?.profilePictureURL)
            context.startActivity(intent)
        }
    }

    // Clean all elements of the recycler
    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(tweetList: List<Tweet>) {
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }

}