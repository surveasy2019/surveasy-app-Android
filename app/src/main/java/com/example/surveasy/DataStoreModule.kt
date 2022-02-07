package com.example.surveasy
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.*
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.catch
//import kotlinx.coroutines.flow.map
//import java.io.IOException
//
//
//class DataStoreModule(val context : Context) {
//    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")
//
//    private val stringKey = stringPreferencesKey("name") // string 저장 키값
//    private val intKey = longPreferencesKey("id") // int 저장 키값
//
//
//
//    suspend fun setStringVal(text : String){
//        context.dataStore.edit { preferences ->
//            preferences[stringKey] = text
//        }
//    }
//
//    val long : Flow<Long> = context.dataStore.data
//        .catch { exception ->
//            if(exception is IOException){
//                emit(emptyPreferences())
//            }else{
//                throw exception
//            }
//        }.map { preferences ->
//            return@map preferences[intKey]?:0
//        }
//
//    suspend fun setIntVal(intVal : Long?){
//        context.dataStore.edit { preferences ->
//            if(intVal==null){
//                preferences[intKey] = 0
//            }else{
//                preferences[intKey] = intVal
//            }
//
//
//
//        }
//    }
//}