package br.com.tairoroberto.mypet.register.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by tairo on 9/2/17.
 */
data class UserRegisterResponse(
        @SerializedName("success") var success: Boolean,
        @SerializedName("message") var message: String) : Parcelable {
    constructor(source: Parcel) : this(
            1 == source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt((if (success) 1 else 0))
        writeString(message)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserRegisterResponse> = object : Parcelable.Creator<UserRegisterResponse> {
            override fun createFromParcel(source: Parcel): UserRegisterResponse = UserRegisterResponse(source)
            override fun newArray(size: Int): Array<UserRegisterResponse?> = arrayOfNulls(size)
        }
    }
}