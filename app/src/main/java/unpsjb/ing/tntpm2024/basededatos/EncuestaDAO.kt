package unpsjb.ing.tntpm2024.basededatos

import android.adservices.adid.AdId
import androidx.lifecycle.LiveData
import androidx.room.*
import unpsjb.ing.tntpm2024.basededatos.encuestas.Encuesta
import unpsjb.ing.tntpm2024.basededatos.alimentos.Alimento
import unpsjb.ing.tntpm2024.basededatos.relaciones.AlimentoEncuesta

@Dao
interface EncuestaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEncuesta(encuesta: Encuesta)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlimento(alimento: Alimento)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlimentoEncuesta(alimentoEncuesta: AlimentoEncuesta)

    @Transaction
    @Query("SELECT * FROM tabla_encuesta")
    fun getEncuestas(): LiveData<List<Encuesta>>

    @Transaction
    @Query("SELECT COUNT(encuestaId) from tabla_encuesta")
    fun getCantidadEncuestas(): Int

    @Transaction
    @Query("DELETE FROM tabla_encuesta")
    fun borrarEncuestas()

    @Transaction
    @Query("SELECT * FROM tabla_alimento")
    fun getAlimentos(): LiveData<List<Alimento>>

    @Transaction
    @Query("SELECT * FROM tabla_alimento WHERE nombre = :nombre")
    fun getAlimentoByName(nombre: String): Alimento

    @Transaction
    @Query("SELECT COUNT(alimentoId) from tabla_alimento")
    fun getCantidadAlimentos(): Int

    @Transaction
    @Query("SELECT * FROM tabla_encuesta WHERE alimentoId = :alimentoId")
    fun getEncuestasByAlimentos(alimentoId: Int): LiveData<List<AlimentoEncuesta>>

}