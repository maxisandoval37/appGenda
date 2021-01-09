package com.mxdigitalacademy.appgenda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapter(var context: Context, items:ArrayList<ObjContacto>): BaseAdapter() {

    private var items:ArrayList<ObjContacto>? = null
    var tipoTemplate:Int = R.layout.template_contacto

    init {
        this.items=items
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var holder:ViewHolder?= null
        var vista:View? = p1

        if (vista==null){
            vista = LayoutInflater.from(context).inflate(tipoTemplate,null)
            holder = ViewHolder(vista)
            vista.tag = holder
        }
        else
            holder = vista.tag as? ViewHolder

        val item = getItem(p0) as ObjContacto
        holder?.imagen?.setImageResource(item.getImgAvatar())
        holder?.nombreCompleto?.text = item.getNombreCompleto()
        holder?.telefono?.text = item.getTelefonoPrincipal()

        return vista!!
    }

    override fun getItem(p0: Int): Any {
        return items?.get(p0)!!
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return items?.count()!!
    }

    fun filter(contactos: List<ObjContacto>, filtro: String): ArrayList<ObjContacto> {
        val contactosAuxFiltro: ArrayList<ObjContacto> = ArrayList()

        for (contacto in contactos) {
            if (contacto.getNombreCompleto().toLowerCase(Locale.ROOT).contains(filtro.toLowerCase(Locale.ROOT))){
                contactosAuxFiltro.add(contacto)
            }
        }
        notifyDataSetChanged()
        return contactosAuxFiltro
    }

    fun cambiarTipoVista(){
        tipoTemplate = if(tipoTemplate == R.layout.template_contacto)
            R.layout.template_contacto_grid
        else
            R.layout.template_contacto

        notifyDataSetChanged()
    }

    private class ViewHolder(vista:View){
        var imagen: ImageView? = null
        var nombreCompleto: TextView? = null
        var telefono: TextView? = null

        init {
            imagen = vista.findViewById(R.id.imgAvatar)
            nombreCompleto = vista.findViewById(R.id.tvNombreApellido)
            telefono = vista.findViewById(R.id.tvNumTelefono)
        }
    }

}