package unpsjb.ing.tntpm2024.basededatos.alimentos

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Alimento::class],
            version = 2,
            autoMigrations = [
                AutoMigration (from = 1, to = 2)
            ])
abstract class AlimentoRoomDatabase : RoomDatabase() {

    abstract fun alimentoDao(): AlimentoDAO

    companion object {

        @Volatile
        private var INSTANCIA: AlimentoRoomDatabase? = null

        fun obtenerDatabase(
            context: Context,
            viewModelScope: CoroutineScope
        ): AlimentoRoomDatabase {
            val instanciaTemporal = INSTANCIA
            if (instanciaTemporal != null) {
                return instanciaTemporal
            }
            synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AlimentoRoomDatabase::class.java,
                    "alimento_database"
                )
                    .addCallback(AlimentoDatabaseCallback(viewModelScope))
                    .build()
                INSTANCIA = instancia
                return instancia
            }
        }

        private class AlimentoDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCIA?.let { database ->
                    scope.launch {
                        cargarBaseDeDatos(database.alimentoDao())
                    }
                }
            }

            suspend fun cargarBaseDeDatos(alimentoDAO: AlimentoDAO) {

                if(alimentoDAO.cantidadDeAlimentos() == 0) {

                    alimentoDAO.insertar(
                        Alimento(
                            1,
                            "agua",
                            "liquido",
                            0.0
                        )
                    )

                    alimentoDAO.insertar(
                        Alimento(
                            2,
                            "yogur bebible",
                            "lacteo",
                            3.25
                        )
                    )

                    alimentoDAO.insertar(
                        Alimento(
                            3,
                            "avena",
                            "cereal",
                            4.9
                        )
                    )

                    alimentoDAO.insertar(
                        Alimento(
                            4,
                            "pollo",
                            "carne",
                            9.7
                        )
                    )
                }

            }

        }

    }
}