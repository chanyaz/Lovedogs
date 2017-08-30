package br.com.tairoroberto.mypet.petshop.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by tairo on 8/22/17.
 */
data class PetShop(@SerializedName("_id")
                   var id: String? = null,

                   @SerializedName("name")
                   var name: String? = null,

                   @SerializedName("address")
                   var address: String? = null,

                   @SerializedName("phone")
                   var phone: String? = null,

                   @SerializedName("latitude")
                   var latitude: String? = null,

                   @SerializedName("longitude")
                   var longitude: String? = null,

                   @SerializedName("favorite")
                   var favorite: Int? = null,

                   @SerializedName("since")
                   var since: Boolean = false,

                   @SerializedName("image_url")
                   var imageUrl: String? = null,

                   @SerializedName("open")
                   var open: String? = null,

                   @SerializedName("close")
                   var close: String? = null,

                   @SerializedName("stars")
                   var stars: String? = null) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            1 == source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(address)
        writeString(phone)
        writeString(latitude)
        writeString(longitude)
        writeValue(favorite)
        writeInt((if (since) 1 else 0))
        writeString(imageUrl)
        writeString(open)
        writeString(close)
        writeString(stars)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PetShop> = object : Parcelable.Creator<PetShop> {
            override fun createFromParcel(source: Parcel): PetShop = PetShop(source)
            override fun newArray(size: Int): Array<PetShop?> = arrayOfNulls(size)
        }
    }
}