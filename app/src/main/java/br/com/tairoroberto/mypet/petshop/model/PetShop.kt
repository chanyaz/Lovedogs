package br.com.tairoroberto.mypet.petshop.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by tairo on 8/22/17.
 */
data class PetShop(val title: String, val subTitle: String, val image: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(subTitle)
        writeString(image)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PetShop> = object : Parcelable.Creator<PetShop> {
            override fun createFromParcel(source: Parcel): PetShop = PetShop(source)
            override fun newArray(size: Int): Array<PetShop?> = arrayOfNulls(size)
        }
    }
}