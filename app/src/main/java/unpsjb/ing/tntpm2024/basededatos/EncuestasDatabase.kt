package unpsjb.ing.tntpm2024.basededatos

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import unpsjb.ing.tntpm2024.basededatos.alimentos.Alimento
import unpsjb.ing.tntpm2024.basededatos.encuestas.Encuesta

@Database(
    entities = [
        Encuesta::class,
        Alimento::class
    ],
    version = 1
)
abstract class EncuestasDatabase: RoomDatabase() {

    abstract val encuestaDAO: EncuestaDAO

    companion object {
        @Volatile
        private var INSTANCE: EncuestasDatabase? = null

        fun getInstance(context: Context): EncuestasDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    EncuestasDatabase::class.java,
                    "encuestas_db"
                ).build().also {
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
                    cargarDatabase(database.encuestaDAO)
                }
            }
        }

        suspend fun cargarDatabase(encuestaDAO: EncuestaDAO) {
            Log.i("EncuestasDatabase", "cargarDatabase")

            if(encuestaDAO.getCantidadAlimentos() == 0) {
                encuestaDAO.insertAlimento(
                    Alimento(
                        1,
                        "Yogur bebible",
                        "liquido",
                        0.35
                    )
                )
            }
        }

    }

}