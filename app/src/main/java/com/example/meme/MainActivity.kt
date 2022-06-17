package com.example.meme

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentMemeUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LoadMeme()
    }
    private fun LoadMeme(){
        val a: ImageView =findViewById(R.id.imageView)
        val progressBar: ProgressBar =findViewById(R.id.progressBar)
        val queue = Volley.newRequestQueue(this)
        progressBar.visibility = View.VISIBLE
        val url1 = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url1, null,
            { response ->
                currentMemeUrl= response["url"] as String?
                Glide.with(this).load(currentMemeUrl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(a)
            },
            { error ->
                Toast.makeText(this,"Cool not",Toast.LENGTH_LONG).show()            }
        )

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest)
    }
    fun shareMeme(view: View) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_TEXT, "Hi, checkout this meme $currentMemeUrl")
        startActivity(Intent.createChooser(i, "Share this meme with"))
    }
    fun showNextMeme(view: View) {
        LoadMeme()
    }
}