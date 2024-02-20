package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.actionButton)
        val text = findViewById<TextView>(R.id.titleTextView)
        val input = findViewById<TextInputEditText>(R.id.inputEditText)
        button.setOnClickListener {
            text.text = input.text
            input.setText("")
        }
    }
}