package unpsjb.ing.tntpm2024.basededatos.relaciones

import androidx.room.Embedded
import androidx.room.Relation
import unpsjb.ing.tntpm2024.basededatos.alimentos.Alimento
import unpsjb.ing.tntpm2024.basededatos.encuestas.Encuesta

data class AlimentoEncuesta(
    @Embedded
    val alimento: Alimento,
    @Relation(
        parentColumn = "alimentoId",
        entityColumn = "alimentoId"
    )
    val encuesta: Encuesta
)
