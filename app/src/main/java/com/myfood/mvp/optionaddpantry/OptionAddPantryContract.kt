package com.myfood.mvp.optionaddpantry

import android.content.Context
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// de opción entre Escanear directo o ir a Pantalla Añadir producto despensa
interface OptionAddPantryContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {

        //Metodo que crea las instancias de las bases de datos
        fun createInstances(application: Context)
    }
}