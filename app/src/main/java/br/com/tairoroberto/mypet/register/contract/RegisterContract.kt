package br.com.tairoroberto.mypet.register.contract

import br.com.tairoroberto.mypet.base.BaseMVP

/**
 * Created by tairo on 8/9/17.
 */

class RegisterContract {
    interface View : BaseMVP.View {
        fun showProgress(show: Boolean)
    }

    interface Presenter : BaseMVP.Presenter<View> {

    }
}
