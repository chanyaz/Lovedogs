package br.com.tairoroberto.mypet.petshop.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.tairoroberto.mypet.R


/**
 * A simple [Fragment] subclass.
 */
class ListPetshopsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_list_petshops, container, false)
    }

}// Required empty public constructor
