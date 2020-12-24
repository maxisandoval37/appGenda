package com.mxdigitalacademy.appgenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {//nos permite asociar elems a nuestra interfaz
        menuInflater.inflate(R.menu.menu_contactos,menu)//agregamos la toolbar al MainActivity
        return super.onCreateOptionsMenu(menu)
    }

    private fun iniciarToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = ""
        setSupportActionBar(toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniciarToolbar()
    }
}