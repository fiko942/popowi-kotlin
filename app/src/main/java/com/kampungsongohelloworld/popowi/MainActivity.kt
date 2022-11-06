package com.kampungsongohelloworld.popowi

import android.content.Context
import android.content.DialogInterface
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    var mMediaPlayer: MediaPlayer? = null
    var totalClick = 0


    private fun showAlert(title: String, message: String): Boolean {
        var alertDialog = AlertDialog.Builder(this)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        alertDialog.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Ok") { _: DialogInterface?, _: Int -> }
        }.create().show()
        return true
    }

    fun playKagetSound(elTotalClick: TextView): Boolean {
        // get the volume level
        val sound = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if(sound.getStreamVolume(AudioManager.STREAM_MUSIC) <= 0) {
            this.showAlert(
                resources.getString(R.string.volume_notice_title),
                resources.getString(R.string.volume_notice_message)
            )
        }

        if(mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.kaget)
            mMediaPlayer!!.isLooping = false
        }
        mMediaPlayer?.start()
        totalClick+=1
        elTotalClick.text = resources.getString(R.string.total_click, totalClick.toString())
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Elements
        val jokowiKagetImageView: ImageView = findViewById(R.id.jokowiKagetImageView)
        val totalClickTextView: TextView = findViewById(R.id.totalClickTextView)

        jokowiKagetImageView.setOnClickListener { it: View? ->
            jokowiKagetImageView.setImageResource(R.drawable.jokowi_kaget)
            playKagetSound(totalClickTextView)
            CoroutineScope(Dispatchers.IO).launch {
                delay(500)
                withContext(Dispatchers.Main) {
                    jokowiKagetImageView.setImageResource(R.drawable.jokowi)
                }
            }
        }
    }
}