package com.example.gatoscrud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GatosAdapter(private val gatosList: List<Gato>, private val listener: (Gato) -> Unit) : RecyclerView.Adapter<GatosAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val razaGato: TextView = view.findViewById(R.id.tvRaza)
        val edadGato: TextView = view.findViewById(R.id.tvEdad)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gato_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val gato = gatosList[position]
        holder.razaGato.text = gato.raza
        holder.edadGato.text = gato.edad.toString()
        holder.btnEliminar.setOnClickListener { listener(gato) }
    }

    override fun getItemCount() = gatosList.size
}
