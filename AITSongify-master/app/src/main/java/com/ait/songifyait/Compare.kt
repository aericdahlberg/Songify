package com.ait.songifyait

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.ait.songifyait.api.Authorization
import com.ait.songifyait.api.SongInformation
import com.ait.songifyait.api.TrackFeatures
import com.ait.songifyait.data.*
import com.ait.songifyait.databinding.ActivityCompareBinding
import com.ait.songifyait.dialog.Dialog
import kotlinx.android.synthetic.main.activity_compare.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Math.abs
import retrofit2.converter.gson.GsonConverterFactory

class Compare : AppCompatActivity() {

    lateinit var compareBinding: ActivityCompareBinding


    var energy1 = 0.0
    var danceAbility1 = 0.0
    var instrumentalness1 = 0.0
    var key1 = 0.0
    var loudness1 = 0.0
    var timesignature1 = 0.0
    var valence1 = 0.0
    var tempo1 = 0.0
    var energy2 = 0.0
    var danceAbility2 = 0.0
    var instrumentalness2 = 0.0
    var key2 = 0.0
    var loudness2 = 0.0
    var timesignature2 = 0.0
    var valence2 = 0.0
    var tempo2 = 0.0
    var Points = 0.0
    var Percentage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compareBinding = ActivityCompareBinding.inflate(layoutInflater)
        setContentView(compareBinding.root)


        var firstURI = intent.getStringExtra(Dialog.FIRST_URL).toString()
        var secondURI = intent.getStringExtra(Dialog.SECOND_URL).toString()
        getAuthorization(firstURI, secondURI)

