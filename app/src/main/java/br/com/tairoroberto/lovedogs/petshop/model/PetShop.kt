package br.com.tairoroberto.lovedogs.petshop.model

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

                   @SerializedName("description")
                   var description: String? = null,

                   @SerializedName("address")
                   var address: String? = null,

                   @SerializedName("phone")
                   var phone: String? = null,

                   @SerializedName("latitude")
                   var latitude: Double = 0.0,

                   @SerializedName("longitude")
                   var longitude: Double = 0.0,

                   @SerializedName("favorite")
                   var favorite: Boolean = false,

                   @SerializedName("since")
                   var since: String? = null,

                   @SerializedName("image_url")
                   var imageUrl: String? = null,

                   @SerializedName("open")
                   var open: String? = null,

                   @SerializedName("close")
                   var close: String? = null,

                   @SerializedName("stars")
                   var stars: Double = 0.0) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readDouble(),
            source.readDouble(),
            1 == source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(description)
        writeString(address)
        writeString(phone)
        writeDouble(latitude)
        writeDouble(longitude)
        writeInt((if (favorite) 1 else 0))
        writeString(since)
        writeString(imageUrl)
        writeString(open)
        writeString(close)
        writeDouble(stars)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PetShop> = object : Parcelable.Creator<PetShop> {
            override fun createFromParcel(source: Parcel): PetShop = PetShop(source)
            override fun newArray(size: Int): Array<PetShop?> = arrayOfNulls(size)
        }
    }
}