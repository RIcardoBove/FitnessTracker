package co.tiagoaguiar.fitnesstracker

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
            if (!validate()){
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = editImcWeight.text.toString().toInt()
            val height = editImcHeight.text.toString().toInt()

            val result = calculateImc(weight, height)
            Log.d("Teste", "Resultado: $result")
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