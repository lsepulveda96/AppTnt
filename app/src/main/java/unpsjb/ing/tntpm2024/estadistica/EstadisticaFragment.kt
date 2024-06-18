package unpsjb.ing.tntpm2024.estadistica

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import unpsjb.ing.tntpm2024.listado.EncuestaListAdapter
import unpsjb.ing.tntpm2024.R
import unpsjb.ing.tntpm2024.basededatos.entidades.Encuesta
import unpsjb.ing.tntpm2024.basededatos.entidades.EstadisticasConsumo
import unpsjb.ing.tntpm2024.databinding.FragmentEstadisticaBinding
import unpsjb.ing.tntpm2024.encuesta.EncuestaViewModel


class EstadisticaFragment : Fragment() {

    val TAG = "EstadisticaFragment"
    private lateinit var binding: FragmentEstadisticaBinding
    private val viewModel: EstadisticaViewModel by viewModels()

    private lateinit var encuestaViewModel: EncuestaViewModel
    private val adapterList : EncuestaListAdapter by lazy { EncuestaListAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_estadistica, container, false
        )

        binding.estadisticaViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        encuestaViewModel = ViewModelProvider(this)[EncuestaViewModel::class.java]

        encuestaViewModel.todasLasEncuestas
            .observe(
                viewLifecycleOwner
            ) { encuestas ->
                encuestas?.let {
                    adapterList.setEncuestas(it)
                }
            }

        val items = arrayOf("Kilocalorías",
                            "Carbohidratos",
                            "Proteínas",
                            "Colesterol",
                            "Fibras")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinnerItems.adapter = adapter

        binding.spinnerItems.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = items[position]
                encuestaViewModel.estadisticasConsumo.observe(viewLifecycleOwner) { estadisticas ->
                    estadisticas?.let {
                        val (promedio, descripcion) = getPromedioYDescripcion(it, selectedItem)
                        binding.textviewPromedio.text = promedio.toString()
                        binding.textviewDescripcion.text = descripcion
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada
            }
        }

        return binding.root
    }

    private fun getPromedioYDescripcion(estadisticas: EstadisticasConsumo, item: String): Pair<Float, String> {
        return when (item) {
            "Kilocalorías" -> estadisticas.avgKcalTotales to "Consumo promedio de KILOCALORÍAS"
            "Carbohidratos" -> estadisticas.avgCarbohidratos to "Consumo promedio de CARBOHIDRATOS (gr)"
            "Proteínas" -> estadisticas.avgProteinas to "Consumo promedio de PROTEÍNAS (gr)"
            "Colesterol" -> estadisticas.avgColesterol to "Consumo promedio de COLESTEROL (mg)"
            "Fibras" -> estadisticas.avgFibras to "Consumo promedio de FIBRAS (gr)"
            else -> 0f to "Información no disponible."
        }
    }


}