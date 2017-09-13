package br.com.tairoroberto.lovedogs.petshop.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import br.com.tairoroberto.lovedogs.R
import br.com.tairoroberto.lovedogs.base.extension.loadImage
import br.com.tairoroberto.lovedogs.petshopservice.model.Service
import br.com.tairoroberto.lovedogs.petshopservice.view.OnClick
import de.hdodenhof.circleimageview.CircleImageView


/**
 * Created by tairo on 8/22/17.
 */
class ServiceRecyclerAdapter(val context: Context,
                             private var listServices: ArrayList<Service>?,
                             private val onClick: OnClick) : RecyclerView.Adapter<ServiceRecyclerAdapter.ViewHolder>() {

    private var lastPosition = -1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = listServices?.get(position)
        if (service != null) {
            holder.bind(context, service)
            holder.itemView.setOnClickListener({
                onClick.onItemClick(service)
            })
        }
        setAnimation(holder.itemView, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.service_item, parent, false))
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > 0) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun getItemCount(): Int {
        return listServices?.size as Int
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: CircleImageView = view.findViewById(R.id.imageView)
        private val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        private val textViewValue: TextView = view.findViewById(R.id.textViewValue)

        fun bind(context: Context?, service: Service) {

            imageView.loadImage(service.imageService)
            textViewTitle.text = service.name
            textViewValue.text = imageView.context.getString(R.string.formated_value_money, service.value.toString())
        }
    }

    fun update(listServices: ArrayList<Service>) {
        this.listServices?.clear()
        this.listServices?.addAll(listServices)
        notifyDataSetChanged()
    }
}