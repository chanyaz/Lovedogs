package br.com.tairoroberto.lovedogs.petshop.presenter

import android.util.Log
import br.com.tairoroberto.lovedogs.base.extension.showSnackBarError
import br.com.tairoroberto.lovedogs.petshop.contract.ServiceContract
import br.com.tairoroberto.lovedogs.petshop.model.ServiceModel
import br.com.tairoroberto.lovedogs.petshopservice.model.ServicesResponse
import kotlinx.android.synthetic.main.fragment_list_petshops.*

/**
 * Created by tairo on 8/15/17.
 */
class ServicePresenter : ServiceContract.Presenter {

    private var view: ServiceContract.View? = null
    private var model: ServiceContract.Model? = null
    override fun attachView(view: ServiceContract.View) {
        this.view = view
        this.model = ServiceModel(this)
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadServices() {
        model?.listarServicos()
    }

    override fun manipulateServicesResponse(servicesResponse: ServicesResponse) {
        Log.i("LOG", "petshops ${servicesResponse.services}")

        if (servicesResponse.success) {
            view?.showServiceList(servicesResponse.services)
        }
    }

    override fun showError(str: String) {
        view?.getActivity()?.showSnackBarError(view?.getActivity()?.recyclerViewPets!!, str)
    }
}