package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var textInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textInput = findViewById(R.id.text_input)

        // Настройка кнопок
        val buttonOne: Button = findViewById(R.id.button_one)
        buttonOne.setOnClickListener { appendText("1") }

        // Добавьте обработчики для остальных кнопок аналогично
    }

    private fun appendText(value: String) {
        textInput.text.append(value)
    }

    // Добавить методы для обработки арифметических операций
}
