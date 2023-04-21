package com.mulapp.testkotlin.detalles

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mulapp.testkotlin.R
import com.mulapp.testkotlin.databinding.ActivityDetalleBinding

class Detalle : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleBinding
    private lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gson = Gson()

        binding.rvEpisodios.layoutManager = LinearLayoutManager(this)
        cargarTextos()
        intent.getStringExtra("id")?.let { obtenerEpisodios(it) }
    }

    private fun cargarTextos() {
        if (intent.hasExtra("caratula")) {
            try {
                Glide.with(this).load(intent.getStringExtra("caratula"))
                    .error(R.drawable.ic_sin_imagen).into(binding.ivCaratula)
            } catch (e: java.lang.Exception) {
                binding.ivCaratula.setImageResource(R.drawable.ic_sin_imagen)
            }
        } else {
            binding.ivCaratula.setImageResource(R.drawable.ic_sin_imagen)
        }

        binding.tvTitulo.text = intent.getStringExtra("nombre")
        binding.tvIdioma.text = "Idioma: " + intent.getStringExtra("idioma")
        if (intent.getStringExtra("puntaje").equals("null")) {
            binding.tvPuntaje.visibility = View.GONE
        } else {
            binding.tvPuntaje.text = "Puntaje: " + intent.getStringExtra("puntaje")
        }
        binding.tvDescripcion.text = intent.getStringExtra("descripcion")
    }

    private fun obtenerEpisodios(id: String) {
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET,
            "https://api.tvmaze.com/shows/$id/episodes",
            { response ->
                val list =
                    ArrayList(gson.fromJson(response, Array<EpisodioModelo>::class.java).toList())

                if (list.isNotEmpty()) {
                    binding.rvEpisodios.adapter = EpisodiosAdapter(list)
                    binding.tvMensaje.visibility = View.GONE
                    binding.rvEpisodios.visibility = View.VISIBLE
                } else {
                    binding.tvMensaje.text = "No se encontraron episodios disponibles"
                }
            }, { error ->
                Toast.makeText(
                    this,
                    "Hubo un error al obtener la lista de episodios",
                    Toast.LENGTH_SHORT
                ).show()
                binding.tvMensaje.text = "No se encontraron episodios disponibles"
                error.stackTraceToString()
            })
        queue.add(stringRequest)
    }
}