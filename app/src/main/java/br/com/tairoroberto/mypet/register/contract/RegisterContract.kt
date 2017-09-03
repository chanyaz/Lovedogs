package br.com.tairoroberto.mypet.register.contract

import br.com.tairoroberto.mypet.base.BaseMVP
import br.com.tairoroberto.mypet.register.model.UserRegisterRequest
import br.com.tairoroberto.mypet.register.model.UserRegisterResponse

/**
 * Created by tairo on 8/9/17.
 */

class RegisterContract {
    interface View : BaseMVP.View {
        fun showProgress(show: Boolean)
        fun showSnackBarError(msg: String)
        fun showErrorRegister(str: String)
        fun finishActivity()
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun sendRegister(name: String, address: String, phone: String, email: String, password: String)
        fun manipulateRegisterResponse(loginResponse: UserRegisterResponse)
        fun showErrorRegister(str: String)
    }

    interface Model {
        fun registerUser(userRegisterrequest: UserRegisterRequest)
    }
}
