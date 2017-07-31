package br.com.tairoroberto.mypet.login.contract

import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.widget.EditText
import br.com.tairoroberto.mypet.base.BaseMVP

/**
 * Created by tairo on 7/22/17.
 */
class LoginContract {

    interface View : BaseMVP.View {
        fun showProgress(show: Boolean)
        fun setError(editText: EditText, string: String?)
        fun showSnackBar(msg:String)
        fun populateAutoComplete()
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun mayRequestContacts(): Boolean
        fun attemptLogin(email: AppCompatAutoCompleteTextView, password: EditText)
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    }
}