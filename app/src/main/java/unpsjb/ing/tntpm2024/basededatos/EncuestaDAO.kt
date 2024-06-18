package unpsjb.ing.tntpm2024.basededatos

import androidx.lifecycle.LiveData
import androidx.room.*
import unpsjb.ing.tntpm2024.basededatos.entidades.Encuesta
import unpsjb.ing.tntpm2024.basededatos.entidades.EstadisticasConsumo
import unpsjb.ing.tntpm2024.detalle.AlimentoEncuestaDetalles

@Dao
interface EncuestaDAO {

//TRANSACCIONES DE ENCUESTAS

    @Query("SELECT * from tabla_encuesta")
    fun getEncuestas(): LiveData<List<Encuesta>>

    @Query("SELECT * FROM tabla_encuesta encuestas " +
            "INNER JOIN tabla_alimento_encuesta ae ON encuestas.encuestaId = ae.encuestaId " +
            "INNER JOIN tabla_alimento alimentos ON alimentos.alimentoId = ae.alimentoId " +
            "WHERE alimentos.nombre LIKE :searchQuery")
    fun getEncuesta(searchQuery: String): LiveData<List<Encuesta>>

    @Query("""
        SELECT 
            AVG(tabla_alimento_encuesta.porcion * tabla_alimento_encuesta.veces * tabla_alimento.porcentaje_graso) AS avgGrasasTotales,
            AVG(tabla_alimento_encuesta.porcion * tabla_alimento_encuesta.veces * tabla_alimento.kcal_totales) AS avgKcalTotales,
            AVG(tabla_alimento_encuesta.porcion * tabla_alimento_encuesta.veces * tabla_alimento.carbohidratos) AS avgCarbohidratos,
            AVG(tabla_alimento_encuesta.porcion * tabla_alimento_encuesta.veces * tabla_alimento.proteinas) AS avgProteinas,
            AVG(tabla_alimento_encuesta.porcion * tabla_alimento_encuesta.veces * tabla_alimento.alcohol) AS avgAlcohol,
            AVG(tabla_alimento_encuesta.porcion * tabla_alimento_encuesta.veces * tabla_alimento.colesterol) AS avgColesterol,
            AVG(tabla_alimento_encuesta.porcion * tabla_alimento_encuesta.veces * tabla_alimento.fibra) AS avgFibra
        FROM tabla_alimento_encuesta
        INNER JOIN tabla_alimento ON tabla_alimento.alimentoId = tabla_alimento_encuesta.alimentoId
        INNER JOIN tabla_encuesta ON tabla_encuesta.encuestaId = tabla_alimento_encuesta.encuestaId 
        WHERE tabla_encuesta.encuestaCompletada = 'True'
    """)
    fun getPromediosEncuestas(): LiveData<EstadisticasConsumo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEncuesta(encuesta: Encuesta)

    @Insert
    suspend fun insert(encuesta: Encuesta): Long

    @Delete
    fun deleteEncuesta(encuesta: Encuesta)

    @Update
    fun editEncuesta(encuesta: Encuesta)

//    @Query("SELECT * FROM tabla_alimento_encuesta WHERE encuestaId = :encuestaId")
//    fun getAlimentosByEncuestaId(encuestaId: Int): LiveData<List<AlimentoEncuesta>>

    @Transaction
    @Query("""
        SELECT tabla_alimento_encuesta.encuestaId, tabla_alimento.alimentoId, tabla_alimento.nombre, tabla_alimento.categoria, tabla_alimento.medida, tabla_alimento.porcentaje_graso,
               tabla_alimento_encuesta.porcion, tabla_alimento_encuesta.frecuencia, tabla_alimento_encuesta.veces
        FROM tabla_alimento_encuesta
        INNER JOIN tabla_alimento ON tabla_alimento.alimentoId = tabla_alimento_encuesta.alimentoId
        WHERE tabla_alimento_encuesta.encuestaId = :encuestaId
    """)
    fun getAlimentosByEncuestaId(encuestaId: Int): LiveData<List<AlimentoEncuestaDetalles>>
}