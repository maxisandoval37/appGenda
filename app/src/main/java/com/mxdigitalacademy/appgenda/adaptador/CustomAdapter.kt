package com.mxdigitalacademy.appgenda.adaptador

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.mxdigitalacademy.appgenda.R
import com.mxdigitalacademy.appgenda.funciones.Funciones
import com.mxdigitalacademy.appgenda.modelo.ObjContacto
import java.util.*
import kotlin.collections.ArrayList


class CustomAdapter(items:ArrayList<ObjContacto>, var tipoVista: Int, var clickListener: ClickListener, var longClickListener: LongClickListener): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var _items: ArrayList<ObjContacto>? = null
    private var _viewHolder: ViewHolder? = null
    private var _multiseleccion = false
    private var _itemsSeleccionados: ArrayList<Int>? = null

    init {
        this._items = items
        this._itemsSeleccionados = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(tipoVista, parent,false)
        this._viewHolder = ViewHolder(vista, clickListener, longClickListener)
        return this._viewHolder!!
    }

    override fun getItemCount(): Int {
        return _items?.count()!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = _items!![position]
        holder.setImagen(item.getImgAvatar())
        holder.setNombreCompleto(item.getNombreCompleto())
        holder.setTelefono1(item.getTelefonoPrincipal())

        if (existeElemEnItemsSelec(position))
            holder.setColorVista(Color.LTGRAY)
        else
            holder.setColorVista(Color.WHITE)
    }

    fun addItem(contacto: ObjContacto){
        if (!this._items?.contains(contacto)!!)
            this._items?.add(contacto)
        notifyDataSetChanged()
    }

    fun removeItem(contacto: ObjContacto){
        if (this._items?.contains(contacto)!!)
            this._items?.remove(contacto)
        notifyDataSetChanged()
    }

    fun filter(contactos: List<ObjContacto>, filtro: String) {
        val contactosAuxFiltro: ArrayList<ObjContacto> = ArrayList()

        for (contacto in contactos) {
            if (contacto.getNombreCompleto().toLowerCase(Locale.ROOT).contains(filtro.toLowerCase(Locale.ROOT))){
                contactosAuxFiltro.add(contacto)
            }
        }
        this._items = contactosAuxFiltro
        notifyDataSetChanged()
    }

    fun iniciarActionMode(){
        this._multiseleccion = true
    }

    fun destruirActionMode(){
        this._multiseleccion = false
        this._itemsSeleccionados?.clear()
        notifyDataSetChanged()
    }

    fun terminarActionMode(){
        this._itemsSeleccionados?.clear()
        this._multiseleccion = false
        notifyDataSetChanged()
    }

    fun seleccionarItem(index: Int){
            if (this._multiseleccion){
                if (existeElemEnItemsSelec(index)){
                    this._itemsSeleccionados?.remove(index)
                }
                else{
                    this._itemsSeleccionados?.add(index)
                }
                notifyDataSetChanged()
            }
    }

    private fun existeElemEnItemsSelec(index: Int): Boolean{
        return this._itemsSeleccionados?.contains((index))!!
    }

    fun getItemsSeleccionadosCount(): Int{
        return this._itemsSeleccionados?.count()!!
    }

    fun eliminarSeleccionados(){
        if (getItemsSeleccionadosCount()>0){
            val itemsEliminados = ArrayList<ObjContacto>()

            for(index in this._itemsSeleccionados!!){
                itemsEliminados.add(this._items?.get(index)!!)
            }

            this._items?.removeAll(itemsEliminados)
            this._itemsSeleccionados?.clear()
        }
    }

    class ViewHolder(vista: View, clickListener: ClickListener, longClickListener: LongClickListener):RecyclerView.ViewHolder(vista), View.OnClickListener,View.OnLongClickListener{
        private var _vista = vista
        private var _clickListener: ClickListener? = null
        private var _longClickListener: LongClickListener? = null
        private var _imagen: ImageView? = null
        private var _nombreCompleto: TextView? = null
        private var _telefono: TextView? = null

        init {
            this._clickListener = clickListener
            this._longClickListener = longClickListener

            this._vista.setOnClickListener(this)
            this._vista.setOnLongClickListener(this)


            this._imagen = this._vista.findViewById(R.id.imgAvatar)
            this._nombreCompleto = this._vista.findViewById(R.id.tvNombreApellido)
            this._telefono = this._vista.findViewById(R.id.tvNumTelefono)
       }

        fun setImagen(path: String){
            if (path=="null")
                this._imagen?.setImageResource(R.drawable.avatar_defecto)
            else{
                if (Funciones.esFormatoNumerico(path))
                    this._imagen?.setImageResource(path.toInt())
                else
                    this._imagen?.setImageURI(path.toUri())
            }
        }

        fun setNombreCompleto(nombre: String){
            this._nombreCompleto?.text = nombre
        }

        fun setTelefono1(telefono: String){
            this._telefono?.text = telefono
        }

        fun setColorVista(color: Int){
            this._vista.setBackgroundColor(color)
        }

        override fun onClick(p0: View?) {
            this._clickListener?.onClick(p0!!,adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            this._longClickListener?.longClick(p0!!,adapterPosition)
            return true
        }
    }

}