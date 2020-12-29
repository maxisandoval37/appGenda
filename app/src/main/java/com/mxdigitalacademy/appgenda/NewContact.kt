package com.mxdigitalacademy.appgenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class NewContact : AppCompatActivity() {

    private var toolbar: Toolbar? = null

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

    private fun accionBotonGuardar(){
        val boton = findViewById<Button>(R.id.btnGuardar)

        boton.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etNombre)
            val apellido = findViewById<EditText>(R.id.etApellido)
            val tel1 = findViewById<EditText>(R.id.etTelPrincipal)
            val tel2 = findViewById<EditText>(R.id.etTelSecundario)
            val email = findViewById<EditText>(R.id.etEmail)

            val contacto = ObjContacto(
                    R.drawable.ic_launcher_foreground,
                    nombre.text.toString(),apellido.text.toString(),
                    tel1.text.toString(),
                    tel2.text.toString(),
                    email.text.toString())

            MainActivity.listaObjContactos.add(contacto)
            Toast.makeText(this,"Contacto guardado",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        iniciarToolbar()
        accionBotonGuardar()
    }
}