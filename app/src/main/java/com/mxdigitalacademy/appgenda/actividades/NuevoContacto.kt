package com.mxdigitalacademy.appgenda.actividades

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import com.mxdigitalacademy.appgenda.gestorFotos.GestorFotos
import com.mxdigitalacademy.appgenda.R
import com.mxdigitalacademy.appgenda.modelo.ObjContacto
import com.mxdigitalacademy.appgenda.modelo.ObjContactos

class NuevoContacto : AppCompatActivity(){

    private var toolbar: Toolbar? = null
    private lateinit var gestorFotos: GestorFotos
    private var imgMuestra: ImageView? = null
    private var botonSelectFoto: Button? = null
    private var pathIMG: Uri? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->  {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hablitraBotonVolver(){
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun iniciarToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = ""
        setSupportActionBar(toolbar)
        hablitraBotonVolver()
    }

    private fun lanzarMensaje(mensaje: String){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show()
    }

    private fun agregarContactoEnAgenda(contacto: ObjContacto){
        if (!ObjContactos.existeTelefonoEnAgenda(contacto.getTelefonoPrincipal())){
            ObjContactos.agregarContacto(contacto)
            lanzarMensaje("Contacto guardado")
            finish()
        }
        else
            lanzarMensaje("El número "+contacto.getTelefonoPrincipal()+", se encuentra registrado")
    }

    private fun accionBotonGuardar(){
        val boton = findViewById<Button>(R.id.btnGuardar)

        boton.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etNombre).text.toString()
            val apellido = findViewById<EditText>(R.id.etApellido).text.toString()
            val tel1 = findViewById<EditText>(R.id.etTelPrincipal).text.toString()
            var tel2 = findViewById<EditText>(R.id.etTelSecundario).text.toString()
            val email = findViewById<EditText>(R.id.etEmail).text.toString()

            if (nombre.isNotEmpty() && apellido.isNotEmpty() && tel1.isNotEmpty() && email.isNotEmpty()){
                if (tel2.isEmpty())
                    tel2 = "No posee"

                try {
                    ObjContacto.datosValidos(nombre,apellido,tel1,tel2,email)
                    val contacto = ObjContacto(pathIMG.toString(), nombre, apellido, tel1, tel2, email)
                    agregarContactoEnAgenda(contacto)
                }
                catch(e: IllegalArgumentException){
                    lanzarMensaje(e.message.toString())
                }
            }
            else
                lanzarMensaje("Complete los campos restantes para continuar")
        }
    }

    private fun accionBotonCancelar(){
        val boton = findViewById<Button>(R.id.btnCancelar)

        boton.setOnClickListener {
            lanzarMensaje("Acción cancelada")
            finish()
        }
    }

    private fun inicializarElemsGraficos(){
        this.imgMuestra = findViewById(R.id.ivFotoMuestra)
        this.botonSelectFoto = findViewById(R.id.btnSeleccionarImg)
    }

    private fun iniciarGestorFotos(){
        this.gestorFotos = GestorFotos(this, this@NuevoContacto)

        this.botonSelectFoto?.setOnClickListener {
            this.gestorFotos.mostrarOpcionesImportacion()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        iniciarToolbar()
        inicializarElemsGraficos()
        iniciarGestorFotos()
        accionBotonGuardar()
        accionBotonCancelar()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val codSelecciona = gestorFotos.getCodSelecciona()
        val codFoto = gestorFotos.getCodFoto()
        this.pathIMG = gestorFotos.getPath()?.toUri()

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                codSelecciona -> {
                    this.pathIMG = data?.data
                    this.imgMuestra?.setImageURI(data?.data)
                }
                codFoto -> {
                    val bitmap = BitmapFactory.decodeFile(pathIMG.toString())
                    this.imgMuestra?.setImageBitmap(bitmap)
                }
            }
        }
    }
}