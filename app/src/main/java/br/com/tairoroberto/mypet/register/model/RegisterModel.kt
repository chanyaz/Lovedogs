package br.com.tairoroberto.mypet.register.model

import android.util.Log
import br.com.tairoroberto.mypet.base.ApiUtils
import br.com.tairoroberto.mypet.register.contract.RegisterContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tairo on 9/2/17.
 */
class RegisterModel(var presenter: RegisterContract.Presenter): RegisterContract.Model{

    override fun registerUser(userRegisterrequest: UserRegisterRequest) {

        ApiUtils.getApiService()?.registerUser(userRegisterrequest)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulateRegisterResponse(it)
                }, { error ->
                    Log.i("LOG", "${error.message}")
                    presenter.showErrorRegister("Falha ao conectar ao servidor :(")
                })
    }
}