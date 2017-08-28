package br.com.tairoroberto.mypet.register.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by tairo on 8/27/17.
 */
data class User(@SerializedName("name")
                var name: String? = null,

                @SerializedName("address")
                var address: String? = null,

                @SerializedName("phone")
                var phone: String? = null,

                @SerializedName("email")
                var email: String? = null,

                @SerializedName("password")
                var password: String? = null,

                @SerializedName("updated_at")
                var updated_at: String? = null) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(updated_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}