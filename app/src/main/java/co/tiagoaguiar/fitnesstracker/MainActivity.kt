package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MainActivity : AppCompatActivity() {
    private lateinit var rvMain: RecyclerView

    //private lateinit var btnImc: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1- o layout xml
        // 2- Aonde a recyclerView vai aparecer ( A tela main )
        // 3- Lógica - conectar o xml da celeula dentro da lista (Recycler) + qtd Elementos
        val mainAdapter = MainAdapter()
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = mainAdapter

        rvMain.layoutManager = LinearLayoutManager(this)

        // Precisamos de uma classe para administrar o recyclerView e suas células (os seus layouts de itens)
        //Adaper ->

//        btnImc = findViewById(R.id.btn_imc)

//        btnImc.setOnClickListener {
//            val i = Intent(this, ImcActivity::class.java)
//            startActivity(i)
//        }
    }

    private inner class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {
        //Qual é o Layout xml da célula especifica (Item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        // Vai ser disparado toda vez que houver uma rolagem da lista na tela, sendo necessário trocar o conteúdo
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        }


        // Informa quantas células essa Listagem terá.
        override fun getItemCount(): Int {
            return 15
        }

    }

    //está classe e a referência do xml em si
    private class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}