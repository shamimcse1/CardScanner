package com.example.cardscanner

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import ir.arefdev.irdebitcardscanner.DebitCard
import java.lang.String
import kotlin.CharSequence
import kotlin.Int


class SecondActivity : AppCompatActivity() {
    private val space = ' '

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val number = intent.getStringExtra("cardNumber")
        val expiryMonth = intent.getIntExtra("cardExpiryMonth", 0)
        val expiryYear = intent.getIntExtra("cardExpiryYear", 0)
        val card = DebitCard(number, expiryMonth, expiryYear)

        val cardInputWidget = findViewById<TextView>(R.id.card_input_widget)

        val formattedCardNumber = StringBuilder()
        for (i in 0 until card.number.length) {
            if (i == 4 || i == 8 || i == 12) {
                formattedCardNumber.append(" ")
            }
            formattedCardNumber.append(card.number.get(i))
        }
        var txt = formattedCardNumber.toString()

        if (!TextUtils.isEmpty(card.expiryForDisplay())) {
            txt += " \t " + card.expiryForDisplay()
        }
        cardInputWidget.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                FourDigitCardFormat(editable)
            }
        })
        cardInputWidget.text = txt
    }

    private fun FourDigitCardFormat(s: Editable) {
        // Remove spacing char
        if (s.length > 0) {
            if (s[s.length - 1] == space) {
                s.delete(s.length - 1, s.length)
            }
        }
        if (s.length > 0 && s.length % 5 == 0) {
            val c = s[s.length - 1]
            if (space === c) {
                s.delete(s.length - 1, s.length)
            }
        }
        // Insert char where needed.
        if (s.length > 0 && s.length % 5 == 0) {
            val c = s[s.length - 1]
            // Only if its a digit where there should be a space we insert a space
            if (Character.isDigit(c)
                && TextUtils.split(s.toString(), String.valueOf(space)).size <= 3
            ) {
                s.insert(s.length - 1, String.valueOf(space))
            }
        }
    }

}