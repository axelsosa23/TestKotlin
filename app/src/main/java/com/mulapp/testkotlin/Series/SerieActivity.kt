package com.mulapp.testkotlin.Series

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.mulapp.testkotlin.databinding.ActivitySerieBinding

class SerieActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySerieBinding
    private lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gson = Gson()

        binding.rvSeries.layoutManager = LinearLayoutManager(this)
    }

    fun buscarSerie(view: View) {
        obtenerBusqueda(binding.etBuscar.text.toString().trim())
    }

    fun obtenerBusqueda(serie: String) {
        binding.tvMensaje.text = "Buscando serie..."
        binding.tvMensaje.visibility = View.VISIBLE
        binding.rvSeries.visibility = View.GONE

        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET,
            "https://api.tvmaze.com/search/shows?q=$serie",
            { response ->
                val list =
                    ArrayList(gson.fromJson(response, Array<SerieModelo>::class.java).toList())

                if (list.isNotEmpty()) {
                    binding.rvSeries.adapter = SeriesAdapter(list)
                    binding.tvMensaje.visibility = View.GONE
                    binding.rvSeries.visibility = View.VISIBLE
                } else {
                    binding.tvMensaje.text = "No se encuentra la serie buscada, intente nuevamente"
                    binding.rvSeries.visibility = View.GONE
                    binding.tvMensaje.visibility = View.VISIBLE
                }
            },
            { error ->
                Toast.makeText(
                    this,
                    "Hubo un error al obtener la lista de series",
                    Toast.LENGTH_SHORT
                ).show()
                binding.tvMensaje.text = "Intente nuevamente"
                binding.rvSeries.visibility = View.GONE
                binding.tvMensaje.visibility = View.VISIBLE
                error.stackTraceToString()
            })
        queue.add(stringRequest)
    }
}