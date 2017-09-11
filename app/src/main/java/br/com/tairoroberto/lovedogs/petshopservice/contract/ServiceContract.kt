package br.com.tairoroberto.lovedogs.petshop.contract

import br.com.tairoroberto.lovedogs.base.BaseMVP
import br.com.tairoroberto.lovedogs.petshopservice.model.Service
import br.com.tairoroberto.lovedogs.petshopservice.model.ServicesResponse

/**
 * Created by tairo on 8/15/17.
 */
class ServiceContract {

    interface Model {
        fun listarServicos()

    }

    interface View : BaseMVP.View {
        fun showServiceList(services: ArrayList<Service>)
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun loadServices()
        fun manipulateServicesResponse(servicesResponse: ServicesResponse)
        fun showError(str: String)
    }
}