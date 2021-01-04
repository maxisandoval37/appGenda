package com.mxdigitalacademy.appgenda

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var listaVisual: ListView? = null
    private var adaptador: CustomAdapter? = null

    companion object{
        var listaObjContactos: ArrayList<ObjContacto> = ArrayList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {//nos permite asociar elems a nuestra interfaz
        menuInflater.inflate(R.menu.menu_main,menu)
        habilitarSearchView(menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addContact -> {
                val intent = Intent(this,NewContact::class.java)
                startActivity(intent)

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun habilitarSearchView(menu: Menu?){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda =menu?.findItem(R.id.app_bar_search)
        val vistaBusqueda = itemBusqueda?.actionView as SearchView
        vistaBusqueda.queryHint = "Buscar"

        vistaBusqueda.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        vistaBusqueda.setOnQueryTextFocusChangeListener { _, b ->
            if (!b)
                adaptador?.restaurarTodosLosContactos()
        }

        vistaBusqueda.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adaptador?.filtrarPorNombre(p0!!)
                return true
            }

        })
    }

    private fun iniciarToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    private fun listView(){
        agregarContactosDePrueba()
        listaVisual = findViewById<ListView>(R.id.listaContactos)

        adaptador = CustomAdapter(this,listaObjContactos)
        listaVisual?.adapter = adaptador

        listaVisual?.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(this,InfoContacto::class.java)
            intent.putExtra("ID",i.toString())
            startActivity(intent)
        }
    }

    private fun agregarContactosDePrueba(){
        listaObjContactos.add(ObjContacto(R.drawable.walter_white,"Walter","White","3478598723","","ww@bb.com"))
        listaObjContactos.add(ObjContacto(R.drawable.jesse_pinkman,"Jesse","Pinkman","112783498","2734892893","jesseP@gmail.com"))
        listaObjContactos.add(ObjContacto(R.drawable.gus_fring,"Gus","Fring","1132456745","","fring@gmail.com"))
        listaObjContactos.add(ObjContacto(R.drawable.walter_white,"Walter","Fake","312398723","23423434","fake@kk.com"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniciarToolbar()
        listView()
    }

    override fun onResume() {
        super.onResume()
        adaptador?.notifyDataSetChanged()
    }
}