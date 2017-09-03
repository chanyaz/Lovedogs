package br.com.tairoroberto.mypet.register.presenter

import android.content.Intent
import br.com.tairoroberto.mypet.home.HomeActivity
import br.com.tairoroberto.mypet.register.contract.RegisterContract
import br.com.tairoroberto.mypet.register.model.RegisterModel
import br.com.tairoroberto.mypet.register.model.UserRegisterRequest
import br.com.tairoroberto.mypet.register.model.UserRegisterResponse

/**
 * Created by tairo on 8/9/17.
 */
class RegisterPresenter : RegisterContract.Presenter {
    private var view: RegisterContract.View? = null

    private var model: RegisterContract.Model? = null
    override fun attachView(view: RegisterContract.View) {
        this.view = view
        model = RegisterModel(this)
    }

    override fun detachView() {
        this.view = null
    }

    override fun sendRegister(name: String, address: String, phone: String, email: String, password: String) {
        val userRegisterrequest = UserRegisterRequest()
        userRegisterrequest.name = name
        userRegisterrequest.address = name
        userRegisterrequest.phone = name
        userRegisterrequest.email = name
        userRegisterrequest.password = name
        model?.registerUser(userRegisterrequest)
    }

    override fun manipulateRegisterResponse(loginResponse: UserRegisterResponse) {
        if( loginResponse.success) {
            view?.showProgress(false)
            view?.getContext()?.startActivity(Intent(view?.getContext(), HomeActivity::class.java))
            view?.finishActivity()

        }else{
            view?.showErrorRegister("Falha ao registrar usuario :(")
        }
    }

    override fun showErrorRegister(str: String) {
        view?.showSnackBarError(str)
    }
}
