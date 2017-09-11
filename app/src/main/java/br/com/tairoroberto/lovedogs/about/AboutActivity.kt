package br.com.tairoroberto.lovedogs.about

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.tairoroberto.lovedogs.BuildConfig
import br.com.tairoroberto.lovedogs.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        textViewVersion.text = BuildConfig.VERSION_NAME
    }
}
