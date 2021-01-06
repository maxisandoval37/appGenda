package com.mxdigitalacademy.appgenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class InfoContacto : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var telClickeado:String = ""

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_info_contacto,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->  {
                finish()
                return true
            }

            R.id.editarContacto -> {
                val intent = Intent(this,Editor::class.java)
                intent.putExtra("nroTelefonoClick",telClickeado)
                startActivity(intent)
                return true
            }

            R.id.borrarContacto -> {
                MainActivity.elimilarContactoPorTelefono(telClickeado)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun iniciarToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = ""
        setSupportActionBar(toolbar)
        hablitraBotonVolver()
    }

    private fun hablitraBotonVolver(){
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setearInfoElemsVisuales(){
        val fotoAvatar = findViewById<ImageView>(R.id.ivAvatar)
        val nombreCompleto = findViewById<TextView>(R.id.tvNombreCompleto)
        val tel1 = findViewById<TextView>(R.id.tvTel1)
        val tel2 = findViewById<TextView>(R.id.tvTel2)
        val email = findViewById<TextView>(R.id.tvEmail)

        val contactoAux = MainActivity.getContactoTelPrincipal(telClickeado)

        contactoAux?.getImgAvatar()?.let { fotoAvatar.setImageResource(it) }
        nombreCompleto.text = contactoAux?.getNombreCompleto()
        tel1.text = contactoAux?.getTelefonoPrincipal()
        tel2.text = contactoAux?.getTelefonoSecundario()
        email.text = contactoAux?.getEmail()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_contacto)

        telClickeado = intent.getStringExtra("nroTelefonoClick").toString()
        iniciarToolbar()
        setearInfoElemsVisuales()
    }

    override fun onResume() {
        super.onResume()
        setearInfoElemsVisuales()
    }
}