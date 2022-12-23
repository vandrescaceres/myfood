package com.myfood.mvp.addstoreplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myfood.R
import com.myfood.constants.Constant
import com.myfood.constants.Constant.Companion.MODE_ADD
import com.myfood.databases.databasesqlite.entity.StorePlace
import com.myfood.databinding.AddStorePlaceFragmentBinding
import com.myfood.mvp.storeplacelist.StorePlaceListFragment
import com.myfood.popup.Popup


class AddStorePlaceFragment(
    private val mode: Int,
    private val storePlaceToUpdate: StorePlace? = null
) : Fragment(),
    AddStorePlaceContract.View {

    //Declaracion variables globales
    private var _binding: AddStorePlaceFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var addStorePlacePresenter: AddStorePlacePresenter
    private var mutableTranslations: MutableMap<String, String> = mutableMapOf()

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Añadir Lugar Almacenaje
        _binding = AddStorePlaceFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutAddPlace.visibility = View.INVISIBLE

        //Creamos el presentador
        addStorePlacePresenter = AddStorePlacePresenter(requireContext())

        //Obtenemos las traducciones de pantalla
        this.mutableTranslations = addStorePlacePresenter.getTranslationsScreen()
        setTranslations()

        //Añadimos el titulo en la cabacera en caso de estar en modo
        //modificar
        if (mode == Constant.MODE_UPDATE) binding.etAddPlaceName.setText(
            storePlaceToUpdate!!.storePlace
        )

        //Inicializamos el boton Añadir
        initAddStorePlaceBtn()
    }

    private fun initAddStorePlaceBtn() {

        //Inicializamos la lógica al realizar clic en el botón de añadir.
        binding.btnAddPlaceProduct.setOnClickListener {

            //Obtenemos el valor del lugar de almacenaje del campo de texto
            val storePlace = binding.etAddPlaceName.text.toString()

            //Verificamos que el lugar de almacenaje no es nulo ni vacio
            if (storePlace.isNotEmpty()) {
                //Insertamos o actualizamos un lugar de almacenaje según el caso
                if (mode == MODE_ADD) {
                    addStorePlacePresenter.addStorePlace(storePlace)
                } else {
                    addStorePlacePresenter.updateStorePlace(
                        binding.etAddPlaceName.text.toString(),
                        storePlaceToUpdate!!.idStorePlace.toString()
                    )
                }
                //Volvemos en cualquiera de los dos casos a la pantalla Lugares de almacenaje
                loadFragment(StorePlaceListFragment())
            } else {
                //Si el nombre de unidad de almacenaje está vacia mostramos un mensaje al usuario
                //indicandoselo.
                Popup.showInfo(
                    requireContext(),
                    resources,
                    mutableTranslations[Constant.MSG_STORE_PLACE_REQUIRED]!!
                )
            }
        }
    }

    //Establece las traducciones de los textos
    override fun setTranslations() {
        binding.layoutAddPlace.visibility = View.VISIBLE
        binding.lAPlaceName.text =
            mutableTranslations[Constant.LABEL_STORE_PLACE]!!
        if (mode == MODE_ADD) {
            binding.header.titleHeader.text =
                mutableTranslations[Constant.TITLE_ADD_STORE_PLACE]!!
            binding.btnAddPlaceProduct.text =
                mutableTranslations[Constant.BTN_ADD_STORE_PLACE]!!
        } else {
            binding.header.titleHeader.text =
                mutableTranslations[Constant.TITLE_UPDATE_STORE_PLACE]!!
            binding.btnAddPlaceProduct.text =
                mutableTranslations[Constant.BTN_UPDATE_STORE_PLACE]!!
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