package br.com.tairoroberto.lovedogs.login.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import br.com.tairoroberto.lovedogs.R
import br.com.tairoroberto.lovedogs.base.extension.getConfig
import br.com.tairoroberto.lovedogs.settings.ConfigDAO
import br.com.tairoroberto.lovedogs.settings.Configuracoes
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val changeBounds =  ChangeBounds()
            changeBounds.duration = 2000
            window.sharedElementExitTransition = changeBounds
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getConfig()

        FirebaseMessaging.getInstance().subscribeToTopic("android")

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startAnimation(img1)
            }
        }, 600)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startAnimation(img2)
            }
        }, 1000)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startAnimation(img3)
            }
        }, 1200)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startAnimation(img4)
            }
        }, 1700)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startAnimation(img5)
            }
        }, 2000)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startAnimation(img6)
            }
        }, 2300)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startAnimation(img7)
            }
        }, 2600)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startAnimation(img8)
            }
        }, 2800)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startAnimation(img9)
            }
        }, 3100)
    }

    fun startAnimation(imageView: ImageView) {
        runOnUiThread {
            imageView.visibility = View.VISIBLE

            if (imageView.id == img9.id) {
                img1.visibility = View.GONE
                img2.visibility = View.GONE
                img3.visibility = View.GONE
                img4.visibility = View.GONE
                img5.visibility = View.GONE
                img6.visibility = View.GONE
                img7.visibility = View.GONE
                img8.visibility = View.GONE
                img9.visibility = View.GONE

                val slide_up = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up)
                imageSplash.visibility = View.VISIBLE
                //textView.visibility = View.VISIBLE
                imageSplash.startAnimation(slide_up)

                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this@SplashActivity,
                                    Pair.create(imageSplash, "profile_photo"))

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                startActivity(Intent(this@SplashActivity, LoginActivity::class.java), options.toBundle())
                            } else {
                                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                            }

                            finish()
                        }
                    }
                }, 1500)
            }
        }
    }
}
