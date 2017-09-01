package br.com.tairoroberto.mypet.login.model

import android.util.Log
import br.com.tairoroberto.mypet.base.ApiUtils
import br.com.tairoroberto.mypet.login.contract.LoginContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tairo on 8/31/17.
 */
class LoginModel(val presenter: LoginContract.Presenter){

    fun getLogin(email: String, password: String){
        ApiUtils.getApiService()?.login(email, password)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulateloginResponse(it)
                }, {
                    error ->
                    Log.i("LOG", "${error.message}")
                })
    }
}