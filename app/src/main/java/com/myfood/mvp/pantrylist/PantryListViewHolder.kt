package com.myfood.mvp.pantrylist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.myfood.databinding.ElementPantryListBinding


class PantryListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    //Obtenemos el binding de la vista del item
    val binding = ElementPantryListBinding.bind(view)

    //LLenamos los datos del item
    fun render(
        pantryListModel: PantryList,
        onClickListener: (PantryList) -> Unit,
        onCLickDelete: (Int, PantryList) -> Unit,
        onClickUpdate: (PantryList) -> Unit,
        currency: String
    ) {
        binding.tvPPName.text = pantryListModel.name
        binding.tvPPQuantity.text = pantryListModel.quantity
        binding.tvPPQuantityUnit.text = pantryListModel.quantityUnit
        binding.tvPPPrice.text = pantryListModel.price
        binding.tvPPCurrency.text = currency

        //Inicializamos los eventos del item de la lista
        binding.itemViewPL.setOnClickListener { onClickListener(pantryListModel) }
        binding.btnDeletePP.setOnClickListener { onCLickDelete(adapterPosition, pantryListModel) }
        binding.btnUpdatePP.setOnClickListener { onClickUpdate(pantryListModel) }
    }
}