package com.example.myrecipe.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.myrecipe.databinding.DialogFormularioImagemBinding
import com.example.myrecipe.ui.extensions.tryLoadImage
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FormularioDialog(private val context: Context) {

    fun alterarImagem(urlImagem: String? = null, carregarImagem: (url: String) -> Unit) {

        var binding = DialogFormularioImagemBinding.inflate(LayoutInflater.from(context))
        var url: String?

        urlImagem?.let {
            binding.dialogFormularioImagem.tryLoadImage(it)
            binding.dialogFormularioEditText.setText(it)
        }

        binding.dialogFormularioButton.setOnClickListener {
            url = binding.dialogFormularioEditText.text.toString()
            binding.dialogFormularioImagem.tryLoadImage(url)
        }

        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("Confirmar") { _, _ ->
                url = binding.dialogFormularioEditText.text.toString()
                url?.let { carregarImagem(it) }
            }.setNegativeButton("Cancelar") { _, _ ->
            }.show()
    }

    fun alterarCategoria(carregarCategoria: (categoria: String) -> Unit) {

        val listaCategoria = arrayOf("Outros", "Carnes", "Saladas", "Massas", "Sobremesas","Frutos do Mar")

        MaterialAlertDialogBuilder(context)
            .setSingleChoiceItems(listaCategoria, 0) { _, indice ->
                carregarCategoria(listaCategoria[indice])
            }.setPositiveButton("Confirmar") { _, _ -> }
            .setNegativeButton("Cancelar") { _, _ -> }
            .show()
    }
}
