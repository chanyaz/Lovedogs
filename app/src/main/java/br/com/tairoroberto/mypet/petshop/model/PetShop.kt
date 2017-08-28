package br.com.tairoroberto.mypet.petshop.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by tairo on 8/22/17.
 */
data class PetShop(@SerializedName("id")
                   var id: String? = null,

                   @SerializedName("name")
                   var name: String? = null,

                   @SerializedName("address")
                   var address: String? = null,

                   @SerializedName("latitude")
                   var latitude: String? = null,

                   @SerializedName("longitude")
                   var longitude: String? = null,

                   @SerializedName("number_of_customers")
                   var numberOfCustomers: Int? = null,

                   @SerializedName("favorite")
                   var favorite: Boolean = false,

                   @SerializedName("since")
                   var since: String? = null,

                   @SerializedName("image_url")
                   var imageUrl: String? = null,

                   @SerializedName("RedirectUrl")
                   var redirectUrl: String? = null,

                   @SerializedName("updated_at")
                   var updatedAt: String? = null) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
        parcel.writeValue(numberOfCustomers)
        parcel.writeByte(if (favorite) 1 else 0)
        parcel.writeString(since)
        parcel.writeString(imageUrl)
        parcel.writeString(redirectUrl)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PetShop> {
        override fun createFromParcel(parcel: Parcel): PetShop {
            return PetShop(parcel)
        }

        override fun newArray(size: Int): Array<PetShop?> {
            return arrayOfNulls(size)
        }
    }
}