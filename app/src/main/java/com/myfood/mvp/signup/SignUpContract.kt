package com.myfood.mvp.signup

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.OneValueEntity
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Registro
interface SignUpContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun onInsertedUser(result: OneValueEntity)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun insertUser(
            name: String,
            surnames: String,
            email: String,
            password: String
        )
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun createInstances(context: Context)
        fun insertUser(
            name: String,
            surnames: String,
            email: String,
            password: String,
        ): MutableLiveData<OneValueEntity>
    }
}