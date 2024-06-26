package unpsjb.ing.tntpm2024.basededatos.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_alimento")
data class Alimento(
    @PrimaryKey(autoGenerate = true)
    var alimentoId: Int = 0,

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "categoria")
    var categoria: String,

    @ColumnInfo(name = "medida")
    var medida: String,

    @ColumnInfo(name = "kcal_totales")
    var kcal: Double,

    @ColumnInfo(name = "carbohidratos")
    var carbohidratos: Double,

    @ColumnInfo(name = "proteinas")
    var proteinas: Double,

    @ColumnInfo(name = "grasas")
    var grasas: Double,

    @ColumnInfo(name = "alcohol")
    var alcohol: Double,

    @ColumnInfo(name = "coresterol")
    var coresterol: Double,

    @ColumnInfo(name = "fibra")
    var fibra: Double,
) {
    constructor() : this(0, "", "", "", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
}
