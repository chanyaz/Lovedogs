package br.com.tairoroberto.lovedogs.register.presenter

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import br.com.tairoroberto.lovedogs.R
import br.com.tairoroberto.lovedogs.home.HomeActivity
import br.com.tairoroberto.lovedogs.register.contract.RegisterContract
import br.com.tairoroberto.lovedogs.register.model.RegisterModel
import br.com.tairoroberto.lovedogs.register.model.UserRegisterRequest
import br.com.tairoroberto.lovedogs.register.model.UserRegisterResponse

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

        if( TextUtils.isEmpty(name) ) {
            view?.setErrorRegister(R.id.editName, view?.getContext()?.getString(R.string.error_field_required).toString())
            return
        }

        if( TextUtils.isEmpty(address) ) {
            view?.setErrorRegister(R.id.editAddress, view?.getContext()?.getString(R.string.error_field_required).toString())
            return
        }

        if( TextUtils.isEmpty(phone) ) {
            view?.setErrorRegister(R.id.editPhone, view?.getContext()?.getString(R.string.error_field_required).toString())
            return
        }

        if( TextUtils.isEmpty(email) ) {
            view?.setErrorRegister(R.id.editEmail, view?.getContext()?.getString(R.string.error_field_required).toString())
            return
        }

        if( TextUtils.isEmpty(password) ){
            view?.setErrorRegister(R.id.editPassword, view?.getContext()?.getString(R.string.error_field_required).toString())
            return
        }

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

    override fun getActivity(): Activity {
        return view?.getContext() as Activity
    }
}
