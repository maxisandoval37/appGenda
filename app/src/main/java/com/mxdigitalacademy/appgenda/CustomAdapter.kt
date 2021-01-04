package com.mxdigitalacademy.appgenda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter(var context: Context, items:ArrayList<ObjContacto>): BaseAdapter() {

    var items:ArrayList<ObjContacto>? = null

    init {
        this.items=items
    }

    companion object{
        var contactosAux: ArrayList<ObjContacto> = ArrayList(MainActivity.listaObjContactos)
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var holder:ViewHolder?= null
        var vista:View? = p1

        if (vista==null){
            vista = LayoutInflater.from(context).inflate(R.layout.template_contacto,null)
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

    fun filtrarPorNombre(filtro: String){
        if (filtro.isNotEmpty()) {
            items?.clear()!!
            MainActivity.listaObjContactos.clear()
            for (contacto in contactosAux){
                for (i in filtro.indices) {
                    if (filtro.length <= contacto.getNombre().length){
                        if (filtro.toLowerCase()[i] == contacto.getNombre().toLowerCase()[i]){
                            if (!MainActivity.listaObjContactos.contains(contacto))
                                MainActivity.listaObjContactos.add(contacto)
                        }
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

    fun restaurarTodosLosContactos(){
        items?.clear()!!
        MainActivity.listaObjContactos.clear()
        MainActivity.listaObjContactos.addAll(contactosAux)
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