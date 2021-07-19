package com.example.newsapp

import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter : NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.layoutManager = LinearLayoutManager(this)
        fetch()
        mAdapter = NewsListAdapter(this)
        recycler.adapter = mAdapter


        
    }
    private fun fetch(){
        val url = "https://saurav.tech/NewsAPI/everything/cnn.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                Log.d("Hii", "fetch: in object")

          val newsJsonArray =  it.getJSONArray("articles")
                Log.d("Hii", "fetch: $newsJsonArray")

            val newsArray = ArrayList<News>()
                for (i in 0 until  newsJsonArray.length()){


                    val newsJsonObject =  newsJsonArray.getJSONObject(i)
                    Log.d("Hii", "fetch: $newsJsonObject")
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")

                    )
                    newsArray.add(news)
                    Log.d("Hii", "fetch: $news")

                }
                mAdapter.updateNews(newsArray)


            }, {
                Log.d("Hii", "fetch: Error")
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

    override fun onItemClicked(item: News) {
       val builder = CustomTabsIntent.Builder()
        val customTabsIntent  = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }
}