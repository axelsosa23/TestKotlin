package com.mulapp.testkotlin.detalles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mulapp.testkotlin.R
import com.mulapp.testkotlin.databinding.ItemSerieBinding

class EpisodiosAdapter(private val episodios: ArrayList<EpisodioModelo>) :
    RecyclerView.Adapter<EpisodiosAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSerieBinding.bind(itemView)

        fun render(episodio: EpisodioModelo) = with(itemView) {
            binding.tvTituloItem.text = episodio.name
            binding.tvDescripcionItem.text = episodio.summary

            try {
                Glide.with(binding.ivCaratulaItem.context).load(episodio.image.original)
                    .error(R.drawable.ic_sin_imagen).into(binding.ivCaratulaItem)
            } catch (e: java.lang.Exception) {
                binding.ivCaratulaItem.setImageResource(R.drawable.ic_sin_imagen)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_serie,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = episodios[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = episodios.size
}