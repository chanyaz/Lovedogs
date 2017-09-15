package br.com.tairoroberto.lovedogs.settings

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by tairo on 9/10/17.
 */
class Configuracoes : Parcelable {
    var id: Long = 0

    var user: String = ""

    var password: String = ""

    var notification: Boolean = false

    var sound_notification: String = ""

    var vibrate: Boolean = false

    var share: Boolean = false

    constructor(source: Parcel) : this(
    )

    constructor()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Configuracoes> = object : Parcelable.Creator<Configuracoes> {
            override fun createFromParcel(source: Parcel): Configuracoes = Configuracoes(source)
            override fun newArray(size: Int): Array<Configuracoes?> = arrayOfNulls(size)
        }
    }
}