package com.project.getfit.ui.rutina;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.getfit.ui.ejercicios.Ejercicio;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter // note this annotation
    public String fromOptionValuesList(ArrayList<Ejercicio> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Ejercicio>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public ArrayList<Ejercicio> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Ejercicio>>() {
        }.getType();
        ArrayList<Ejercicio> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
