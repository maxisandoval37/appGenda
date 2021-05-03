package com.mxdigitalacademy.appgenda.solicitudHTTP

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class SolicitudHTTP(context: Context) {

    private var _context: Context? = null

    init {
        this._context = context
    }

    fun verficarConexionInternet(activity: AppCompatActivity):Boolean{
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return (networkInfo != null) && networkInfo.isConnected
    }


    fun solicitudHTTPVolley(url: String) {
        val colaDeSolicitudes = Volley.newRequestQueue(_context)
        val solicitud = StringRequest(Request.Method.GET, url, Response.Listener {
            var soli = it.replace("[","")
            soli =  soli.replace("]","")
            cargarDatos(soli)
        },
                Response.ErrorListener { error ->
                    when (error.toString()) {
                        "com.android.volley.NoConnectionError" -> Toast.makeText(_context, "Sin acceso a la Red", Toast.LENGTH_SHORT).show()
                    }
                })
        colaDeSolicitudes.add(solicitud)
    }

    private fun cargarDatos(json: String){
        val gson = Gson()

        val objeto = gson.fromJson(json, com.mxdigitalacademy.appgenda.solicitudHTTP.Response::class.java)
    }

}