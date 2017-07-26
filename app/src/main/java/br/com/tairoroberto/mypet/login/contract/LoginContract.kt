package br.com.tairoroberto.mypet.login.contract

/**
 * Created by tairo on 7/22/17.
 */
class LoginContract {
    interface View {
        fun showProgress(show: Boolean)
    }

    interface Presenter {
        fun mayRequestContacts(): Boolean
        fun attemptLogin()
    }
}