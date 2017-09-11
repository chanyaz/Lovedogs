package br.com.tairoroberto.lovedogs.settings

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by tairo on 9/10/17.
 */
data class Configuracoes(var id: Long = 0) : Parcelable {

    constructor(source: Parcel) : this(
            source.readLong()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Configuracoes> = object : Parcelable.Creator<Configuracoes> {
            override fun createFromParcel(source: Parcel): Configuracoes = Configuracoes(source)
            override fun newArray(size: Int): Array<Configuracoes?> = arrayOfNulls(size)
        }
    }
}