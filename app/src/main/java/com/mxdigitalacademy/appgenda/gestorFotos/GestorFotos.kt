package com.mxdigitalacademy.appgenda.gestorFotos

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
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.mxdigitalacademy.appgenda.funciones.Funciones

class GestorFotos (context: Context, activity: Activity){

    private val rutaImagen = "misFotos/AppGenda"

    private val _codSelecciona = 10
    private val _codFoto = 20

    private var _path: String? = null

    private var _context: Context? = null
    private var _activity: Activity? = null

    companion object{
        fun setearImgView(iv: ImageView, path: String, imgDefecto: Int) {
            if (path=="null")
                iv.setImageResource(imgDefecto)
            else{
                if (Funciones.esFormatoNumerico(path))
                    iv.setImageResource(path.toInt())
                else
                    iv.setImageURI(path.toUri())
            }
        }
    }

    init {
        this._context = context
        this._activity = activity
    }

    private fun tomarFoto() {
        val fileImagen = File(Environment.getExternalStorageDirectory(), rutaImagen)
        var isCreada: Boolean = fileImagen.exists()
        var nombreImagen = ""
        if (!isCreada)
            isCreada = fileImagen.mkdirs()

        if (isCreada)
            nombreImagen = (System.currentTimeMillis() / 1000).toString() + ".jpg"

        _path=Environment.getExternalStorageDirectory().absolutePath + File.separator+rutaImagen+File.separator+nombreImagen

        val imagen = File(_path!!)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val authorities = _context!!.packageName + ".provider"
            val imageUri: Uri = FileProvider.getUriForFile(_context!!, authorities, imagen)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen))
        }
        _activity!!.startActivityForResult(intent, _codFoto)
    }

    fun cargarImagen() {
        val opciones = arrayOf<CharSequence>("Tomar Foto", "Cargar Imagen", "Cancelar")
        val alertOpciones = Builder(_context!!)
        alertOpciones.setTitle("Seleccione una Opción")
        alertOpciones.setItems(opciones) { dialogInterface, i ->
            if (opciones[i] == "Tomar Foto") {
                tomarFoto()
            } else {
                if (opciones[i] == "Cargar Imagen") {
                    val intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    intent.type = "image/"
                    _activity!!.startActivityForResult(Intent.createChooser(intent, "Seleccione la Aplicación"), _codSelecciona)
                } else {
                    dialogInterface.dismiss()
                }
            }
        }
        alertOpciones.show()
    }

    fun getPath(): String?{
        return this._path
    }

    fun getCodSelecciona(): Int{
        return  this._codSelecciona
    }

    fun getCodFoto(): Int{
        return this._codFoto
    }
}