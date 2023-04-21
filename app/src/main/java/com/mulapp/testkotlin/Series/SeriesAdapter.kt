package com.mulapp.testkotlin.Series

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mulapp.testkotlin.R
import com.mulapp.testkotlin.databinding.ItemSerieBinding
import com.mulapp.testkotlin.detalles.Detalle


class SeriesAdapter(private val series: ArrayList<SerieModelo>) :
    RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSerieBinding.bind(itemView)

        fun render(serie: SerieModelo) = with(itemView) {
            binding.tvTituloItem.text = serie.show.name
            binding.tvDescripcionItem.text = serie.show.summary

            try {
                Glide.with(binding.ivCaratulaItem.context).load(serie.show.image.original)
                    .error(R.drawable.ic_sin_imagen).into(binding.ivCaratulaItem)
            } catch (e: java.lang.Exception) {
                binding.ivCaratulaItem.setImageResource(R.drawable.ic_sin_imagen)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, Detalle::class.java)
                intent.putExtra("id", serie.show.id.toString())
                try {
                    intent.putExtra("caratula", serie.show.image.original)
                } catch (_: java.lang.Exception) {
                }
                intent.putExtra("nombre", serie.show.name)
                intent.putExtra("idioma", serie.show.language)
                intent.putExtra("puntaje", serie.show.rating.average.toString())
                intent.putExtra("descripcion", serie.show.summary)
                itemView.context.startActivity(intent)
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
        val item = series[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = series.size
}