package br.com.tairoroberto.lovedogs.login.model

import android.os.Parcel
import android.os.Parcelable
import br.com.tairoroberto.lovedogs.register.model.User
import com.google.gson.annotations.SerializedName

/**
 * Created by tairo on 8/27/17.
 */
data class LoginResponse(
        @SerializedName("success") var success: Boolean,
        @SerializedName("user") var user: User) : Parcelable {

    constructor(source: Parcel) : this(
            1 == source.readInt(),
            source.readParcelable<User>(User::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt((if (success) 1 else 0))
        writeParcelable(user, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LoginResponse> = object : Parcelable.Creator<LoginResponse> {
            override fun createFromParcel(source: Parcel): LoginResponse = LoginResponse(source)
            override fun newArray(size: Int): Array<LoginResponse?> = arrayOfNulls(size)
        }
    }
}