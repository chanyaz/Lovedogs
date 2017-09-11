package br.com.tairoroberto.lovedogs.petshop.presenter

import android.util.Log
import br.com.tairoroberto.lovedogs.base.extension.showSnackBarError
import br.com.tairoroberto.lovedogs.petshop.contract.FavoriteContract
import br.com.tairoroberto.lovedogs.petshop.contract.PetshopContract
import br.com.tairoroberto.lovedogs.petshop.model.FavoriteModel
import br.com.tairoroberto.lovedogs.petshop.model.PetshopsResponse
import kotlinx.android.synthetic.main.fragment_list_petshops.*

/**
 * Created by tairo on 8/15/17.
 */
class FavoritePresenter : FavoriteContract.Presenter {

    private var view: FavoriteContract.View? = null
    private var model: FavoriteContract.Model? = null
    override fun attachView(view: FavoriteContract.View) {
        this.view = view
        this.model = FavoriteModel(this)
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadPetshops() {
        model?.listarPetshops()
    }

    override fun manipulatePetshopsResponse(petshopsResponse: PetshopsResponse) {
        Log.i("LOG", "petshops ${petshopsResponse.petshops}")

        if (petshopsResponse.success) {
            view?.showPetshopsList(petshopsResponse.petshops)
        }
    }

    override fun showError(str: String) {
        view?.getActivity()?.showSnackBarError(view?.getActivity()?.recyclerViewPets!!, str)
    }
}