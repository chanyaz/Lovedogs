package br.com.tairoroberto.lovedogs.login.model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import br.com.tairoroberto.lovedogs.base.ApiUtils
import br.com.tairoroberto.lovedogs.login.contract.LoginContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tairo on 8/31/17.
 */
class LoginModel(val presenter: LoginContract.Presenter) : LoginContract.Model {
    override fun getLogin(email: String, password: String) {
        ApiUtils.getApiService()?.login(email, password)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulateloginResponse(it)
                }, { error ->
                    Log.i("LOG", "${error.message}")
                    presenter.showProgress(false)
                    presenter.showSnackBarError("Falha na comunicação :(")
                })
    }

    override fun saveUserLogin(emailStr: String, passwordStr: String, activity: Activity?) {
        val preference: SharedPreferences? = activity?.getPreferences(Context.MODE_PRIVATE)
        preference?.edit()?.putString("email", emailStr)?.apply()
        preference?.edit()?.putString("password", passwordStr)?.apply()
    }

    override fun getStringPreference(activity: Activity?, key: String): String? {
        return activity?.getPreferences(Context.MODE_PRIVATE)?.getString(key, "")
    }
}