        val iconView1 = findViewById<ImageView>(R.id.ivIcon1)
        val videoView = findViewById<VideoView>(R.id.videoback)
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.loop )
        videoView.setVideoURI(videoUri)
        videoView.start()
        var mp = MediaPlayer.create(this, videoUri)
        mp.isLooping = true

        val imageView = findViewById<TextView>(R.id.tvComparisonValue)
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, PropertyValuesHolder.ofFloat("scaleX", 1.1f),
            PropertyValuesHolder.ofFloat("scaleY", 1.1f))
        objectAnimator.setDuration(310)
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE)
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE)
        objectAnimator.setInterpolator(FastOutSlowInInterpolator())
        objectAnimator.start()

        iconView1.visibility =View.INVISIBLE
        if(Percentage < 25.toString()){
            iconView1.visibility = View.VISIBLE
        }

    }
    fun getAuthorization(firstURI : String, secondURI : String) {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var authorizationAPI = retrofit.create(Authorization::class.java)
        val call = authorizationAPI.getToken()

        call.enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                var authorizationResult = response.body()
                val TOKEN = authorizationResult?.access_token.toString()

                getTrackFeatures(TOKEN, firstURI, secondURI)
                getSongInformation(TOKEN, firstURI, secondURI)
                getSongImage(TOKEN, firstURI, secondURI)

            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun comparision() {
        if (abs(danceAbility1 - danceAbility2) < .25) {
            Points += 1
        }
        if (abs(energy1 - energy2) < .25) {
            Points += 1
        }

        if (abs(instrumentalness1 - instrumentalness2) < .2) {
            Points += 1
        }
        if (abs(key1 - key2) < 3) {
            Points += 1
        }
        if (abs(loudness1 - loudness2) < 20) {
            Points += 1
        }
        if (abs(timesignature1 - timesignature2) < 3) {
            Points += 1
        }
        if (abs(valence1 - valence2) < .25) {
            Points += 1
        }
        if (abs(tempo1 - tempo2) < 34) {
            Points += 1
        }
        Percentage = ((Points * 12.5).toString() + "%")
    }

    fun getTrackFeatures(Token : String, firstURI: String, secondURI: String){
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var spotifyAPI = retrofit.create(TrackFeatures::class.java)
        val call = spotifyAPI.getTrackFeatures("Bearer "+Token, firstURI)
        val secondCall = spotifyAPI.getTrackFeatures("Bearer "+Token, secondURI)

        call.enqueue(object : Callback<AudioFeatures>{

            override fun onResponse(call: Call<AudioFeatures>, response: Response<AudioFeatures>) {
                var spotifyResult = response.body()
                energy1 = spotifyResult?.energy!!.toDouble()

                danceAbility1= spotifyResult?.danceability!!.toDouble()
                instrumentalness1= spotifyResult?.instrumentalness!!.toDouble()
                key1=spotifyResult?.key!!.toDouble()
                loudness1=spotifyResult?.loudness!!.toDouble()
                timesignature1=spotifyResult?.time_signature!!.toDouble()
                valence1=spotifyResult?.valence!!.toDouble()
                tempo1= spotifyResult?.tempo!!.toDouble()


                //compareBinding.tvSong1.text = "Acousticness: ${spotifyResult?.acousticness}"
            }
            override fun onFailure(call: Call<AudioFeatures>, t: Throwable) {
                TODO("Not yet implemented")

            }

        })
        secondCall.enqueue(object : Callback<AudioFeatures>{

            override fun onResponse(call: Call<AudioFeatures>, response: Response<AudioFeatures>) {

                var spotifyResult = response.body()
//
                energy2 = spotifyResult?.energy!!.toDouble()
                danceAbility2= spotifyResult?.danceability!!.toDouble()
                instrumentalness2= spotifyResult?.instrumentalness!!.toDouble()
                key2=spotifyResult?.key!!.toDouble()
                loudness2=spotifyResult?.loudness!!.toDouble()
                timesignature2=spotifyResult?.time_signature!!.toDouble()
                valence2=spotifyResult?.valence!!.toDouble()
                tempo2= spotifyResult?.tempo!!.toDouble()
                comparision()


                compareBinding.tvComparisonValue.text=Percentage
            }
            override fun onFailure(call: Call<AudioFeatures>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getSongInformation(Token : String, firstURI: String, secondURI: String) {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var spotifyAPI = retrofit.create(SongInformation::class.java)
        val call = spotifyAPI.getSongInformation("Bearer " + Token, firstURI)
        val secondCall = spotifyAPI.getSongInformation("Bearer " + Token, secondURI)

        call.enqueue(object : Callback<artistInfo> {

            override fun onResponse(call: Call<artistInfo>, response: Response<artistInfo>) {
                var spotifyResult = response.body()
                var name = spotifyResult?.name

                compareBinding.tvSong1.text = name
                Log.d("NAME", "The name is : ${name}")


            }

            override fun onFailure(call: Call<artistInfo>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        secondCall.enqueue(object : Callback<artistInfo> {

            override fun onResponse(call: Call<artistInfo>, response: Response<artistInfo>) {
                var spotifyResult = response.body()
                var name = spotifyResult?.name
                compareBinding.tvSong2.text = name
                Log.d("NAME", "The name is : ${name}")
            }

            override fun onFailure(call: Call<artistInfo>, t: Throwable) {
                TODO("Not yet implemented")

            }
        })
    }
    fun getSongImage(Token : String, firstURI: String, secondURI: String) {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var spotifyAPI = retrofit.create(SongInformation::class.java)
        val call = spotifyAPI.getSongImage("Bearer " + Token, firstURI)
        val secondCall = spotifyAPI.getSongInformation("Bearer " + Token, secondURI)

        call.enqueue(object : Callback<Images> {

            override fun onResponse(call: Call<Images>, response: Response<Images>) {
                var spotifyResult = response.body()
                var name = spotifyResult?.url
                //compareBinding.tvArtist1.text = name + "Artist name:" +
                //"Income: " + keys?.getString("income")

                //Log.d("NAME", "The artist name is : ${name}")
            }

            override fun onFailure(call: Call<Images>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}
