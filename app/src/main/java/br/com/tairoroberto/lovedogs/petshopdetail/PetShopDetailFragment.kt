package br.com.tairoroberto.lovedogs.petshopdetail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ImageView
import android.widget.TextView

import br.com.tairoroberto.lovedogs.R
import br.com.tairoroberto.lovedogs.base.extension.loadImage
import br.com.tairoroberto.lovedogs.petshop.model.PetShop


/**
 * A simple [Fragment] subclass.
 */
class PetShopDetailFragment : Fragment() {

    private var petShop: PetShop? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            petShop = arguments.getParcelable(PETSHOP)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_pet_shop_detail, container, false)
        val imageViewPetShop = view.findViewById<ImageView>(R.id.imageViewPetShop)
        val textViewTitle = view.findViewById<TextView>(R.id.textViewTitle)
        val textViewAddress = view.findViewById<TextView>(R.id.textViewAddress)

        imageViewPetShop.loadImage(petShop?.imageUrl)
        textViewTitle.text = petShop?.name
        textViewAddress.text = petShop?.address

        setHasOptionsMenu(true)

        return view
    }

    companion object {
        private val PETSHOP = "petshop"

        fun newInstance(petshop: PetShop): PetShopDetailFragment {
            val fragment = PetShopDetailFragment()
            val args = Bundle()
            args.putParcelable(PETSHOP, petshop)
            fragment.arguments = args
            return fragment
        }
    }
}
