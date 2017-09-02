package br.com.tairoroberto.mypet.register.contract

import br.com.tairoroberto.mypet.base.BaseMVP

/**
 * Created by tairo on 8/9/17.
 */

class RegisterContract {
    interface View : BaseMVP.View {
        fun showProgress(show: Boolean)

        fun showErrorRegister(str: String)
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun sendRegister(name: String, address: String, phone: String, email: String, password: String)
    }
}
