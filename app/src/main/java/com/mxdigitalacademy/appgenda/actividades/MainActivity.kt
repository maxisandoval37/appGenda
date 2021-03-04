package com.mxdigitalacademy.appgenda.actividades

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mxdigitalacademy.appgenda.R
import com.mxdigitalacademy.appgenda.adaptador.ClickListener
import com.mxdigitalacademy.appgenda.adaptador.CustomAdapter
import com.mxdigitalacademy.appgenda.adaptador.LongClickListener
import com.mxdigitalacademy.appgenda.funciones.Funciones
import com.mxdigitalacademy.appgenda.modelo.ObjContacto
import com.mxdigitalacademy.appgenda.permisos.SolicitudPermisos

class MainActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var listaRecyclerView:RecyclerView? = null
    private var vistaBusqueda:SearchView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var viewSwitcher: ViewSwitcher? = null

    private var actionMode: ActionMode? = null
    private var estadoActionMode = false
    private var callback: ActionMode.Callback? = null

    companion object{
        var listaObjContactos: ArrayList<ObjContacto> = ArrayList()
        var adaptador: CustomAdapter? = null
        var nroTelefonoClick: String = ""

        fun agregarContacto(contacto: ObjContacto){
            listaObjContactos.add(contacto)
            adaptador?.addItem(contacto)
            adaptador?.notifyDataSetChanged()
        }

        fun getContactoTelPrincipal(tel1: String): ObjContacto?{
            for (contacto in listaObjContactos){
                if (contacto.getTelefonoPrincipal() == tel1)
                    return contacto
            }
            return null
        }

        fun eliminarContactoPorTelefono(tel1: String){
            val itemBorrar: ObjContacto?

            for (x:Int in 0 .. listaObjContactos.size){
                if (listaObjContactos[x].getTelefonoPrincipal() == tel1){
                    itemBorrar= listaObjContactos[x]
                    listaObjContactos.remove(itemBorrar)
                    adaptador?.removeItem(itemBorrar)
                    adaptador?.notifyDataSetChanged()
                    break
                }
            }
        }

        fun existeTelefonoEnAgenda(tel1: String): Boolean{
            var existe = false
            for (contacto in listaObjContactos){
                existe = existe or (contacto.getTelefonoPrincipal() == tel1)
            }
            return existe
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        habilitarSearchView(menu)
        habilitarSwitchViewStyle(menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addContact -> {
                val intent = Intent(this, NuevoContacto::class.java)
                startActivity(intent)
                eliminarTerminosBuscados()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun habilitarSearchView(menu: Menu?){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.app_bar_search)
        vistaBusqueda = itemBusqueda?.actionView as SearchView
        vistaBusqueda?.queryHint = "Buscar"

        vistaBusqueda?.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        vistaBusqueda?.setOnQueryTextFocusChangeListener { _, b ->
            if (!b)
                eliminarTerminosBuscados()
        }

        vistaBusqueda?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adaptador?.filter(listaObjContactos,p0!!)
                return true
            }

        })
    }

    private fun habilitarSwitchViewStyle(menu: Menu?){
        viewSwitcher = findViewById(R.id.viewSwitcher)

        val itemSwitch = menu?.findItem(R.id.app_bar_switch)
        itemSwitch?.setActionView(R.layout.switch_item)
        val switchView = itemSwitch?.actionView?.findViewById<Switch>(R.id.switchViewStyle)

        switchView?.setOnCheckedChangeListener { _, b ->
            switchCambiarTipoDeVista(b)
        }

    }

    private fun switchCambiarTipoDeVista(estado: Boolean){
        eliminarTerminosBuscados()
        viewSwitcher?.showNext()
        if (estado)
            inicializarListaRecyclerView(R.layout.template_contacto_grid)
        else
            inicializarListaRecyclerView(R.layout.template_contacto)
    }

    private fun iniciarToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    private fun inicializarListaRecyclerView(tipoVista: Int){
        listaRecyclerView = findViewById(R.id.rv_Contactos)
        layoutManager = LinearLayoutManager(this)

        if (tipoVista == R.layout.template_contacto_grid){
            layoutManager = GridLayoutManager(this, 2)
            listaRecyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        }

        listaRecyclerView?.layoutManager = layoutManager
        listaRecyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        accionesListaRecyclerView(tipoVista)

        listaRecyclerView?.adapter = adaptador
    }

    private fun accionesListaRecyclerView(tipoVista: Int){
        adaptador = CustomAdapter(listaObjContactos, tipoVista,object: ClickListener {
            override fun onClick(vista: View, index: Int) {

                nroTelefonoClick = vista.findViewById<TextView>(R.id.tvNumTelefono).text.toString()
                val intent = Intent(this@MainActivity, InfoContacto::class.java)
                startActivity(intent)

                eliminarTerminosBuscados()
            }
        }, object: LongClickListener {
            override fun longClick(vista: View, index: Int) {

                if (!estadoActionMode){
                    startSupportActionMode(callback!!)
                    estadoActionMode = true
                    adaptador?.seleccionarItem(index)
                }
                else
                    adaptador?.seleccionarItem(index)

                actionMode?.title = Funciones.setCantTituloSeleccionados(adaptador?.getItemsSeleccionadosCount()!!)
            }

        })
    }

    private fun eliminarTerminosBuscados(){
        vistaBusqueda?.setQuery("",true)
    }

    private fun activarActionMode(){
        callback = object: ActionMode.Callback{

            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                accionesItemsToolbarContextual(p1!!)

                adaptador?.terminarActionMode()
                actionMode?.finish()
                estadoActionMode = false
                return true
            }

            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                supportActionBar?.hide()
                actionMode = p0
                adaptador?.iniciarActionMode()
                menuInflater.inflate(R.menu.menu_contextual,p1!!)
                return true
            }

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(p0: ActionMode?) {
                adaptador?.destruirActionMode()
                estadoActionMode = false
                supportActionBar?.show()
            }
        }
    }

    private fun accionesItemsToolbarContextual(item: MenuItem){
        when (item.itemId){
            R.id.ItemEliminar -> adaptador?.eliminarSeleccionados()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SolicitudPermisos(this,this@MainActivity).validarPermisos()
        Funciones.agregarContactosDePrueba(listaObjContactos)
        iniciarToolbar()
        inicializarListaRecyclerView(R.layout.template_contacto)
        activarActionMode()
    }

    override fun onResume() {
        super.onResume()
        adaptador?.notifyDataSetChanged()
    }
}