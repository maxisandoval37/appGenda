package com.mxdigitalacademy.appgenda.permisos

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlin.system.exitProcess

class SolicitudPermisos(context: Context, activity: Activity) : ActivityCompat.OnRequestPermissionsResultCallback{

    private var _context: Context? = null
    private var _activity: Activity? = null

    init {
        this._context = context
        this._activity = activity
    }

    fun iniciarSolicitud(){
        validaPermisos()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (!(grantResults.size == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED))
                solicitarPermisosManual()
        }
    }

    private fun solicitarPermisosManual() {
        val opciones = arrayOf<CharSequence>("si", "no")
        val alertOpciones = AlertDialog.Builder(_context!!)
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?")
        alertOpciones.setItems(opciones) { dialogInterface, i ->
            if (opciones[i] == "si") {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri: Uri = Uri.fromParts("package", _context!!.packageName, null)
                intent.data = uri
                _context!!.startActivity(intent)
            } else {
                Toast.makeText(_context!!, "Los permisos no fueron aceptados", Toast.LENGTH_SHORT).show()
                dialogInterface.dismiss()
                exitProcess(0)
            }
        }
        alertOpciones.show()
    }

    fun validaPermisos(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true

        if (_context!!.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && _context!!.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return true

        if (permisosDenegados())
            cargarDialogoRecomendacion()
        else
            ActivityCompat.requestPermissions(_activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), 100)

        return false
    }

    fun permisosDenegados(): Boolean{
        return ActivityCompat.shouldShowRequestPermissionRationale(_activity!!,Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(_activity!!,Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun cargarDialogoRecomendacion() {
        val dialogo = AlertDialog.Builder(_context!!)
        dialogo.setTitle("Permisos Desactivados")
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App")
        dialogo.setPositiveButton("Aceptar") { _, _ ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(_activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), 100)
            }
        }
        dialogo.show()
    }

}