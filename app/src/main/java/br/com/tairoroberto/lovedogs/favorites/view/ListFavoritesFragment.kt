package br.com.tairoroberto.lovedogs.petshop.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.tairoroberto.lovedogs.R
import br.com.tairoroberto.lovedogs.favorites.view.OnClickMenu
import br.com.tairoroberto.lovedogs.petshop.contract.FavoriteContract
import br.com.tairoroberto.lovedogs.petshop.model.PetShop
import br.com.tairoroberto.lovedogs.petshop.presenter.FavoritePresenter
import br.com.tairoroberto.lovedogs.petshopdetail.PetshopDetailActivity
import org.jetbrains.anko.startActivity

/**
 * A simple [Fragment] subclass.
 */
class ListFavoritesFragment : Fragment(), FavoriteContract.View, OnClick, OnClickMenu {

    private val presenter: FavoriteContract.Presenter = FavoritePresenter()
    var listPetshops: ArrayList<PetShop>? = ArrayList()
    var adapter: FavoriteRecyclerAdapter? = null
    var recyclerViewPets: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var petShopIndexToRemove: Int = -1

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

        presenter.loadPetshops()

        adapter = FavoriteRecyclerAdapter(activity, listPetshops, this, this)
        recyclerViewPets?.adapter = adapter

        swipeRefreshLayout?.setOnRefreshListener({
            presenter.loadPetshops()
        })

        return view
    }

    override fun showPetshopsList(petshops: ArrayList<PetShop>) {
        adapter?.update(petshops)
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun onItemClick(petShop: PetShop) {
        activity.startActivity<PetshopDetailActivity>("petShop" to petShop)
    }

    override fun onItemMenuClick(position: Int) {
        presenter.updatePetshop(listPetshops?.get(position))
        petShopIndexToRemove = position
    }

    override fun updateList(petShop: PetShop) {
        adapter?.removeItem(petShopIndexToRemove)
        Toast.makeText(activity, "${petShop.name} removido!", Toast.LENGTH_SHORT).show()
        petShopIndexToRemove = -1
    }
}
