package co.tiagoaguiar.fitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.model.App
import co.tiagoaguiar.fitnesstracker.model.Calc

class ListCalcActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        lateinit var response: List<Calc?>

        val rvListCal: RecyclerView = findViewById(R.id.rv_list_calc)
        rvListCal.layoutManager = LinearLayoutManager(this)

        val type =
            intent?.extras?.getString("type") ?: throw IllegalStateException("type not found!")

        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            response = dao.getRegisterByType(type)

            runOnUiThread {
                val adapter = ListCalcAdapter(response)
                rvListCal.adapter = adapter
            }

        }.start()

    }

    private inner class ListCalcAdapter(private val list: List<Calc?>) :
        RecyclerView.Adapter<ListCalcViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
            val view = layoutInflater.inflate(R.layout.list_calc_rv, parent, false)
            return ListCalcViewHolder(view)
        }

        override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
            val itemCurrent = list
            itemCurrent[position]?.let { holder.bind(it) }
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    private inner class ListCalcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Calc) {
            val textCalc = itemView.findViewById<TextView>(R.id.res_imc)

            textCalc.setText("IMC: ${item.res.toString()}")

        }

    }

}

