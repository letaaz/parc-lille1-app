package com.example.letaaz.parclille1.ui.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.letaaz.parclille1.InjectorUtils
import com.example.letaaz.parclille1.ui.MainActivity
import com.example.letaaz.parclille1.R
import com.example.letaaz.parclille1.data.Probleme
import kotlinx.android.synthetic.main.probleme_recyclerview_item.view.*

/**
 * This class represents the Problem's recyclerView adapter
 * Responsible for inflating the item's view
 * It also provides a listener function for an item
 */
class ProblemeListAdapter(context: Context): RecyclerView.Adapter<ProblemeListAdapter.ProblemeViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mProblemes: List<Probleme>? = null
    private val mContext: Context = context
    var onItemClick: ((Probleme) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemeViewHolder {
        val itemView = mInflater.inflate(R.layout.probleme_recyclerview_item, parent, false)
        return ProblemeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProblemeViewHolder, position: Int) {
        if(mProblemes != null) {
            val current = mProblemes!![position]
            holder.problemeItemColor.setBackgroundColor(InjectorUtils.problemeTypeColor(mContext, current.type))
            holder.problemeItemType.text = current.type
            holder.problemeItemDate.text = MainActivity.DATE_FORMAT.format(current.date)
            if (!current.adresse.isEmpty())
                holder.problemeItemPosition.text = current.adresse
            else
                holder.problemeItemPosition.text = "" + current.position_lat + "," + current.position_long
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
        val problemeItemColor = itemView.item_probleme_color
        val problemeItemType = itemView.item_probleme_type
        val problemeItemDate = itemView.item_probleme_date
        val problemeItemPosition = itemView.item_probleme_position

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(mProblemes!![adapterPosition])
            }
        }
    }

}