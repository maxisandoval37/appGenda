package com.mxdigitalacademy.appgenda.gestorFotos

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import android.app.AlertDialog.Builder
import android.os.Environment
import java.io.File
import android.content.Context
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.mxdigitalacademy.appgenda.funciones.Funciones

class GestorFotos (context: Context, activity: Activity){

    private val _rutaImagen = "misFotos/AppGenda"
    private val _codSeleccionFoto = 10
    private val _codTomarFoto = 20

    private var _pathFotoActual: String? = null
    private var _context: Context? = null
    private var _activity: Activity? = null

    companion object{
        fun setearImgView(iv: ImageView, path: String, imgDefecto: Int, tamIMG: Int) {
            if (path=="null")
                iv.setImageResource(imgDefecto)
            else{
                if (Funciones.esFormatoNumerico(path))
                    iv.setImageResource(path.toInt())
                else{
                    iv.setImageURI(path.toUri())
                    iv.layoutParams.height = tamIMG
                }
            }
        }
    }

    init {
        this._context = context
        this._activity = activity
    }

    fun mostrarOpcionesImportacion() {
        val opciones = arrayOf<CharSequence>("Tomar Foto", "Cargar Imagen", "Cancelar")
        val alertOpciones = Builder(_context!!)
        alertOpciones.setTitle("Seleccione una opción")
        alertOpciones.setItems(opciones) { dialogInterface, i ->
            when(opciones[i]){
                "Tomar Foto" -> {
                    val permisoCamera = Manifest.permission.CAMERA
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(_activity!!, permisoCamera))
                        tomarFoto()
                    else
                        mostrarDialogoSolicitudPermisos()
                }
                "Cargar Imagen" -> cargarFoto()
                "Cancelar" -> dialogInterface.dismiss()
            }
        }
        alertOpciones.show()
    }

    private fun tomarFoto() {
        val fileImagen = File(Environment.getExternalStorageDirectory(), _rutaImagen)
        var isCreada: Boolean = fileImagen.exists()
        var nombreImagen = ""
        if (!isCreada)
            isCreada = fileImagen.mkdirs()

        if (isCreada)
            nombreImagen = (System.currentTimeMillis() / 1000).toString() + ".jpg"

        _pathFotoActual=Environment.getExternalStorageDirectory().absolutePath + File.separator+_rutaImagen+File.separator+nombreImagen

        intentTomarFoto()
    }

    private fun intentTomarFoto(){
        val imagen = File(_pathFotoActual!!)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val authorities = _context!!.packageName + ".provider"
            val imageUri: Uri = FileProvider.getUriForFile(_context!!, authorities, imagen)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        }
        else
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen))

        _activity?.startActivityForResult(intent, _codTomarFoto)
    }

    private fun cargarFoto(){
        val intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/"
        _activity!!.startActivityForResult(Intent.createChooser(intent, "Seleccione la Aplicación"), _codSeleccionFoto)
    }

    private fun mostrarDialogoSolicitudPermisos(){
        val dialogo = Builder(_context)
        dialogo.setTitle("Permisos Desactivados")
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App")
        dialogo.setPositiveButton("Aceptar") { _, _ ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(_activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), 100)
            }
        }
        dialogo.show()
    }

    fun getPath(): String?{
        return this._pathFotoActual
    }

    fun getCodSelecciona(): Int{
        return  this._codSeleccionFoto
    }

    fun getCodFoto(): Int{
        return this._codTomarFoto
    }
}