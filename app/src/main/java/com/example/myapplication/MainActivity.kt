package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val urlBase= "https://jsonplaceholder.typicode.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit= Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PostApiService::class.java)

        lifecycleScope.launch {
            try {
                val response = service.getUserPost()

                val titlesAndIds = response.map { "ID: ${it.id}, Title: ${it.title}" }
                val allTitlesAndIds = titlesAndIds.joinToString("\n")

                runOnUiThread {
                    val textView = findViewById<TextView>(R.id.tv_title)
                    textView.text = allTitlesAndIds
                }
            } catch (e: Exception) {

                runOnUiThread {
                    val textView = findViewById<TextView>(R.id.tv_title)
                    textView.text = "Error: ${e.message}"
                }
            }
        }
    }
}