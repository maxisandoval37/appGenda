package com.mxdigitalacademy.appgenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class NewContact : AppCompatActivity() {

    private var toolbar: Toolbar? = null

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

    fun lanzarMensaje(mensaje: String){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show()
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

                val contacto = ObjContacto(R.drawable.ic_launcher_foreground, nombre,apellido, tel1, tel2, email)

                MainActivity.listaObjContactos.add(contacto)
                lanzarMensaje("Contacto guardado")
                finish()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        iniciarToolbar()
        accionBotonGuardar()
        accionBotonCancelar()
    }
}