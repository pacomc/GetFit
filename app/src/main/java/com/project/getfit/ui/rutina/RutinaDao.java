package com.project.getfit.ui.rutina;


import androidx.room.*;

import java.util.List;


@Dao
public interface RutinaDao {
    @Query("SELECT * FROM rutina")
    List<Rutina> getAll();

    @Query("SELECT * FROM rutina WHERE uid IN (:userIds)")
    List<Rutina> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM rutina WHERE nombre_rutina LIKE :nombreRutina LIMIT 1")
    Rutina findByName(String nombreRutina);

    @Query("SELECT ejercicios_rutina FROM rutina WHERE nombre_rutina LIKE :nombreRutina LIMIT 1")
    Rutina getEjerciciosRutinaByName(String nombreRutina);

    @Query("SELECT ejercicios_rutina FROM rutina WHERE uid LIKE :idRutina")
    Rutina getEjerciciosRutinaById(int idRutina);

    @Insert
    void insertAll(Rutina... rutinas);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Rutina rutina);


    @Delete
    void delete(Rutina rutina);
}
