package com.example.calculadorgorjeta

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textValor = findViewById<EditText>(R.id.editText_Valor)
        val valorGorjeta = findViewById<SeekBar>(R.id.seekBar_ValorGorjeta)

        textValor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val valorFixo = findViewById<TextView>(R.id.text_ValorFixo)
                val totalFixo = findViewById<TextView>(R.id.text_TotalFixo)
                val valor = textValor.text.toString().toDoubleOrNull()
                if (valor !== null) {
                    val gorjeta = valor * 15 / 100
                    valorFixo.text = String.format("$%.2f", gorjeta)
                    totalFixo.text = String.format("$%.2f", valor + gorjeta)
                } else {
                    valorFixo.text = "$0.0"
                    totalFixo.text = "$0.0"
                }
                val antigaGorjeta = valorGorjeta.progress
                valorGorjeta.progress = 0
                valorGorjeta.progress = antigaGorjeta
            }
        })

        valorGorjeta.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val textoVariavel = findViewById<TextView>(R.id.text_Variavel)
                textoVariavel.text = "${seekBar.progress}%"

                val valorVariavel = findViewById<TextView>(R.id.text_ValorVariavel)
                val totalVariavel = findViewById<TextView>(R.id.text_TotalVariavel)
                val valor = textValor.text.toString().toDoubleOrNull()
                if (valor !== null) {
                    val gorjeta = valor * seekBar.progress / 100
                    valorVariavel.text = String.format("$%.2f", gorjeta)
                    totalVariavel.text = String.format("$%.2f", valor + gorjeta)
                } else {
                    valorVariavel.text = "$0.0"
                    totalVariavel.text = "$0.0"

                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}