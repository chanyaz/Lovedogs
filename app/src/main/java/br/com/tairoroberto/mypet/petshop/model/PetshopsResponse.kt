package br.com.tairoroberto.mypet.petshop.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by tairo on 9/7/17.
 */
data class PetshopsResponse(@SerializedName("success") var success: Boolean,
                            @SerializedName("petshops") var petshops: ArrayList<PetShop>) : Parcelable {
    constructor(source: Parcel) : this(
            1 == source.readInt(),
            source.createTypedArrayList(PetShop.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt((if (success) 1 else 0))
        writeTypedList(petshops)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PetshopsResponse> = object : Parcelable.Creator<PetshopsResponse> {
            override fun createFromParcel(source: Parcel): PetshopsResponse = PetshopsResponse(source)
            override fun newArray(size: Int): Array<PetshopsResponse?> = arrayOfNulls(size)
        }
    }
}
