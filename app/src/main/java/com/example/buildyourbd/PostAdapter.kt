package com.example.buildyourbd


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(private val posts: MutableList<String>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val postText: TextView = view.findViewById(R.id.postTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.postText.text = posts[position]
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun agregarPost(post: String) {
        posts.add(post)
        notifyItemInserted(posts.size - 1)
    }
}
