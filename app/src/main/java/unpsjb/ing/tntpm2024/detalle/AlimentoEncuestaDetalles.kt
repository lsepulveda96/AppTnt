package unpsjb.ing.tntpm2024.detalle

data class AlimentoEncuestaDetalles(
    val encuestaId: Int,
    val alimentoId: Int,
    val nombre: String,
    val categoria: String,
    val medida: String,
    val porcentaje_graso: String,
    val porcion: String,
    val frecuencia: String,
    val veces: String
)