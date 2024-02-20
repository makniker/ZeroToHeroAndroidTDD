package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.actionButton)
        val text = findViewById<TextView>(R.id.titleTextView)
        val input = findViewById<TextInputEditText>(R.id.inputEditText)
        input.doAfterTextChanged {
            if (it != null) {
                    button.isEnabled = it.length > 2
            }
            text.text = it
        }
        button.setOnClickListener {
            val t = input.text
            input.setText("")
            text.text = t
            button.isEnabled = false
        }
    }
}