package com.myfood.mvp.addquantityunit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myfood.R
import com.myfood.constants.Constant.Companion.MODE_ADD
import com.myfood.constants.Constant.Companion.MODE_UPDATE
import com.myfood.databases.databasesqlite.entity.QuantityUnit
import com.myfood.databinding.AddQuantityUnitFragmentBinding
import com.myfood.mvp.quantityunitlist.QuantityUnitListFragment
import com.myfood.popup.Popup


class AddQuantityUnitFragment(
    private val mode: Int,
    private val quantityUnitToUpdate: QuantityUnit? = null
) : Fragment(),
    AddQuantityUnitContract.View {

    //Declaración de variables globales
    private var _binding: AddQuantityUnitFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var addQuantityUnitModel: AddQuantityUnitModel
    private lateinit var addQuantityUnitPresenter: AddQuantityUnitPresenter
    private var mutableTranslations: MutableMap<String, String> = mutableMapOf()

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Añadir Unidad de Cantidad
        _binding = AddQuantityUnitFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutAddQuantityUnit.visibility = View.INVISIBLE

        //Creamos el modelo
        addQuantityUnitModel = AddQuantityUnitModel()

        //Creamos el presentador
        addQuantityUnitPresenter = AddQuantityUnitPresenter(requireContext())

        //Obtenemos las traducciones de pantalla
        this.mutableTranslations = addQuantityUnitPresenter.getTranslationsScreen()
        setTranslations()

        //Añadimos el titulo en la cabacera en caso de estar en modo
        //modificar
        if (mode == MODE_UPDATE) binding.etAddQuantityUnitName.setText(quantityUnitToUpdate!!.quantityUnit)

        //Inicializamos el boton Añadir
        initAddQuantityUnitBtn()
    }

    private fun initAddQuantityUnitBtn() {

        //Inicializamos la lógica al realizar clic en el botón de añadir.
        binding.btnAddQuantityUnit.setOnClickListener {

            //Obtenemos el valor de la cantidad del campo de texto
            val quantityUnit = binding.etAddQuantityUnitName.text.toString()

            //Verificamos que la cantidad no es nula ni vacia
            if (quantityUnit.isNotEmpty()) {

                //Insertamos o actualizamos una unidad de cantidad según el caso
                if (mode == MODE_ADD) {
                    addQuantityUnitPresenter.addQuantityUnit(quantityUnit)
                } else {
                    addQuantityUnitPresenter.updateQuantityUnit(
                        quantityUnit,
                        quantityUnitToUpdate!!.idQuantityUnit.toString()
                    )
                }
                //Volvemos en cualquiera de los dos casos a la pantalla Unidades de Cantidad
                loadFragment(QuantityUnitListFragment())
            } else {
                //Si el nombre de la cantidad está vacia mostramos un mensaje al usuario
                //indicandoselo.
                Popup.showInfo(
                    requireContext(),
                    resources,
                    mutableTranslations[com.myfood.constants.Constant.MSG_QUANTIY_UNIT_REQUIRED]!!
                )
            }
        }
    }

    //Establece las traducciones de los textos
    override fun setTranslations() {
        binding.layoutAddQuantityUnit.visibility = View.VISIBLE
        binding.lAddQuantityUnitName.text =
            mutableTranslations[com.myfood.constants.Constant.LABEL_QUANTITY_UNIT]!!
        if (mode == MODE_ADD) {
            binding.header.titleHeader.text =
                mutableTranslations[com.myfood.constants.Constant.TITLE_ADD_QUANTITY_UNIT]!!
            binding.btnAddQuantityUnit.text =
                mutableTranslations[com.myfood.constants.Constant.BTN_ADD_QUANTITY_UNIT]!!
        } else {
            binding.header.titleHeader.text =
                mutableTranslations[com.myfood.constants.Constant.TITLE_UPDATE_QUANTITY_UNIT]!!
            binding.btnAddQuantityUnit.text =
                mutableTranslations[com.myfood.constants.Constant.BTN_UPDATE_QUANTITY_UNIT]!!
        }
    }

    //Metodo que nos permite navegar a otro Fragment o pantalla
    fun loadFragment(fragment: Fragment) {

        //Declaramos una transacción
        //Añadimos el fragment a la pila backStack (sirve para cuando
        //hacemos clic en el back button del movil)
        //Comiteamos
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}