package com.mxdigitalacademy.appgenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var listaObjContactos: ArrayList<ObjContacto> = ArrayList()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {//nos permite asociar elems a nuestra interfaz
        menuInflater.inflate(R.menu.menu_toolbar,menu)//agregamos la toolbar al MainActivity
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

    private fun iniciarToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    private fun listView(){
        listViewElements()
        val listaVisual = findViewById<ListView>(R.id.listaContactos)

        val adaptador = CustomAdapter(this,listaObjContactos)
        listaVisual.adapter = adaptador

        listaVisual.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            Toast.makeText(this, listaObjContactos[i].getNombreCompleto(), Toast.LENGTH_SHORT).show()
            //setear valores en el info_contacto
            val intent = Intent(this,InfoContacto::class.java)
            startActivity(intent)
        }
    }

    private fun listViewElements(){
        listaObjContactos.add(ObjContacto(R.drawable.walter_white,"Walter","White","3478598723","","ww@bb.com"))
        listaObjContactos.add(ObjContacto(R.drawable.jesse_pinkman,"Jesse","Pinkman","112783498","2734892893","jesseP@gmail.com"))
        listaObjContactos.add(ObjContacto(R.drawable.gus_fring,"Gus","Fring","1132456745","","fring@gmail.com"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniciarToolbar()
        listView()
    }
}