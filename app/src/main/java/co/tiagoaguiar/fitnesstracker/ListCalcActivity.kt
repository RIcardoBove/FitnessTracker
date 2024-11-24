package co.tiagoaguiar.fitnesstracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.model.App
import co.tiagoaguiar.fitnesstracker.model.Calc
import co.tiagoaguiar.fitnesstracker.model.TbmActivity
import java.text.SimpleDateFormat
import java.util.Locale

class ListCalcActivity : AppCompatActivity(), OnListClickListener {

    private lateinit var adapter: ListCalcAdapter
    private lateinit var result: MutableList<Calc>

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        result = mutableListOf()

        adapter = ListCalcAdapter(result, this)

        rv = findViewById(R.id.rv_list_calc)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val type =
            intent?.extras?.getString("type") ?: throw IllegalStateException("type not found!")

        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            runOnUiThread {
                result.addAll(response)
                adapter.notifyDataSetChanged()
            }

        }.start()

    }

    override fun onClick(id: Int, type: String) {
        when (type) {
            "imc" -> {
                val intent = Intent(this, ImcActivity::class.java)
                intent.putExtra("updateId", id)
                startActivity(intent)
            }

            "tbm" -> {
                val intent = Intent(this, TbmActivity::class.java)
                intent.putExtra("updateId", id)
                startActivity(intent)
            }
        }
        finish()
    }

    override fun onLongClick(position: Int, calc: Calc) {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.delete_message))
            .setNegativeButton(android.R.string.cancel) {_, _ ->

            }
            .setPositiveButton(android.R.string.ok) {_, _ ->
                    Thread{
                        val app = application as App
                        val dao = app.db.calcDao()
                        val response = dao.delete(calc)

                        if (response > 0){
                            runOnUiThread {
                                result.removeAt(position)
                                adapter.notifyItemRemoved(position)
                            }
                        }
                    }.start()
            }.create()
            .show()

    }

    private inner class ListCalcAdapter(
        private val list: List<Calc?>,
        private val listener: OnListClickListener
    ) :
        RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>() {
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

        private inner class ListCalcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Calc) {
                val textCalc = itemView.findViewById<TextView>(R.id.res_imc)

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
                val data = sdf.format(item.createdDate)
                val res = item.res

                textCalc.text = getString(R.string.list_response, res, data)

                textCalc.setOnLongClickListener {
                    listener.onLongClick(adapterPosition, item)
                    true
                }

                textCalc.setOnClickListener {
                    listener.onClick(item.id, item.type)
                }

            }
        }
    }


}




