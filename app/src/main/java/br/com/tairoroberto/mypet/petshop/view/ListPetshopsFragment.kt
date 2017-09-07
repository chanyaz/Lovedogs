package br.com.tairoroberto.mypet.petshop.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import br.com.tairoroberto.mypet.R
import br.com.tairoroberto.mypet.login.view.LoginActivity
import br.com.tairoroberto.mypet.petshop.contract.PetshopContract
import br.com.tairoroberto.mypet.petshop.model.PetShop
import br.com.tairoroberto.mypet.petshop.presenter.PetshopPresenter
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import org.jetbrains.anko.startActivity

/**
 * A simple [Fragment] subclass.
 */
class ListPetshopsFragment : Fragment(), PetshopContract.View, OnClick {

    private val presenter: PetshopContract.Presenter = PetshopPresenter()
    val listPetshops: ArrayList<PetShop> = ArrayList()
    var adapter: PetshopsRecyclerAdapter? = null
    var recyclerViewPets: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View? = inflater?.inflate(R.layout.fragment_list_petshops, container, false)

        val layoutManager = LinearLayoutManager(activity)
        recyclerViewPets = view?.findViewById(R.id.recyclerViewPets)
        swipeRefreshLayout = view?.findViewById(R.id.swipeRefreshLayout)

        recyclerViewPets?.layoutManager = layoutManager
        recyclerViewPets?.setHasFixedSize(true)
        adapter = PetshopsRecyclerAdapter(activity, listPetshops, this)
        recyclerViewPets?.adapter = adapter
        presenter.loadPetshops()

        swipeRefreshLayout?.setOnRefreshListener({
            presenter.loadPetshops()
        })

        setHasOptionsMenu(true)
        return view
    }

    override fun showPetshopsList(petshops: ArrayList<PetShop>) {
        adapter?.update(petshops)
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun onItemClick(petShop: PetShop) {
        Toast.makeText(activity, "home petShop: ${petShop.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        inflater?.inflate(R.menu.home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.logout) {
            disconnectFromFacebook()
        }
        return super.onOptionsItemSelected(item)
    }

    fun disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            activity.startActivity<LoginActivity>()
            activity.finish()
            return
        }
        GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                GraphRequest.Callback {
                    LoginManager.getInstance().logOut()

                    activity.startActivity<LoginActivity>()
                    activity.finish()
                }).executeAsync()
    }
}
