package com.udacity.asteroidradar

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.db.Entities
import com.udacity.asteroidradar.main.AsteroidsRecyclerViewAdapter
import com.udacity.asteroidradar.db.Entities.AsteroidEntity

@BindingAdapter("asteroidsList")
fun bind_asteroidsList(recyclerView: RecyclerView,data:List<AsteroidEntity>?){
    data?.let {
        val adapter =  recyclerView.adapter as AsteroidsRecyclerViewAdapter
        adapter.submitList(data)
    }

}
@BindingAdapter("pictureOfDayUrl")
fun bind_pictureOfDayUrl(imageView: ImageView, url: String?) {
    url?.let { Picasso.with(imageView.context).load(Uri.parse(url)).into(imageView)}

}

@BindingAdapter("hazard_status")
fun bind_hazard_status(imageView: ImageView, hazard_status: Boolean) {
    if (hazard_status) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bind_asteroidStatusImage(imageView: ImageView, hazard_status: Boolean) {
    if (hazard_status) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("absolute_magnitude")
fun bind_absolute_magnitude(textView: TextView, absolute_magnitude: Double) {
    val context = textView.context
    //val number = distance.toDouble()
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), absolute_magnitude)
}

@BindingAdapter("estimated_diameter")
fun bind_estimated_diameter(textView: TextView, estimated_diameter: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), estimated_diameter)
}

@BindingAdapter("relative_velocity")
fun bind_relative_velocity(textView: TextView, relative_velocity: String) {
    val context = textView.context
    val number = relative_velocity.toDouble()
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)

@BindingAdapter("distance_from_earth")
fun bind_distance_from_earth(textView: TextView, distance_from_earth: String) {
    val context = textView.context
    //val number = distance_from_earth.toDouble()
    textView.text = String.format(context.getString(R.string.km_unit_format), distance_from_earth)
}

}
