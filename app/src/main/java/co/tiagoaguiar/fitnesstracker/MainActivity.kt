package co.tiagoaguiar.fitnesstracker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvMain: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listItem = mutableListOf<MainItem>()
        listItem.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.baseline_wb_sunny_24,
                textStringId = R.string.imc,
                color = Color.GREEN
            )
        )
        listItem.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.baseline_visibility_24,
                textStringId = R.string.tmb,
                color = Color.BLUE
            )

        )


        // 1- o layout xml
        // 2- Aonde a recyclerView vai aparecer ( A tela main )
        // 3- Lógica - conectar o xml da celeula dentro da lista (Recycler) + qtd Elementos
        val mainAdapter = MainAdapter(listItem)
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = mainAdapter

        rvMain.layoutManager = GridLayoutManager(this, 2)

        // Precisamos de uma classe para administrar o recyclerView e suas células (os seus layouts de itens)
        //Adaper ->

//        btnImc = findViewById(R.id.btn_imc)

//        btnImc.setOnClickListener {
//            val i = Intent(this, ImcActivity::class.java)
//            startActivity(i)
//        }
    }

    private inner class MainAdapter(private val listItem: MutableList<MainItem>) :
        RecyclerView.Adapter<MainViewHolder>() {


        //Qual é o Layout xml da célula especifica (Item)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        // Vai ser disparado toda vez que houver uma rolagem da lista na tela, sendo necessário trocar o conteúdo
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = listItem[position]
            holder.bind(itemCurrent)
        }


        // Informa quantas células essa Listagem terá.
        override fun getItemCount(): Int {
            return listItem.size
        }

    }

    //está classe e a referência do xml em si
    private class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MainItem) {
            val img: ImageView = itemView.findViewById(R.id.item_img_icon)
            val name: TextView = itemView.findViewById(R.id.item_txt_name)
            val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

            img.setImageResource(item.drawableId)
            name.setText(item.textStringId)
            container.setBackgroundColor(item.color)
        }

    }
}