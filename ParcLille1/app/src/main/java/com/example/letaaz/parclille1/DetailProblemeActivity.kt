package com.example.letaaz.parclille1

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class DetailProblemeActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY_1: String = "com.example.letaaz.parclille1.REPLY_1"
        const val EXTRA_REPLY_2: String = "com.example.letaaz.parclille1.REPLY_2"
    }

    private var mPosition :TextView? = null
    private var mType : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_activity)
        mPosition = findViewById(R.id.detail_probleme_position)
        mPosition!!.text = intent.getStringExtra("PROB_POSITION")
        mType = findViewById(R.id.detail_probleme_type)
        mType!!.text = intent.getStringExtra("PROB_TYPE")

        val suppr_btn = findViewById<Button>(R.id.supprimer_probleme_btn)
        suppr_btn.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Voulez-vous supprimer ce problème ?")
                    .setCancelable(false)
                    .setPositiveButton("Continuer") { dialog, id ->
                        run {
                            val replyIntent = Intent()
                            replyIntent.putExtra(EXTRA_REPLY_1, mType!!.text)
                            replyIntent.putExtra(EXTRA_REPLY_2, mPosition!!.text)
                            setResult(Activity.RESULT_OK, replyIntent)
                            finish()
                        }
                    }
                    .setNegativeButton("Annuler", {
                        dialog, id -> dialog.cancel()
                    })

            val alert = dialogBuilder.create()
            alert.setTitle("Alerte de confirmation")
            alert.show()
        }
    }
}