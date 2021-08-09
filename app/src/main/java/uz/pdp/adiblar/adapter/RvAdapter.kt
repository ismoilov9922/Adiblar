package uz.pdp.adiblar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.pdp.adiblar.R
import uz.pdp.adiblar.databinding.CustomItemBinding
import uz.pdp.adiblar.models.AdibData
import java.util.*
import kotlin.collections.ArrayList

class RvAdapter(
    var list: ArrayList<AdibData>,
    private var onItemClickListener: OnItemClickListener,
) :
    RecyclerView.Adapter<RvAdapter.MyViewHolder>(), Filterable {
    val list1 = ArrayList<AdibData>(list)

    inner class MyViewHolder(var customItemBinding: CustomItemBinding) : RecyclerView.ViewHolder(
        customItemBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CustomItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var adibData = list[position]
        if (adibData.getSaved() == true) {
            holder.customItemBinding.save.setBackgroundResource(R.drawable.ic_vector__23_)
        } else {
            holder.customItemBinding.save.setBackgroundResource(R.drawable.ic_vector__25_)
        }
        val obj = list[position]
        Picasso.get().load(obj.getImgUrl()).into(holder.customItemBinding.image)
        val name = obj.getFullName()
        var pos = 0
        for (i in name!!.indices) {
            if (name[i] == ' ') {
                pos = i
                break
            }
        }
        val n = name.substring(0, pos)
        val s = name.substring(pos + 1)
        holder.customItemBinding.name.text = "${n}\n${s}"
        holder.customItemBinding.date.text = "(${obj.getDateOfBirth()})"
        holder.customItemBinding.card.setOnClickListener {
            onItemClickListener.onItemMusic(obj)
        }
        holder.customItemBinding.save.setOnClickListener {
            onItemClickListener.onItemSaved(holder.customItemBinding.save, position, list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemMusic(writersClass: AdibData)
        fun onItemSaved(
            imageView: ImageView,
            position: Int,
            adibData: AdibData,
        )
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val newList = ArrayList<AdibData>()
            if (constraint.isEmpty()) {
                newList.addAll(list1)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim()
                for (i in 0 until list1.size) {
                    if (list1[i].getFullName()?.lowercase(Locale.getDefault())!!
                            .contains(filterPattern)
                    ) {
                        newList.add(list1[i])
                    }
                }
            }
            val results = FilterResults()
            results.values = newList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            list.clear()
            list.addAll(results.values as ArrayList<AdibData>)
            notifyDataSetChanged()
        }
    }
}