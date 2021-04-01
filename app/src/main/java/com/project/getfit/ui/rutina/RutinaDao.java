package com.project.getfit.ui.rutina;


import androidx.room.*;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface RutinaDao {
    @Query("SELECT * FROM rutina")
    List<Rutina> obtenerTodas();

    @Query("SELECT * FROM rutina WHERE uid = :userIds")
    Rutina obtenerPorId(int userIds);

    @Query("SELECT * FROM rutina WHERE nombre_rutina LIKE :nombreRutina")
    List<Rutina> obtenerPorNombre(String nombreRutina);

    @Query("UPDATE rutina SET nombre_rutina = :nombreActualizado WHERE uid = :idRutina")
    void actualizarRutina(int idRutina, String nombreActualizado);


    @Insert
    void insertarRutinas(Rutina... rutinas);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertarRutina(Rutina rutina);

    @Delete
    void borrarRutina(Rutina rutina);
}
