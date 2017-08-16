package br.com.tairoroberto.mypet.petshop.contract

import br.com.tairoroberto.mypet.base.BaseMVP

/**
 * Created by tairo on 8/15/17.
 */
class PetshopContract {


    interface View : BaseMVP.View {

    }

    interface Presenter : BaseMVP.Presenter<View> {

    }
}