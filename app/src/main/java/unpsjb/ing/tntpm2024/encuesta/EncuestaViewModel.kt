package unpsjb.ing.tntpm2024.encuesta

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import unpsjb.ing.tntpm2024.basededatos.EncuestasDatabase
import unpsjb.ing.tntpm2024.basededatos.Repository
import unpsjb.ing.tntpm2024.basededatos.alimentos.Alimento
import unpsjb.ing.tntpm2024.basededatos.encuestas.Encuesta

class EncuestaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : Repository
    private val allEncuestas: LiveData<List<Encuesta>>
    private val allAlimentos: LiveData<List<Alimento>>

    init {

        val dao = EncuestasDatabase.getInstance(application).encuestaDAO

        repository = Repository(dao)
        allEncuestas = repository.allEncuestas
        allAlimentos = repository.allAlimentos

    }

    fun insert(encuesta: Encuesta) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertarEncuesta(encuesta)
    }

    fun getAllEncuestas():LiveData<List<Encuesta>> {
        return this.allEncuestas
    }

    fun getEncuesta(searchQuery: String): LiveData<List<Encuesta>> {
        return repository.getEncuesta(searchQuery).asLiveData()
    }

    fun getAllAlimentos():LiveData<List<Alimento>> {
        return this.allAlimentos
    }

    fun getAlimentoByName(nombre: String): Alimento {
        return repository.getAlimentoByNombre(nombre)
    }

    fun editEncuesta(
        encuestaId: Int,
        alimento: Alimento?,
        porcion: String?,
        frecuencia: String?,
        veces: String?,
        encuestaCompletada: Boolean
    ) = viewModelScope.launch(Dispatchers.IO) {
    }


    private var _alimento = MutableLiveData<Alimento>()
    val alimento: LiveData<Alimento>
        get() = _alimento

    private var _porcion = MutableLiveData<String>("")
    val porcion: LiveData<String>
        get() = _porcion

    private var _frecuencia = MutableLiveData<String>("")
    val frecuencia: LiveData<String>
        get() = _frecuencia

    private var _veces = MutableLiveData<Int>(0)
    val veces: LiveData<Int>
        get() = _veces

    private var _encuestaCompletada = MutableLiveData<Boolean>(false)
    val encuestaCompletada: LiveData<Boolean>
        get() = _encuestaCompletada

    fun encuestaCompletada() {
        _encuestaCompletada.value = true
    }

}