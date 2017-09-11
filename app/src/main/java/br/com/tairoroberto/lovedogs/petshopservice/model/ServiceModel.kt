package br.com.tairoroberto.lovedogs.petshop.model

import android.util.Log
import br.com.tairoroberto.lovedogs.base.ApiUtils
import br.com.tairoroberto.lovedogs.petshop.contract.PetshopContract
import br.com.tairoroberto.lovedogs.petshop.contract.ServiceContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tairo on 8/15/17.
 */
class ServiceModel(private val presenter: ServiceContract.Presenter) : ServiceContract.Model{

    override fun listarServicos() {
        ApiUtils.getApiService()?.getServices()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulateServicesResponse(it)
                }, { error ->
                    Log.i("LOG", "${error.message}")
                    presenter.showError(error.message as String)
                })
    }

    fun getPetshop() {

    }
}