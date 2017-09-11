package br.com.tairoroberto.lovedogs.petshop.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tairoroberto.lovedogs.R
import br.com.tairoroberto.lovedogs.petshop.contract.PetshopContract
import br.com.tairoroberto.lovedogs.petshop.contract.ServiceContract
import br.com.tairoroberto.lovedogs.petshop.model.PetShop
import br.com.tairoroberto.lovedogs.petshop.presenter.ServicePresenter
import br.com.tairoroberto.lovedogs.petshopdetail.PetshopDetailActivity
import br.com.tairoroberto.lovedogs.petshopservice.model.Service
import org.jetbrains.anko.startActivity

/**
 * A simple [Fragment] subclass.
 */
class ListServiceFragment : Fragment(), ServiceContract.View, OnClick, br.com.tairoroberto.lovedogs.petshopservice.view.OnClick {

    private val presenter: ServiceContract.Presenter = ServicePresenter()
    var listServices: ArrayList<Service>? = ArrayList()
    var adapter: ServiceRecyclerAdapter? = null
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

        presenter.loadServices()

        adapter = ServiceRecyclerAdapter(activity, listServices, this)
        recyclerViewPets?.adapter = adapter

        swipeRefreshLayout?.setOnRefreshListener({
            presenter.loadServices()
        })

        return view
    }

    override fun onItemClick(service: Service) {

    }

    override fun showServiceList(services: ArrayList<Service>) {
        adapter?.update(services)
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun onItemClick(petShop: PetShop) {
        activity.startActivity<PetshopDetailActivity>("petShop" to petShop)
    }
}
