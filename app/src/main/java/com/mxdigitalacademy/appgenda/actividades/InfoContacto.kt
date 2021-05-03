package com.mxdigitalacademy.appgenda.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.mxdigitalacademy.appgenda.R
import com.mxdigitalacademy.appgenda.gestorFotos.GestorFotos
import com.mxdigitalacademy.appgenda.modelo.ObjContactos
import com.mxdigitalacademy.appgenda.solicitudHTTP.SolicitudHTTP

class InfoContacto : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var solicitudHTTP: SolicitudHTTP? = null

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
                val intent = Intent(this, EditorContacto::class.java)
                startActivity(intent)
                return true
            }

            R.id.borrarContacto -> {
                ObjContactos.eliminarContactoPorTelefono(ObjContactos.nroTelefonoClick)
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

        val contactoAux = ObjContactos.getContactoTelPrincipal(ObjContactos.nroTelefonoClick)

        GestorFotos.setearImgView(fotoAvatar, contactoAux?.getImgAvatar().toString(),R.drawable.avatar_defecto,750)
        nombreCompleto.text = contactoAux?.getNombreCompleto()
        tel1.text = contactoAux?.getTelefonoPrincipal()
        tel2.text = contactoAux?.getTelefonoSecundario()
        email.text = contactoAux?.getEmail()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_contacto)

        iniciarToolbar()
        setearInfoElemsVisuales()
        solicitudHTTP = SolicitudHTTP(this)
    }

    override fun onResume() {
        super.onResume()
        setearInfoElemsVisuales()
    }
}