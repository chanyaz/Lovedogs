package br.com.tairoroberto.lovedogs.petshopservice.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by tairo on 9/11/17.
 */
data class Service(@SerializedName("id_petshop") var idPetshop: String,
                   @SerializedName("name") var name: String,
                   @SerializedName("image_service") var imageService: String,
                   @SerializedName("value") var value: Double) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(idPetshop)
        writeString(name)
        writeString(imageService)
        writeDouble(value)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Service> = object : Parcelable.Creator<Service> {
            override fun createFromParcel(source: Parcel): Service = Service(source)
            override fun newArray(size: Int): Array<Service?> = arrayOfNulls(size)
        }
    }
}