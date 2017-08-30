package br.com.tairoroberto.mypet.register.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by tairo on 8/27/17.
 */
data class User(@SerializedName("_id")
                var id: String? = null,

                @SerializedName("name")
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

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
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
        writeString(email)
        writeString(password)
        writeString(updated_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}