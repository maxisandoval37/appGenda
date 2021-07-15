package com.mxdigitalacademy.appgenda.actividades

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mxdigitalacademy.appgenda.R
import com.mxdigitalacademy.appgenda.gestorFotos.GestorFotos
import com.mxdigitalacademy.appgenda.modelo.ObjContactos
import com.mxdigitalacademy.appgenda.solicitudHTTP.SolicitudHTTP

class InfoContacto : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var solicitudHTTP: SolicitudHTTP? = null
    private var autorActual=""

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

            R.id.mostrarCita -> {
                if (solicitudHTTP?.verficarConexionInternet(this@InfoContacto)!!){
                    val autor = autorActual.replace(" ","+")
                    solicitudHTTP?.solicitudHTTPVolley("https://www.breakingbadapi.com/api/quote/random?author=$autor")
                } else
                    Toast.makeText(this, "No hay conexión a internet 😢", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.editarContacto -> {
                val intent = Intent(this, EditorContacto::class.java)
                startActivity(intent)
                return true
            }

            R.id.borrarContacto -> {
                dialogoEliminarContacto()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dialogoEliminarContacto() {
        AlertDialog.Builder(this)
            .setTitle("Eliminar contacto")
            .setMessage("¿Quieres eliminar este contacto?")
            .setPositiveButton("Si") { _, _ ->
                ObjContactos.eliminarContactoPorTelefono(ObjContactos.nroTelefonoClick)
                finish()
                overridePendingTransition(0, 0)
            }
            .setNegativeButton("No", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
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
        autorActual = nombreCompleto.text.toString()
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
