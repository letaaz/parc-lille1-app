package com.example.letaaz.parclille1

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.example.letaaz.parclille1.data.AppDatabase
import com.example.letaaz.parclille1.data.Probleme
import com.example.letaaz.parclille1.data.ProblemeRepository
import com.example.letaaz.parclille1.ui.detail.ProblemeDetailViewModel
import com.example.letaaz.parclille1.ui.detail.ProblemeDetailViewModelFactory
import com.example.letaaz.parclille1.ui.ui.ProblemeViewModel

class DetailProblemeActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY_PROBLEME_ID: String = "com.example.letaaz.parclille1.PROBLEME_ID"
    }

    private var mPosition:TextView? = null
    private var mType: TextView? = null
    private var mAddresse: TextView? = null
    private var mDesc: TextView? = null
    private var mDate: TextView? = null
    private lateinit var mProblemeDetailViewModel: ProblemeDetailViewModel
    private var mId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_activity)

        mPosition = findViewById(R.id.detail_probleme_position)
        mType = findViewById(R.id.detail_probleme_type)
        mAddresse = findViewById(R.id.detail_probleme_adresse)
        mDesc = findViewById(R.id.detail_probleme_description)
        mDate = findViewById(R.id.detail_probleme_date_creation)
        mId = intent.getLongExtra("PROB_ID", 0)

        val factory = InjectorUtils.provideProblemeDetailViewModelFactory(this, mId.toInt())
        mProblemeDetailViewModel = ViewModelProviders.of(this, factory).get(ProblemeDetailViewModel::class.java)
        mProblemeDetailViewModel.probleme.observe(this, Observer {
            if(it != null)
                updateFields(it)
        })


        val suppr_btn = findViewById<Button>(R.id.supprimer_probleme_btn)
        suppr_btn.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Voulez-vous supprimer ce problème ?")
                    .setCancelable(false)
                    .setPositiveButton("Continuer") { _,_ ->
                        run {
                            val replyIntent = Intent()
                            replyIntent.putExtra(EXTRA_REPLY_PROBLEME_ID, mId)
                            setResult(Activity.RESULT_OK, replyIntent)
                            finish()
                        }
                    }
                    .setNegativeButton("Annuler") { dialog, _ -> dialog.cancel()
                    }

            val alert = dialogBuilder.create()
            alert.setTitle("Alerte de confirmation")
            alert.show()
        }
    }

    /**
     * Update the view fields according to probleme arg
     */
    private fun updateFields(probleme: Probleme) {
        mType!!.text = probleme.type
        mPosition!!.text = "${probleme.position_lat},${probleme.position_long}"
        mAddresse!!.text = probleme.adresse
        mDesc!!.text = probleme.description
        mDate!!.text = MainActivity.DATE_FORMAT.format(probleme.date)
    }
}