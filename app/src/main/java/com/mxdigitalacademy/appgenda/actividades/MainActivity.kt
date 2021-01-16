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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mxdigitalacademy.appgenda.R
import com.mxdigitalacademy.appgenda.adaptador.ClickListener
import com.mxdigitalacademy.appgenda.adaptador.CustomAdapter
import com.mxdigitalacademy.appgenda.adaptador.LongClickListener
import com.mxdigitalacademy.appgenda.modelo.ObjContacto

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

        fun agregarContacto(contacto: ObjContacto){
            listaObjContactos.add(contacto)
            adaptador?.addItem(contacto)
        }

        fun getContactoTelPrincipal(tel1: String): ObjContacto?{
            for (contacto in listaObjContactos){
                if (contacto.getTelefonoPrincipal() == tel1)
                    return contacto
            }
            return null
        }

        fun eliminarContactoPorTelefono(tel1: String){
            for (x:Int in 0 .. listaObjContactos.size){
                if (listaObjContactos[x].getTelefonoPrincipal() == tel1){
                    adaptador?.removeItem(listaObjContactos[x])
                    listaObjContactos.remove(listaObjContactos[x])
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
                val intent = Intent(this, NewContact::class.java)
                startActivity(intent)
                eliminarTerminosBuscados()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun iniciarToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    private fun inicializarListaRecyclerView(tipoVista: Int){
        listaRecyclerView = findViewById(R.id.rv_Contactos)
        layoutManager = LinearLayoutManager(this)
        listaRecyclerView?.layoutManager = layoutManager

        accionesListaRecyclerView(tipoVista)

        listaRecyclerView?.adapter = adaptador
    }

    private fun accionesListaRecyclerView(tipoVista: Int){
        adaptador = CustomAdapter(listaObjContactos, tipoVista,object: ClickListener {
            override fun onClick(vista: View, index: Int) {

                    val nroTelefonoClick = vista.findViewById<TextView>(R.id.tvNumTelefono).text.toString()

                    val intent = Intent(this@MainActivity, InfoContacto::class.java)
                    intent.putExtra("nroTelefonoClick",nroTelefonoClick)
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

                setTituloSeleccionados(adaptador?.getItemsSeleccionadosCount()!!)
            }

        })
    }

    private fun eliminarTerminosBuscados(){
        vistaBusqueda?.setQuery("",true)
    }

    private fun setTituloSeleccionados(n: Int){
        if (n > 1)
            actionMode?.title = "$n Seleccionados"
        else
            actionMode?.title = "$n Seleccionado"
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
            }

        }
    }

    private fun agregarContactosDePrueba(){
        if (listaObjContactos.isEmpty()){
            listaObjContactos.add(ObjContacto(R.drawable.walter_white, "Walter", "White", "3478598723", "", "ww@bb.com"))
            listaObjContactos.add(ObjContacto(R.drawable.jesse_pinkman, "Jesse", "Pinkman", "112783498", "2734892893", "jesseP@gmail.com"))
            listaObjContactos.add(ObjContacto(R.drawable.gus_fring, "Gus", "Fring", "1132456745", "", "fring@gmail.com"))
            listaObjContactos.add(ObjContacto(R.drawable.walter_white, "Walter", "Fake", "312398723", "23423434", "fake@kk.com"))
            listaObjContactos.add(ObjContacto(R.drawable.mike_ehrmantraut, "Mike", "Ehrmantraut", "113422444", "1163246952", "mikeE2021@email.com"))
            listaObjContactos.add(ObjContacto(R.drawable.saul_goodman, "Saul", "Goodman", "45312344", "23423412", "better_call_saul@email.com"))
            listaObjContactos.add(ObjContacto(R.drawable.lydia_rodarte, "Lydia", "Rodarte", "56245784", "43523421", "Rodarte@box.com"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        agregarContactosDePrueba()
        iniciarToolbar()
        inicializarListaRecyclerView(R.layout.template_contacto)
        activarActionMode()
    }

    override fun onResume() {
        super.onResume()
        adaptador?.notifyDataSetChanged()
    }
}