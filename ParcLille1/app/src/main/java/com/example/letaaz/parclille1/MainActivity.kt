package com.example.letaaz.parclille1

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.letaaz.parclille1.data.Probleme
import com.example.letaaz.parclille1.ui.main.MainViewModel
import com.example.letaaz.parclille1.ui.main.ProblemeListAdapter
import kotlinx.android.synthetic.main.probleme_recyclerview_item.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ADD_PROBLEME_ACTIVITY_REQUEST_CODE = 1
        private const val DETAIL_PROBLEME_ACTIVITY_REQUEST_CODE = 2
    }

    private lateinit var mMainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val recyclerView = findViewById<RecyclerView>(R.id.probleme_recyclerview)
        val adapter = ProblemeListAdapter(this)
        adapter.onItemClick = {
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("PROB_TYPE", it.type)
            intent.putExtra("PROB_POSITION", it.position)
            startActivityForResult(intent, DETAIL_PROBLEME_ACTIVITY_REQUEST_CODE)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val mAjoutButton = findViewById<Button>(R.id.ajout_probleme_btn)
        val mViderButton = findViewById<Button>(R.id.vider_problemes_btn)

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mMainViewModel.mAllProblemes.observe(this, Observer {
            adapter.setProblemes(it!!)
        })

        mAjoutButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivityForResult(intent, ADD_PROBLEME_ACTIVITY_REQUEST_CODE)
        }

        mViderButton.setOnClickListener {
            mMainViewModel.removeAllProblemes()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

           if (requestCode == ADD_PROBLEME_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                val prob = Probleme(data!!.getStringExtra(SecondActivity.EXTRA_REPLY_1), data.getStringExtra(SecondActivity.EXTRA_REPLY_2))
                mMainViewModel.addProbleme(prob)

                mMainViewModel.mAllProblemes.value!!.forEach {
                    Log.d("MAINACTIVITY", "type : " + it.type + " | position : " + it.position)
                }
           } else if (requestCode == DETAIL_PROBLEME_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                val prob = Probleme(data!!.getStringExtra(SecondActivity.EXTRA_REPLY_1), data.getStringExtra(SecondActivity.EXTRA_REPLY_2))
                mMainViewModel.removeProbleme(prob)

           } else {
               Toast.makeText(applicationContext, "Probleme not saved", Toast.LENGTH_LONG).show()
           }
    }

}
