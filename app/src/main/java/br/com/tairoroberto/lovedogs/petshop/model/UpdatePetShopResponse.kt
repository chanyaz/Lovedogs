package br.com.tairoroberto.lovedogs.petshop.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by tairo on 9/12/17.
 */
data class UpdatePetShopResponse(@SerializedName("success") var success: Boolean,
                                 @SerializedName("message") var message: String,
                                 @SerializedName("petshop") var petShop: PetShop) : Parcelable {

    constructor(source: Parcel) : this(
            1 == source.readInt(),
            source.readString(),
            source.readParcelable<PetShop>(PetShop::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt((if (success) 1 else 0))
        writeString(message)
        writeParcelable(petShop, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UpdatePetShopResponse> = object : Parcelable.Creator<UpdatePetShopResponse> {
            override fun createFromParcel(source: Parcel): UpdatePetShopResponse = UpdatePetShopResponse(source)
            override fun newArray(size: Int): Array<UpdatePetShopResponse?> = arrayOfNulls(size)
        }
    }
}