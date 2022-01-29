package com.ait.songifyait

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.ait.songifyait.data.URL
import com.ait.songifyait.databinding.ActivityMainBinding
import com.ait.songifyait.dialog.Dialog
import com.skyfishjy.library.RippleBackground
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Dialog.URLHandler{
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView = findViewById<ImageView>(R.id.image_view)

        val rippleView = findViewById<RippleBackground>(R.id.ripple)
        rippleView.startRippleAnimation()
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, PropertyValuesHolder.ofFloat("scaleX", 1.1f),
        PropertyValuesHolder.ofFloat("scaleY", 1.1f))
        objectAnimator.setDuration(310)
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE)
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE)
        objectAnimator.setInterpolator(FastOutSlowInInterpolator())
        objectAnimator.start()

        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.apply{
            setEnterFadeDuration(1000)
            setExitFadeDuration(2000)
            start()
        }


        binding.btnSearch.setOnClickListener {
            Dialog().show(supportFragmentManager, "DIALOG")
        }
    }

    override fun urlCreated(url: URL) {
        TODO("Not yet implemented")
    }



}