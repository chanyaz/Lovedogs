package br.com.tairoroberto.mypet.login.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import br.com.tairoroberto.mypet.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animFadein = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        val animation = AnimationSet(true)

        animation.interpolator = AccelerateInterpolator()
        animation.addAnimation(animFadein)

        img1.startAnimation(animation)
        img2.startAnimation(animation)
        img3.startAnimation(animation)
        img4.startAnimation(animation)
        img5.startAnimation(animation)
        img6.startAnimation(animation)
        img7.startAnimation(animation)
        img8.startAnimation(animation)
        img9.startAnimation(animation)
        imageSplash.startAnimation(animation)

        imageSplash.visibility = View.VISIBLE
    }
}
