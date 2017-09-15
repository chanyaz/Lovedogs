package br.com.tairoroberto.lovedogs.register.contract

import android.app.Activity
import br.com.tairoroberto.lovedogs.base.BaseMVP
import br.com.tairoroberto.lovedogs.register.model.UserRegisterRequest
import br.com.tairoroberto.lovedogs.register.model.UserRegisterResponse
import br.com.tairoroberto.lovedogs.settings.Configuracoes

/**
 * Created by tairo on 8/9/17.
 */

class RegisterContract {
    interface View : BaseMVP.View {
        fun showProgress(show: Boolean)
        fun showSnackBarError(msg: String)
        fun showErrorRegister(str: String)
        fun finishActivity()
        fun setErrorRegister(idView: Int, msg: String)
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun sendRegister(name: String, address: String, phone: String, email: String, password: String)
        fun manipulateRegisterResponse(loginResponse: UserRegisterResponse)
        fun showErrorRegister(str: String)
        fun getActivity(): Activity
        fun getConfig(): Configuracoes?
    }

    interface Model {
        fun registerUser(userRegisterrequest: UserRegisterRequest)
        fun saveUserLogin(emailStr: String?, passwordStr: String?)
        fun getConfig(): Configuracoes?
    }
}
