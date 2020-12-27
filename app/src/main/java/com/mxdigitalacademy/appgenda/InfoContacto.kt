package com.mxdigitalacademy.appgenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class InfoContacto : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto)

        iniciarToolbar()
    }
}