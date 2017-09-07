package br.com.tairoroberto.mypet.petshop.contract

import br.com.tairoroberto.mypet.base.BaseMVP
import br.com.tairoroberto.mypet.petshop.model.PetShop
import br.com.tairoroberto.mypet.petshop.model.PetshopsResponse

/**
 * Created by tairo on 8/15/17.
 */
class PetshopContract {

    interface Model {
        fun listarPetshops()

    }

    interface View : BaseMVP.View {
        fun showPetshopsList(petshops: ArrayList<PetShop>)
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun loadPetshops()
        fun manipulatePetshopsResponse(petshopsResponse: PetshopsResponse)
        fun showError(str: String)
    }
}