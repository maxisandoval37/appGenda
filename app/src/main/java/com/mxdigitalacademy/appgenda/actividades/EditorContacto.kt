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
import com.mxdigitalacademy.appgenda.R
import com.mxdigitalacademy.appgenda.gestorFotos.GestorFotos
import com.mxdigitalacademy.appgenda.modelo.ObjContactos

class EditorContacto : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var telClickeado = ObjContactos.nroTelefonoClick
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

    private fun setearInfoInputsTexts(){
        val nombreEditor = findViewById<EditText>(R.id.etNombreEditor)
        val apellidoEditor = findViewById<EditText>(R.id.etApellidoEditor)
        val tel1Editor = findViewById<EditText>(R.id.etTelPrincipalEditor)
        val tel2Editor = findViewById<EditText>(R.id.etTelSecundarioEditor)
        val emailEditor = findViewById<EditText>(R.id.etEmailEditor)

        GestorFotos.setearImgView(this.imgMuestra!!, ObjContactos.getContactoTelPrincipal(telClickeado)?.getImgAvatar().toString(),R.drawable.avatar_defecto,320)
        nombreEditor.setText(ObjContactos.getContactoTelPrincipal(telClickeado)?.getNombre())
        apellidoEditor.setText(ObjContactos.getContactoTelPrincipal(telClickeado)?.getApellido())
        tel1Editor.setText(ObjContactos.getContactoTelPrincipal(telClickeado)?.getTelefonoPrincipal())
        tel2Editor.setText(ObjContactos.getContactoTelPrincipal(telClickeado)?.getTelefonoSecundario())
        emailEditor.setText(ObjContactos.getContactoTelPrincipal(telClickeado)?.getEmail())
    }

    private fun lanzarMensaje(mensaje: String){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show()
    }

    private fun accionBotonGuardar() {
        val botonGuardar = findViewById<Button>(R.id.btnGuardarEditor)

        botonGuardar.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etNombreEditor).text.toString()
            val apellido = findViewById<EditText>(R.id.etApellidoEditor).text.toString()
            var tel2 = findViewById<EditText>(R.id.etTelSecundarioEditor).text.toString()
            val email = findViewById<EditText>(R.id.etEmailEditor).text.toString()

            if (nombre.isNotEmpty() && apellido.isNotEmpty() && email.isNotEmpty()) {
                if (tel2.isEmpty())
                    tel2 = "No posee"
                try {
                    ObjContactos.actualizarObjetoContacto(this.telClickeado, detectarCambiosAvatar(), nombre, apellido, tel2, email)
                    lanzarMensaje("Contacto actualizado")
                    finish()
                } catch (e: IllegalArgumentException) {
                    lanzarMensaje(e.message.toString())
                }
            } else
                lanzarMensaje("Complete los campos restantes para continuar")
        }
    }

    private fun detectarCambiosAvatar(): String{
        if (this.pathIMG == null)
            return ObjContactos.getContactoTelPrincipal(telClickeado)?.getImgAvatar().toString()
        return pathIMG.toString()
    }

    private fun inicializarElemsGraficos(){
        this.imgMuestra = findViewById(R.id.ivFotoMuestraEditor)
        this.botonSelectFoto = findViewById(R.id.btnSeleccionarImgEditor)
    }

    private fun iniciarGestorFotos(){
        this.gestorFotos = GestorFotos(this, this@EditorContacto)

        this.botonSelectFoto?.setOnClickListener {
            this.gestorFotos.mostrarOpcionesImportacion()
        }
    }

    private fun accionBotonCancelar(){
        val boton = findViewById<Button>(R.id.btnCancelarEditor)

        boton.setOnClickListener {
            lanzarMensaje("Acción cancelada")
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor_contacto)

        findViewById<EditText>(R.id.etTelPrincipalEditor).isEnabled = false
        iniciarToolbar()
        inicializarElemsGraficos()
        iniciarGestorFotos()
        setearInfoInputsTexts()
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
                    ObjContactos.getContactoTelPrincipal(telClickeado)?.setImgAvatar(pathIMG.toString())
                }
            }
        }
    }
}