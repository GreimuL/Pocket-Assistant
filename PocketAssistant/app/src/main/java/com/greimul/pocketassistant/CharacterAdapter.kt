package com.greimul.pocketassistant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greimul.pocketassistant.Characters.CharType
import com.greimul.pocketassistant.Characters.CharacterState
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterAdapter(): RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    val viewData:List<CharType> = listOf(CharType.WhiteCat,CharType.ProtoBoy)

    class ViewHolder(v:View):RecyclerView.ViewHolder(v){
        var imageView = v.imageview_item
        var textViewName = v.textview_char_item
        var textViewDesc = v.textview_char_desc
    }

    override fun getItemCount(): Int = CharacterState.charNum

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(viewData[position].imageResId)
        holder.textViewName.text = viewData[position].nameStr
        holder.textViewDesc.text = viewData[position].desc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_character,parent,false)
        return ViewHolder(v)
    }
}