package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter( private val listner : NewsItemClicked) : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView : TextView = itemView.findViewById(R.id.title)
        val image : ImageView = itemView.findViewById(R.id.image)
        val author : TextView = itemView.findViewById(R.id.author)

    }
    val items : ArrayList<News> = ArrayList()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder =NewsViewHolder(view)
        view.setOnClickListener{
            listner.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currntItem = items[position]
        holder.titleView.text = currntItem.title
        holder.author.text = currntItem.author
        Glide.with(holder.itemView.context).load(currntItem.imageUrl).into(holder.image)


    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateNews(UpdatedNews : ArrayList<News>){
        items.clear()
        items.addAll(UpdatedNews)
        notifyDataSetChanged()
    }

}
interface NewsItemClicked{
     fun onItemClicked(item : News)
}