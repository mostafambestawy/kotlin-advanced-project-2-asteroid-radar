package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.AsteroidBrief
import com.udacity.asteroidradar.databinding.AsteroidItemBinding
import com.udacity.asteroidradar.db.Entities.AsteroidEntity


class AsteroidsRecyclerViewAdapter(val onClickListener: OnClickListener) :
    ListAdapter<AsteroidBrief, AsteroidsRecyclerViewAdapter.AsteroidViewHolder>(DiffCallback) {
    /**
     * The AsteroidViewHolder constructor takes the binding variable from the associated
     * AsteroidItem, which nicely gives it access to the full [AsteroidBrief] information.
     */
    class AsteroidViewHolder(private var binding: AsteroidItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroidBrief: AsteroidBrief) {
            binding.asteroidBrief = asteroidBrief
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }


    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [AsteroidBrief]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<AsteroidBrief>() {
        override fun areItemsTheSame(oldItem: AsteroidBrief, newItem: AsteroidBrief): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AsteroidBrief, newItem: AsteroidBrief): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AsteroidViewHolder {

        return AsteroidViewHolder(
            AsteroidItemBinding.inflate(
                LayoutInflater.from(parent.context),
                /**
                 * we must use the complete arguments function by passing parent, false
                 * to ensure  the whole width of recycler view
                 * */
                parent, false
            )
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroidBrief = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(asteroidBrief)
        }
        holder.bind(asteroidBrief)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [AsteroidBrief]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [AsteroidBrief]
     */
    class OnClickListener(val clickListener: (asteroidBrief: AsteroidBrief) -> Unit) {
        fun onClick(asteroidBrief: AsteroidBrief) = clickListener(asteroidBrief)
    }
}