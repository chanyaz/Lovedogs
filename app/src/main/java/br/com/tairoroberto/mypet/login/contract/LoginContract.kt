package br.com.tairoroberto.mypet.login.contract

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.widget.EditText
import br.com.tairoroberto.mypet.base.BaseMVP
import br.com.tairoroberto.mypet.login.model.LoginResponse

/**
 * Created by tairo on 7/22/17.
 */
class LoginContract {

    interface View : BaseMVP.View {
        fun showProgress(show: Boolean)
        fun setError(editText: EditText, string: String?)
        fun showSnackBar(msg:String)
        fun showSnackBarError(msg: String)
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun attemptLogin(email: AppCompatAutoCompleteTextView, password: EditText)
        fun manipulateloginResponse(loginResponse: LoginResponse)
    }
}