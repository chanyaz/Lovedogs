package br.com.tairoroberto.mypet.petshop.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.tairoroberto.mypet.R
import br.com.tairoroberto.mypet.petshop.model.PetShop
import com.squareup.picasso.Picasso


/**
 * Created by tairo on 8/22/17.
 */
class PetshopsRecyclerAdapter(val context: Context,
                              private var listPetshops: ArrayList<PetShop>,
                              private val onClick: OnClick) : RecyclerView.Adapter<PetshopsRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val petShop = listPetshops[position]
        holder.bind(context, petShop)
        holder.itemView.setOnClickListener({
            onClick.onItemClick(petShop)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.petshops_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listPetshops.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        private val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        private val textViewOpenClose: TextView = view.findViewById(R.id.textViewOpenClose)

        fun bind(context: Context?, petShop: PetShop) {
            Picasso.with(context).load(petShop.imageUrl).into(imageView)
            textViewTitle.text = petShop.name
            textViewOpenClose.text = petShop.address
        }
    }

    fun update(listPetshops: ArrayList<PetShop>) {
        this.listPetshops = listPetshops
        notifyDataSetChanged()
    }
}