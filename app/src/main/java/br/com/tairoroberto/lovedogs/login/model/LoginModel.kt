package br.com.tairoroberto.lovedogs.login.model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import br.com.tairoroberto.lovedogs.base.ApiUtils
import br.com.tairoroberto.lovedogs.base.extension.getConfig
import br.com.tairoroberto.lovedogs.base.extension.getConfigDAO
import br.com.tairoroberto.lovedogs.login.contract.LoginContract
import br.com.tairoroberto.lovedogs.settings.Configuracoes
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
        val config = presenter.getActivity()?.getConfig()
        config?.user = emailStr
        config?.password = passwordStr
        presenter.getActivity()?.getConfigDAO()?.update(config)
    }

    override fun getConfig(): Configuracoes? {
        return presenter.getActivity()?.getConfig()
    }
}
