package com.greimul.crawlertest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kotlinx.android.synthetic.main.item_setting.*
import kotlinx.android.synthetic.main.item_setting.view.*

class Fragment2 : Fragment(){
    val settingStrArray = arrayOf("Character","Text","ETC")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting,container,false)

        view.recyclerview_setting.apply{
            adapter = SettingRecyclerAdapter()
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        }

        return view
    }

    inner class SettingRecyclerAdapter(): RecyclerView.Adapter<SettingRecyclerAdapter.ViewHolder>(){

        inner class ViewHolder(v:View):RecyclerView.ViewHolder(v){
            var textView = v.textview_setting
        }

        override fun getItemCount(): Int =settingStrArray.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = settingStrArray[position]
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_setting,parent,false)
            return ViewHolder(view)
        }

    }
}