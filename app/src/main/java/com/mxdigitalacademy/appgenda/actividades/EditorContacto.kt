package com.mxdigitalacademy.appgenda.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.mxdigitalacademy.appgenda.R

class EditorContacto : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var telClickeado = MainActivity.nroTelefonoClick

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

        nombreEditor.setText(MainActivity.getContactoTelPrincipal(telClickeado)?.getNombre())
        apellidoEditor.setText(MainActivity.getContactoTelPrincipal(telClickeado)?.getApellido())
        tel1Editor.setText(MainActivity.getContactoTelPrincipal(telClickeado)?.getTelefonoPrincipal())
        tel2Editor.setText(MainActivity.getContactoTelPrincipal(telClickeado)?.getTelefonoSecundario())
        emailEditor.setText(MainActivity.getContactoTelPrincipal(telClickeado)?.getEmail())
    }

    private fun lanzarMensaje(mensaje: String){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show()
    }

    private fun actualizarObjetoContacto(nombre: String, apellido: String, tel1: String, tel2: String, email: String){
        //MainActivity.getContactoTelPrincipal(telClickeado)?.setImgAvatar(0)
        MainActivity.getContactoTelPrincipal(telClickeado)?.setNombre(nombre)
        MainActivity.getContactoTelPrincipal(telClickeado)?.setApellido(apellido)
        MainActivity.getContactoTelPrincipal(telClickeado)?.setTelPrincipal(tel1)
        MainActivity.getContactoTelPrincipal(telClickeado)?.setTelSecundario(tel2)
        MainActivity.getContactoTelPrincipal(telClickeado)?.setEmail(email)
    }

    private fun actualizarTelClickeado(tel: String){
        telClickeado = tel
        MainActivity.nroTelefonoClick = telClickeado
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
                if (MainActivity.existeTelefonoEnAgenda(tel1) && tel1 != telClickeado)
                        lanzarMensaje("El número $tel1, se encuentra registrado")
                else{
                    if (tel2.isEmpty())
                        tel2 = "No posee"
                    try {
                        actualizarObjetoContacto(nombre,apellido,tel1,tel2,email)
                        actualizarTelClickeado(tel1)
                        lanzarMensaje("Contacto actualizado")
                        finish()
                    }
                    catch(e: IllegalArgumentException){
                        lanzarMensaje(e.message.toString())
                    }
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

        iniciarToolbar()
        setearInfoInputsTexts()
        accionBotonGuardar()
        accionBotonCancelar()
    }
}