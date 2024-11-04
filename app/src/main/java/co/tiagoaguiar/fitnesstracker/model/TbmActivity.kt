package co.tiagoaguiar.fitnesstracker.model

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import co.tiagoaguiar.fitnesstracker.ListCalcActivity
import co.tiagoaguiar.fitnesstracker.R

class TbmActivity : AppCompatActivity() {

    private lateinit var lifeStyle: AutoCompleteTextView

    private lateinit var editTbmWeight: EditText
    private lateinit var editTbmHeight: EditText
    private lateinit var editTbmAge: EditText
    private lateinit var buttonTbmSend: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tbm)

        lifeStyle = findViewById(R.id.auto_lifestyle)
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        lifeStyle.setText(items.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lifeStyle.setAdapter(adapter)

        editTbmWeight = findViewById(R.id.edit_tmb_weight)
        editTbmHeight = findViewById(R.id.edit_tmb_height)
        editTbmAge = findViewById(R.id.edit_tmb_age)

        buttonTbmSend = findViewById(R.id.btn_tmb_send)
        buttonTbmSend.setOnClickListener {

            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = editTbmWeight.text.toString().toInt()
            val height = editTbmHeight.text.toString().toInt()
            val age = editTbmAge.text.toString().toInt()
            val result = calculateTbm(weight, height, age)
            val response = tmbRequest(result)

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.tmb_response, response))
                .setPositiveButton(android.R.string.ok) { dialog, p1 ->
                }
                .setNegativeButton(R.string.save) { dialog, p1 ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "tmb", res = response))

                        runOnUiThread {
                            openListCalcActivity()
                        }

                    }.start()
                }
                .create()
                .show()


            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        }

    }

    private fun tmbRequest(tbm: Double): Double {
        val items = resources.getStringArray(R.array.tmb_lifestyle)

        return if (lifeStyle.text.toString() == items[0]) tbm * 1.2
        else if (lifeStyle.text.toString() == items[1]) tbm * 1.375
        else if (lifeStyle.text.toString() == items[2]) tbm * 1.55
        else if (lifeStyle.text.toString() == items[3]) tbm * 1.725
        else if (lifeStyle.text.toString() == items[4]) tbm * 1.9
        else 0.0
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search) {
            finish()
            openListCalcActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun openListCalcActivity() {

        val intent = Intent(this, ListCalcActivity::class.java)
        intent.putExtra("type", "tmb")
        startActivity(intent)
    }

    private fun calculateTbm(weight: Int, height: Int, age: Int): Double {
        return 66 + (13.8 * weight) + (5 * height) - (6.8 * age)
    }

    private fun validate(): Boolean {
        return editTbmHeight.text.toString().isNotEmpty() &&
                editTbmWeight.text.toString().isNotEmpty() &&
                editTbmAge.text.toString().isNotEmpty() &&
                !editTbmHeight.text.toString().startsWith("0") &&
                !editTbmWeight.text.toString().startsWith("0") &&
                !editTbmAge.text.toString().startsWith("0")

    }
}