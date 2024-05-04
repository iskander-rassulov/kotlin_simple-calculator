package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var textInput: EditText
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textInput = findViewById(R.id.text_input)
        setNumericOnClickListener()
        setOperatorOnClickListener()
    }

    private fun setNumericOnClickListener() {
        val buttonsIds = listOf(R.id.button_one, R.id.button_two, R.id.button_three, R.id.button_four,
            R.id.button_five, R.id.button_six, R.id.button_seven, R.id.button_eight, R.id.button_nine, R.id.button_zero)

        buttonsIds.forEach {
            findViewById<Button>(it).setOnClickListener { v ->
                val button = v as Button
                if (stateError) {
                    textInput.text.clear()
                    stateError = false
                }
                textInput.append(button.text)
                lastNumeric = true
            }
        }
    }

    private fun setOperatorOnClickListener() {
        val operatorsIds = listOf(R.id.button_add, R.id.button_subtract, R.id.button_multiply, R.id.button_divide)

        operatorsIds.forEach {
            findViewById<Button>(it).setOnClickListener { v ->
                val button = v as Button
                if (lastNumeric && !stateError) {
                    textInput.append(button.text)
                    lastNumeric = false
                    lastDot = false
                }
            }
        }

        findViewById<Button>(R.id.button_dot).setOnClickListener {
            if (lastNumeric && !stateError && !lastDot) {
                textInput.append(".")
                lastNumeric = false
                lastDot = true
            }
        }

        findViewById<Button>(R.id.button_clear).setOnClickListener {
            textInput.text.clear()
            lastNumeric = false
            stateError = false
            lastDot = false
        }

        findViewById<Button>(R.id.button_delete).setOnClickListener {
            val length = textInput.text.length
            if (length > 0) {
                textInput.text.delete(length - 1, length)
            }
        }

        findViewById<Button>(R.id.button_equals).setOnClickListener { onEqual() }
    }

    private fun onEqual() {
        if (lastNumeric && !stateError) {
            val text = textInput.text.toString()
            val expression = ExpressionBuilder(text).build()
            try {
                val result = expression.evaluate()
                textInput.text.clear()
                textInput.append(DecimalFormat("0.######").format(result as Double)) // Явное приведение к Double
                lastDot = result.toString().contains(".")
            } catch (ex: Exception) {
                textInput.text.clear()
                textInput.append("Error")
                stateError = true
                lastNumeric = false
            }
        }
    }
}

