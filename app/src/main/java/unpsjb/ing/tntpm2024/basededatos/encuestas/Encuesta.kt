package unpsjb.ing.tntpm2024.basededatos.encuestas

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import unpsjb.ing.tntpm2024.basededatos.alimentos.Alimento

@Entity(tableName = "tabla_encuesta")
data class Encuesta(
    @PrimaryKey(autoGenerate = true)
    var encuestaId: Int = 0,
    @Embedded
    var alimento: Alimento,
    @ColumnInfo(name = "porcion")
    var porcion: String,
    @ColumnInfo(name = "frecuencia")
    var frecuencia:String,
    @ColumnInfo(name = "veces")
    var veces: String,
    @ColumnInfo(name = "encuestaCompletada")
    var encuestaCompletada: Boolean

)