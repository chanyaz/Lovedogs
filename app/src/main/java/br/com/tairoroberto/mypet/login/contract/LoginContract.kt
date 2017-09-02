package br.com.tairoroberto.mypet.login.contract

import android.app.Activity
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.widget.CheckBox
import android.widget.EditText
import br.com.tairoroberto.mypet.base.BaseMVP
import br.com.tairoroberto.mypet.login.model.LoginResponse
import org.json.JSONObject

/**
 * Created by tairo on 7/22/17.
 */
class LoginContract {

    interface View : BaseMVP.View {
        fun showProgress(show: Boolean)
        fun setError(editText: EditText, string: String?)
        fun showSnackBar(msg: String)
        fun showSnackBarError(msg: String)
        fun getActivity(): Activity?
        fun  setUseFromFacebook(emailStr: String, name: String?, picture: String)
    }

    interface Presenter : BaseMVP.Presenter<View> {
        fun attemptLogin(email: AppCompatAutoCompleteTextView, password: EditText, checkBox: CheckBox)
        fun manipulateloginResponse(loginResponse: LoginResponse)
        fun showSnackBarError(s: String)
        fun showProgress(show: Boolean)
        fun getStringPreference(key: String): String?
        fun setUserFromFacebook(userJson: JSONObject?)
    }

    interface Model {
        fun getLogin(email: String, password: String)
        fun saveUserLogin(emailStr: String, passwordStr: String, activity: Activity?)
        fun getStringPreference(activity: Activity?, key: String): String?
    }
}