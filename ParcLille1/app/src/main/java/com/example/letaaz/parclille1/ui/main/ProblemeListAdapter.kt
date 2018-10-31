package com.example.letaaz.parclille1.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.letaaz.parclille1.R
import com.example.letaaz.parclille1.data.Probleme
import kotlinx.android.synthetic.main.probleme_recyclerview_item.view.*

class ProblemeListAdapter(context: Context): RecyclerView.Adapter<ProblemeListAdapter.ProblemeViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mProblemes: List<Probleme>? = null
    var onItemClick: ((Probleme) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemeViewHolder {
        val itemView = mInflater.inflate(R.layout.probleme_recyclerview_item, parent, false)
        return ProblemeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProblemeViewHolder, position: Int) {
        if(mProblemes != null) {
            val current = mProblemes!![position]
            holder.problemeItemType.text = current.type
            holder.problemeItemPosition.text = current.position
        } else {
            holder.problemeItemType.text = "ERROR ON BIND"
            holder.problemeItemPosition.text = "ERROR ON BIND"
        }
    }

    override fun getItemCount(): Int {
        if (mProblemes != null) return mProblemes!!.size else return 0
    }

    fun setProblemes(problemes :List<Probleme>) {
        mProblemes = problemes
        notifyDataSetChanged()
    }

    inner class ProblemeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val problemeItemType = itemView.item_probleme_type
        val problemeItemPosition = itemView.item_probleme_position

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(mProblemes!![adapterPosition])
            }
        }
    }

}