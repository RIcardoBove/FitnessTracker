package co.tiagoaguiar.fitnesstracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import co.tiagoaguiar.fitnesstracker.model.App
import co.tiagoaguiar.fitnesstracker.model.Calc

class ImcActivity : AppCompatActivity() {
    private lateinit var editImcWeight: EditText
    private lateinit var editImcHeight: EditText
    private lateinit var buttonImcSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editImcWeight = findViewById(R.id.edit_imc_weight)
        editImcHeight = findViewById(R.id.edit_imc_height)
        buttonImcSend = findViewById(R.id.btn_imc_send)

        buttonImcSend.setOnClickListener {

            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = editImcWeight.text.toString().toInt()
            val height = editImcHeight.text.toString().toInt()
            val result = calculateImc(weight, height)
            val imcResposeId = imcResponce(result)

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, result))
                .setMessage(imcResposeId)
                .setPositiveButton(android.R.string.ok) { dialogInterface, p1 ->

                }
                .setNegativeButton(R.string.save) { dialogInterface, p1 ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "imc", res = result))

                        runOnUiThread {
                            openListCalcActivity()
                        }

                    }.start()
                }
                .create()
                .show()

            //                    object : DialogInterface.OnClickListener {
//                        override fun onClick(p0: DialogInterface?, p1: Int) {
//
//                        }
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        }
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
        intent.putExtra("type", "imc")
        startActivity(intent)
    }

    //Essa anotação diz para o desenvolvedor que essa funçao não retorna um inteiro
    //qualquer, mas um resource ou R.
    @StringRes
    private fun imcResponce(imc: Double): Int {

        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 20.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }

    }

    private fun calculateImc(weight: Int, height: Int): Double {
        //peso / (altura * altura)
        return weight / ((height / 100.0) * (height / 100.0))
    }

    private fun validate(): Boolean {
        return editImcWeight.text.toString().isNotEmpty() &&
                editImcHeight.text.toString().isNotEmpty() &&
                !editImcHeight.text.toString().startsWith("0") &&
                !editImcWeight.text.toString().startsWith("0")
    }
}