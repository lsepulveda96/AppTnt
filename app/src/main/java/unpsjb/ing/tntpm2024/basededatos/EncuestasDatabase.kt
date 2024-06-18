package unpsjb.ing.tntpm2024.basededatos

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import unpsjb.ing.tntpm2024.basededatos.entidades.Alimento
import unpsjb.ing.tntpm2024.basededatos.entidades.AlimentoEncuesta
import unpsjb.ing.tntpm2024.basededatos.entidades.Encuesta

@Database(
    version = 1,
    entities = [
        Encuesta::class,
        Alimento::class,
        AlimentoEncuesta::class
    ],
    exportSchema = true
)
abstract class EncuestasDatabase : RoomDatabase() {

    abstract val encuestaDAO: EncuestaDAO

    abstract fun alimentoDao(): AlimentoDAO

    abstract fun alimentoEncuestaDao(): AlimentoEncuestaDao

    companion object {
        @Volatile
        private var INSTANCE: EncuestasDatabase? = null

        fun getInstance(context: Context): EncuestasDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    EncuestasDatabase::class.java,
                    "encuestas_db"
                )
                    .addCallback(EncuestasDatabaseCallback(CoroutineScope(Dispatchers.IO)))
                    .fallbackToDestructiveMigration()
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }

    private class EncuestasDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.alimentoDao())
                }
            }
        }

        suspend fun populateDatabase(alimentoDao: AlimentoDAO) {

            if (alimentoDao.getCantidadAlimentos() == 0) {
                val alimentos = listOf(
                    Alimento(
                        nombre = "Manzana",
                        categoria = "Fruta",
                        medida = "Unidad",
                        porcentajeGraso = 0.2,
                        kcalTotales = 58.2,
                        carbohidratos = 13.8,
                        proteinas = 0.3,
                        alcohol = 0.0,
                        colesterol = 0.0,
                        fibra = 2.4
                    ),
                    Alimento(
                        nombre = "Leche",
                        categoria = "Lácteo",
                        medida = "Litro",
                        porcentajeGraso = 3.0,
                        kcalTotales = 57.9,
                        carbohidratos = 4.6,
                        proteinas = 3.1,
                        alcohol = 0.0,
                        colesterol = 10.1,
                        fibra = 0.0
                    ),
                    Alimento(
                        nombre = "Pan",
                        categoria = "Cereal",
                        medida = "Gramo",
                        porcentajeGraso = 1.3,
                        kcalTotales = 210.0,
                        carbohidratos = 52.2,
                        proteinas = 7.5,
                        alcohol = 0.0,
                        colesterol = 0.0,
                        fibra = 5.3
                    ),
                    Alimento(
                        nombre = "Carne picada",
                        categoria = "Carne",
                        medida = "Gramo",
                        porcentajeGraso = 9.4,
                        kcalTotales = 165.1,
                        carbohidratos = 0.0,
                        proteinas = 20.2,
                        alcohol = 0.0,
                        colesterol = 65.3,
                        fibra = 0.0
                    )
                )
                alimentoDao.insertAll(alimentos)
            }
        }

    }
}
