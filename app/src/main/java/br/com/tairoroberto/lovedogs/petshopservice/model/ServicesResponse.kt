package br.com.tairoroberto.lovedogs.petshopservice.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by tairo on 9/11/17.
 */
data class ServicesResponse(@SerializedName("success") var success: Boolean,
                            @SerializedName("services") var services: ArrayList<Service>) : Parcelable {

    constructor(source: Parcel) : this(
            1 == source.readInt(),
            ArrayList<Service>().apply { source.readList(this, Service::class.java.classLoader) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt((if (success) 1 else 0))
        writeList(services)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ServicesResponse> = object : Parcelable.Creator<ServicesResponse> {
            override fun createFromParcel(source: Parcel): ServicesResponse = ServicesResponse(source)
            override fun newArray(size: Int): Array<ServicesResponse?> = arrayOfNulls(size)
        }
    }
}