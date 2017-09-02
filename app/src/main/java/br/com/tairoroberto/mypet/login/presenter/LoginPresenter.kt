package br.com.tairoroberto.mypet.login.presenter

import android.content.Intent
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import br.com.tairoroberto.mypet.R
import br.com.tairoroberto.mypet.home.HomeActivity
import br.com.tairoroberto.mypet.login.contract.LoginContract
import br.com.tairoroberto.mypet.login.model.LoginModel
import br.com.tairoroberto.mypet.login.model.LoginResponse
import org.json.JSONObject

/**
 * Created by tairo on 7/30/17.
 */
class LoginPresenter : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private var model: LoginContract.Model? = null

    override fun attachView(view: LoginContract.View) {
        this.view = view
        this.model = LoginModel(this)
    }

    override fun detachView() {
        this.view = null
    }

    override fun attemptLogin(email: AppCompatAutoCompleteTextView, password: EditText, checkBox: CheckBox) {

        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = view?.getContext()?.getString(R.string.error_field_required)
            focusView = email
            cancel = true
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordStr)) {
            password.error = view?.getContext()?.getString(R.string.error_field_required)
            focusView = password
            cancel = true
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            view?.showProgress(true)
            model?.getLogin(emailStr, passwordStr)
        }

        if (checkBox.isChecked) {
            model?.saveUserLogin(emailStr, passwordStr, view?.getActivity())
        }
    }

    override fun manipulateloginResponse(loginResponse: LoginResponse) {
        Log.i("LOG", "Success: ${loginResponse.success} ${loginResponse.user}")

        view?.showProgress(false)
        if (loginResponse.success) {
            view?.getContext()?.startActivity(Intent(view?.getContext(), HomeActivity::class.java))
        } else {
            view?.showSnackBarError("Usuário não cadatrado ou senha inválida :(")
        }
    }

    override fun getStringPreference(key: String): String? {
        return model?.getStringPreference(view?.getActivity(), key)
    }

    override fun showSnackBarError(s: String) {
        view?.showSnackBarError(s)
    }

    override fun showProgress(show: Boolean) {
        view?.showProgress(show)
    }

    override fun setUserFromFacebook(userJson: JSONObject?) {
        if (userJson != null) {
            view?.setUseFromFacebook(userJson["email"].toString(), userJson["name"].toString(), ((userJson["picture"] as JSONObject)["data"] as JSONObject)["url"] as String)
            Log.i("LOG", "User $userJson")
        }
    }
}
