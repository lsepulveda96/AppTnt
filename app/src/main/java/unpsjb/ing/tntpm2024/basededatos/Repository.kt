package unpsjb.ing.tntpm2024.basededatos

import androidx.lifecycle.LiveData
import unpsjb.ing.tntpm2024.basededatos.EncuestaDAO
import unpsjb.ing.tntpm2024.basededatos.alimentos.Alimento
import unpsjb.ing.tntpm2024.basededatos.encuestas.Encuesta
import unpsjb.ing.tntpm2024.basededatos.relaciones.AlimentoEncuesta

class Repository(private val encuestaDAO: EncuestaDAO) {

    val allEncuestas: LiveData<List<Encuesta>> = encuestaDAO.getEncuestas()

    val allAlimentos: LiveData<List<Alimento>> = encuestaDAO.getAlimentos()

    suspend fun insertarEncuesta(encuesta: Encuesta) {
        encuestaDAO.insertEncuesta(encuesta)
    }

    fun getCantidadEncuestas(): Int {
        return encuestaDAO.getCantidadEncuestas()
    }

    fun borrarEncuestas() {
        encuestaDAO.borrarEncuestas()
    }

    suspend fun insertarAlimento(alimento: Alimento) {
        encuestaDAO.insertAlimento(alimento)
    }

    fun getAlimentoByNombre(nombre: String): Alimento {
        return encuestaDAO.getAlimentoByName(nombre)
    }

    fun getEncuestasByAlimentos(alimentoId: Int): LiveData<List<AlimentoEncuesta>> {
        return encuestaDAO.getEncuestasByAlimentos(alimentoId)
    }


    fun getCantidadAlimentos(): Int {
        return encuestaDAO.getCantidadAlimentos()
    }

    suspend fun insertarAlimentoEncuesta(alimentoEncuesta: AlimentoEncuesta) {
        encuestaDAO.insertAlimentoEncuesta(alimentoEncuesta)
    }

}