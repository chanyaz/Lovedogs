package br.com.tairoroberto.lovedogs.petshop.model

import android.util.Log
import br.com.tairoroberto.lovedogs.base.ApiUtils
import br.com.tairoroberto.lovedogs.petshop.contract.PetshopContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by tairo on 8/15/17.
 */
class PetshopModel(private val presenter: PetshopContract.Presenter) : PetshopContract.Model{

    override fun listarPetshops() {
        ApiUtils.getApiService()?.getPetshops()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulatePetshopsResponse(it)
                }, { error ->
                    Log.i("LOG", "${error.message}")
                    presenter.showError(error.message as String)
                })
    }

    override fun updatePetshop(petShop: PetShop) {
        ApiUtils.getApiService()?.updatePetshop(petShop)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    presenter.manipulateUpdatePetshopResponse(it)
                }, { error ->
                    Log.i("LOG", "${error.message}")
                    presenter.showError("Falha na comunicação :(")
                })
    }

    fun getPetshop() {

    }
}