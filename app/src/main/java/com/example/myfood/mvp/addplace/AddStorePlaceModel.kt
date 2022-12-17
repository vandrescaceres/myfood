package com.example.myfood.mvp.addplace

import android.content.Context
import com.example.myfood.databases.MyFoodRepository
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.enum.ScreenType

class AddStorePlaceModel : AddStorePlaceContract.Model {
    private val myFoodRepository = MyFoodRepository()

    override fun getInstance(context: Context) {
        myFoodRepository.getInstance(context)
    }

    override fun addStorePlace(storePlace: String) {
        myFoodRepository.addStorePlace(storePlace)
    }

    override fun updateStorePlace(storePlace: String, id: String) {
        myFoodRepository.updateStorePlace(storePlace, id)
    }

    override fun getCurrentLanguage(): String {
        return myFoodRepository.getCurrentLanguage()
    }

    override fun getTranslations(language: Int): List<Translation> {
        return myFoodRepository.getTranslations(language, ScreenType.CONFIG.int)
    }
}