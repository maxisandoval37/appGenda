package com.mxdigitalacademy.appgenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class Editor : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var idContactoActual:Int = 0

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

        nombreEditor.setText(MainActivity.listaObjContactos[idContactoActual].getNombre())
        apellidoEditor.setText(MainActivity.listaObjContactos[idContactoActual].getApellido())
        tel1Editor.setText(MainActivity.listaObjContactos[idContactoActual].getTelefonoPrincipal())
        tel2Editor.setText(MainActivity.listaObjContactos[idContactoActual].getTelefonoSecundario())
        emailEditor.setText(MainActivity.listaObjContactos[idContactoActual].getEmail())
    }

    private fun lanzarMensaje(mensaje: String){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show()
    }

    private fun actualizarObjetoContacto(nombre: String, apellido: String, tel1: String, tel2: String, email: String){
        //MainActivity.listaObjContactos[idContactoActual].setImgAvatar(0)
        MainActivity.listaObjContactos[idContactoActual].setNombre(nombre)
        MainActivity.listaObjContactos[idContactoActual].setApellido(apellido)
        MainActivity.listaObjContactos[idContactoActual].setTelPrincipal(tel1)
        MainActivity.listaObjContactos[idContactoActual].setTelSecundario(tel2)
        MainActivity.listaObjContactos[idContactoActual].setEmail(email)
    }

    private fun accionBotonGuardar(){
        val botonGuardar = findViewById<Button>(R.id.btnGuardarEditor)

        botonGuardar.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etNombreEditor).text.toString()
            val apellido = findViewById<EditText>(R.id.etApellidoEditor).text.toString()
            val tel1 = findViewById<EditText>(R.id.etTelPrincipalEditor).text.toString()
            var tel2 = findViewById<EditText>(R.id.etTelSecundarioEditor).text.toString()
            val email = findViewById<EditText>(R.id.etEmailEditor).text.toString()

            if (nombre.isNotEmpty() && apellido.isNotEmpty() && tel1.isNotEmpty() && email.isNotEmpty()){
                if (tel2.isEmpty())
                    tel2 = "No posee"
                try {
                    actualizarObjetoContacto(nombre,apellido,tel1,tel2,email)
                    lanzarMensaje("Contacto actualizado")
                    finish()
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
        val boton = findViewById<Button>(R.id.btnCancelarEditor)

        boton.setOnClickListener {
            lanzarMensaje("Acción cancelada")
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        idContactoActual= intent.getStringExtra("ID_EDITOR")?.toInt()!!
        iniciarToolbar()
        setearInfoInputsTexts()
        accionBotonGuardar()
        accionBotonCancelar()
    }
}