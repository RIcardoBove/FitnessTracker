package co.tiagoaguiar.fitnesstracker.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import co.tiagoaguiar.fitnesstracker.R

class TbmActivity : AppCompatActivity() {

    private lateinit var lifeStyle: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tbm)

        lifeStyle = findViewById(R.id.auto_lifestyle)
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        lifeStyle.setText(items.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lifeStyle.setAdapter(adapter)

    }
}