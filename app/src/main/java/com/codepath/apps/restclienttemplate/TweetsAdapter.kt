package com.codepath.apps.restclienttemplate

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


class TweetsAdapter(val tweets: MutableList<Tweet>): RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {
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

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val profileImage = itemView.findViewById<ImageView>(R.id.ivProfileImage)
        val tweetBody = itemView.findViewById<TextView>(R.id.tvTweet)
        val profileName = itemView.findViewById<TextView>(R.id.tvTwitterHandle)
        val name = itemView.findViewById<TextView>(R.id.tvUsername)
        val timestamp = itemView.findViewById<TextView>(R.id.tvTime)
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