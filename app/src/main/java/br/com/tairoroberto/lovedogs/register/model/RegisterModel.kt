package br.com.tairoroberto.lovedogs.register.model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import br.com.tairoroberto.lovedogs.base.ApiUtils
import br.com.tairoroberto.lovedogs.base.extension.getConfig
import br.com.tairoroberto.lovedogs.base.extension.getConfigDAO
import br.com.tairoroberto.lovedogs.register.contract.RegisterContract
import br.com.tairoroberto.lovedogs.settings.Configuracoes
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
                    saveUserLogin(userRegisterrequest.email, userRegisterrequest.password)
                }, { error ->
                    Log.i("LOG", "${error.message}")
                    presenter.showErrorRegister("Falha ao conectar ao servidor :(")
                })
    }

    override fun saveUserLogin(emailStr: String?, passwordStr: String?) {
        val config = presenter.getActivity().getConfig()
        config?.user = emailStr.toString()
        config?.password = passwordStr.toString()
        presenter.getActivity().getConfigDAO().update(config)
    }

    override fun getConfig(): Configuracoes? {
        return presenter.getActivity().getConfig()
    }
}