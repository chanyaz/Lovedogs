package br.com.tairoroberto.mypet.register.model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import br.com.tairoroberto.mypet.base.ApiUtils
import br.com.tairoroberto.mypet.register.contract.RegisterContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tairo on 9/2/17.
 */
class RegisterModel(var presenter: RegisterContract.Presenter) : RegisterContract.Model {

    override fun registerUser(userRegisterrequest: UserRegisterRequest) {

        ApiUtils.getApiService()?.registerUser(userRegisterrequest)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulateRegisterResponse(it)
                    saveUserLogin(userRegisterrequest.email, userRegisterrequest.password, presenter.getActivity())
                }, { error ->
                    Log.i("LOG", "${error.message}")
                    presenter.showErrorRegister("Falha ao conectar ao servidor :(")
                })
    }

    override fun saveUserLogin(emailStr: String?, passwordStr: String?, activity: Activity?) {
        val preference: SharedPreferences? = activity?.getPreferences(Context.MODE_PRIVATE)
        preference?.edit()?.putString("email", emailStr)?.apply()
        preference?.edit()?.putString("password", passwordStr)?.apply()
    }

    override fun getStringPreference(activity: Activity?, key: String): String? {
        return activity?.getPreferences(Context.MODE_PRIVATE)?.getString(key, "")
    }
}