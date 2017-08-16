package br.com.tairoroberto.mypet.petshop.presenter

import br.com.tairoroberto.mypet.petshop.contract.PetshopContract
import br.com.tairoroberto.mypet.petshop.model.PetshopModel

/**
 * Created by tairo on 8/15/17.
 */
class PetshopPresenter : PetshopContract.Presenter {

    private var view: PetshopContract.View? = null
    private var model: PetshopModel? = null
    override fun attachView(view: PetshopContract.View) {
        this.view = view
        this.model = PetshopModel(this)
    }

    override fun detachView() {
        this.view = null
    }
}