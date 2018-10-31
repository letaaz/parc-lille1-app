package com.example.letaaz.parclille1

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY_1: String = "com.example.letaaz.parclille1.REPLY_1"
        const val EXTRA_REPLY_2: String = "com.example.letaaz.parclille1.REPLY_2"
    }

    private var mSpinner : Spinner? = null
    private var mPosition : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        mSpinner = findViewById(R.id.form_probleme_type_spinner)
        mPosition = findViewById(R.id.form_probleme_location_text)

        val validation_btn = findViewById<Button>(R.id.form_probleme_validation_btn)
        validation_btn.setOnClickListener {
            val replyIntent = Intent()
            // form checking
            val position = mPosition!!.text
            val types = resources.getStringArray(R.array.probleme_type)
            val type = types[mSpinner!!.selectedItemPosition]
            replyIntent.putExtra(EXTRA_REPLY_1, type)
            replyIntent.putExtra(EXTRA_REPLY_2, position)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }
}
