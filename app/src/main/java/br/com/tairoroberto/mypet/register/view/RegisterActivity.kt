package br.com.tairoroberto.mypet.register.view

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.tairoroberto.mypet.R
import br.com.tairoroberto.mypet.register.contract.RegisterContract
import br.com.tairoroberto.mypet.register.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterContract.View{
    private val presenter: RegisterContract.Presenter = RegisterPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(toolbar)
        toolbar_text.text = getString(R.string.register_activity)
        presenter.attachView(this)
    }

    override fun getContext(): Context {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showProgress(show: Boolean) {

    }
}
