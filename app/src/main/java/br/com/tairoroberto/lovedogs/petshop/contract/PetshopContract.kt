package br.com.tairoroberto.lovedogs.petshop.contract

import br.com.tairoroberto.lovedogs.base.BaseMVP
import br.com.tairoroberto.lovedogs.petshop.model.PetShop
import br.com.tairoroberto.lovedogs.petshop.model.PetshopsResponse
import br.com.tairoroberto.lovedogs.petshop.model.UpdatePetShopResponse

/**
 * Created by tairo on 8/15/17.
 */
class PetshopContract {

    interface Model {
        fun listarPetshops()
        fun updatePetshop(petShop: PetShop)
    }

    interface View : BaseMVP.View {
        fun showPetshopsList(petshops: ArrayList<PetShop>)
        fun updateList(petShop: PetShop)
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun loadPetshops()
        fun manipulatePetshopsResponse(petshopsResponse: PetshopsResponse)
        fun showError(str: String)
        fun updatePetshop(petShop: PetShop?)
        fun manipulateUpdatePetshopResponse(updatePetShopResponse: UpdatePetShopResponse)
    }
}