package br.com.tairoroberto.mypet.petshop.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import br.com.tairoroberto.mypet.R
import br.com.tairoroberto.mypet.petshop.contract.PetshopContract
import br.com.tairoroberto.mypet.petshop.model.PetShop
import br.com.tairoroberto.mypet.petshop.presenter.PetshopPresenter
import android.view.MenuInflater




/**
 * A simple [Fragment] subclass.
 */
class ListPetshopsFragment : Fragment(), PetshopContract.View, OnClick {

    private val presenter: PetshopContract.Presenter = PetshopPresenter()

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
        val recyclerViewPets: RecyclerView? = view?.findViewById(R.id.recyclerViewPets)
        val swipeRefreshLayout: SwipeRefreshLayout? = view?.findViewById(R.id.swipeRefreshLayout)

        recyclerViewPets?.layoutManager = layoutManager
        recyclerViewPets?.setHasFixedSize(true)

        val listPetshops: ArrayList<PetShop> = ArrayList()
        listPetshops.add(PetShop("Pet 1", "pet 1 jgfajslfjsld", "http://gestaoenegocios.digisa.com.br/upload/imagens_upload/Franquia_de_petshop.jpg"))
        listPetshops.add(PetShop("Pet 2", "pet 2 jgfajslfjsld", "http://www.portaltopfranquias.com.br/wp-content/uploads/2016/11/Franquias-de-Pet-Shop.jpg"))
        listPetshops.add(PetShop("Pet 3", "pet 3 jgfajslfjsld", "http://elsumario.com/wp-content/uploads/2016/06/perros-y-gatos-960x500.jpg"))
        listPetshops.add(PetShop("Pet 4", "pet 4 jgfajslfjsld", "http://gestaoenegocios.digisa.com.br/upload/imagens_upload/Franquia_de_petshop.jpg"))
        listPetshops.add(PetShop("Pet 5", "pet 5 jgfajslfjsld", "http://www.portaltopfranquias.com.br/wp-content/uploads/2016/11/Franquias-de-Pet-Shop.jpg"))
        listPetshops.add(PetShop("Pet 6", "pet 6 jgfajslfjsld", "http://elsumario.com/wp-content/uploads/2016/06/perros-y-gatos-960x500.jpg"))

        val adapter = PetshopsRecyclerAdapter(activity, listPetshops, this)
        recyclerViewPets?.adapter = adapter

        swipeRefreshLayout?.setOnRefreshListener({
            adapter.update(listPetshops)
            swipeRefreshLayout.isRefreshing = false
        })


        setHasOptionsMenu(true)
        return view
    }

    override fun onItemClick(petShop: PetShop) {
        Toast.makeText(activity, "home petShop: ${petShop.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        inflater?.inflate(R.menu.home, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }
}
