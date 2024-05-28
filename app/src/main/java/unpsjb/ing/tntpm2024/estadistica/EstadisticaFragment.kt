package unpsjb.ing.tntpm2024.estadistica

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.databinding.DataBindingUtil
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import unpsjb.ing.tntpm2024.R
import unpsjb.ing.tntpm2024.databinding.FragmentEstadisticaBinding


class EstadisticaFragment : Fragment() {

    private lateinit var binding: FragmentEstadisticaBinding
    private val viewModel: EstadisticaViewModel by viewModels()

    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_estadistica, container, false
        )

        binding.estadisticaViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        pieChart = binding.pieChart
        setupPieChart()
        loadPieChartData()

        return binding.root
    }

    private fun setupPieChart() {
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.White.toArgb())
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.Black.toArgb())
        pieChart.centerText = "Consumo de Grasas"
        pieChart.setCenterTextSize(24f)
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = true
    }

    private fun loadPieChartData() {
        // Ejemplo de datos de consumo de grasas (en gramos)
        val consumoDiario = 20f
        val consumoSemanal = 140f
        val consumoMensual = 600f

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(consumoDiario, "Diario"))
        entries.add(PieEntry(consumoSemanal, "Semanal"))
        entries.add(PieEntry(consumoMensual, "Mensual"))

        val colors = ArrayList<Int>()
        colors.add(Color.Red.toArgb())
        colors.add(Color.Blue.toArgb())
        colors.add(Color.Green.toArgb())

        val dataSet = PieDataSet(entries, "Consumo de Grasas")
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.Black.toArgb())

        pieChart.data = data
        pieChart.invalidate()
    }

}