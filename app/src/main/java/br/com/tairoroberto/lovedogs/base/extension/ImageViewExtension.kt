package br.com.tairoroberto.lovedogs.base.extension

import android.widget.ImageView
import br.com.tairoroberto.lovedogs.R
import com.squareup.picasso.Picasso


/**
 * Created by tairo on 9/2/17.
 */

fun ImageView.loadImage(url: String?) {
    Picasso.with(context).load(url).placeholder(R.drawable.animais).into(this)
}