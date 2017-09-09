package br.com.tairoroberto.lovedogs.register.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by tairo on 9/2/17.
 */
data class UserRegisterRequest(
        @SerializedName("name")
        var name: String? = null,

        @SerializedName("address")
        var address: String? = null,

        @SerializedName("phone")
        var phone: String? = null,

        @SerializedName("email")
        var email: String? = null,

        @SerializedName("password")
        var password: String? = null) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(address)
        writeString(phone)
        writeString(email)
        writeString(password)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserRegisterRequest> = object : Parcelable.Creator<UserRegisterRequest> {
            override fun createFromParcel(source: Parcel): UserRegisterRequest = UserRegisterRequest(source)
            override fun newArray(size: Int): Array<UserRegisterRequest?> = arrayOfNulls(size)
        }
    }
}