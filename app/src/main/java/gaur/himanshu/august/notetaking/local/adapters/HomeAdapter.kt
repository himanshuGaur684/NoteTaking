package gaur.himanshu.august.notetaking.local.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import gaur.himanshu.august.notetaking.databinding.ListItemBinding
import gaur.himanshu.august.notetaking.local.models.Note
import kotlinx.android.synthetic.main.list_item.view.*

class HomeAdapter(private val clickListioners: HomeAdaptersClickListioners) :
    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {


    var list = mutableListOf<Note>()
    fun setContentList(list: List<Note>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.MyViewHolder, position: Int) {
        holder.viewDataBinding.setVariable(BR.note, this.list[position])
        holder.viewDataBinding.root.setOnLongClickListener {
            clickListioners.clickListioners(this.list[position], position)
            return@setOnLongClickListener false
        }

        holder.viewDataBinding.root.list_item_root.setOnClickListener {
            clickListioners.detailsClickListioners(this.list[position], position)
        }
    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}