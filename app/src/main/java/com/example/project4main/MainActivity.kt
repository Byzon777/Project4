
package com.example.project4main

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private val FEDERAL_RATE = 0.10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etGross = findViewById<EditText>(R.id.etGross)
        val etStatePct = findViewById<EditText>(R.id.etStatePct)
        val cbSocial = findViewById<CheckBox>(R.id.cbSocial)
        val cbMedicare = findViewById<CheckBox>(R.id.cbMedicare)
        val btnCalc = findViewById<Button>(R.id.btnCalculate)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnCalc.setOnClickListener {
            // Validate inputs
            val grossText = etGross.text.toString()
            val stateText = etStatePct.text.toString()
            if (grossText.isBlank() || stateText.isBlank()) {
                Toast.makeText(this, "Enter both salary and state %", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val gross = grossText.toDouble()
            val statePct = stateText.toDouble() / 100.0
            val socialRate = if (cbSocial.isChecked) 0.06 else 0.0
            val medicareRate = if (cbMedicare.isChecked) 0.015 else 0.0

            // Compute deductions
            val fedDed = gross * FEDERAL_RATE
            val stateDed = gross * statePct
            val socDed = gross * socialRate
            val medDed = gross * medicareRate
            val totalDed = fedDed + stateDed + socDed + medDed
            val netSalary = gross - totalDed

            // Display results
            val fmt = Locale.US
            tvResult.text = """
                Federal Tax: $${"%.2f".format(fedDed)}
                State Tax:   $${"%.2f".format(stateDed)}
                Social Sec:  $${"%.2f".format(socDed)}
                Medicare:    $${"%.2f".format(medDed)}

                Total Ded.:  $${"%.2f".format(totalDed)}
                Net Salary:  $${"%.2f".format(netSalary)}
            """.trimIndent()
        }
    }
}
