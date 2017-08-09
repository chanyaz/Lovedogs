package br.com.tairoroberto.mypet.register.presenter

import br.com.tairoroberto.mypet.register.contract.RegisterContract

/**
 * Created by tairo on 8/9/17.
 */
class RegisterPresenter : RegisterContract.Presenter {

    private var view: RegisterContract.View? = null

    override fun attachView(view: RegisterContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

}