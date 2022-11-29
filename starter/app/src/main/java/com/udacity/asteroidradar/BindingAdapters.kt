package com.udacity.asteroidradar

import android.content.DialogInterface.OnClickListener
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.main.AsteroidsRecyclerViewAdapter

@BindingAdapter("asteroidsList")
fun bindAsteroidsList(recyclerView: RecyclerView, data:List<AsteroidBrief>?){
    data?.let {
        val adapter =  recyclerView.adapter as AsteroidsRecyclerViewAdapter
        adapter.submitList(data)
    }

}
@BindingAdapter("pictureOfDayUrl")
fun bindPictureOfDayUrl(imageView: ImageView, url: String?) {
    url?.let { Picasso.with(imageView.context).load(Uri.parse(url)).into(imageView)}

}

@BindingAdapter("hazard_status")
fun bindHazardStatus(imageView: ImageView, hazard_status: Boolean) {
    if (hazard_status) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindAsteroidStatusImage(imageView: ImageView, hazard_status: Boolean) {
    if (hazard_status) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("absolute_magnitude")
fun bindAbsoluteMagnitude(textView: TextView, absolute_magnitude: Double?) {
    absolute_magnitude?.let {
        val context = textView.context
        textView.text =
            String.format(context.getString(R.string.astronomical_unit_format), absolute_magnitude)
    }
}

@BindingAdapter("estimated_diameter")
fun bindEstimatedDiameter(textView: TextView, estimated_diameter: Double?) {
    estimated_diameter?.let {
        val context = textView.context
        textView.text =
            String.format(context.getString(R.string.km_unit_format), estimated_diameter)
    }
}

@BindingAdapter("relative_velocity")
fun bindRelativeVelocity(textView: TextView, relative_velocity: String?) {
    relative_velocity?.let {
        val context = textView.context
        val number = relative_velocity.toDouble()
        textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
    }
}

@BindingAdapter("distance_from_earth")
fun bindDistanceFromEarth(textView: TextView, distance_from_earth: String?) {
    distance_from_earth?.let {
        val context = textView.context
        textView.text =
            String.format(context.getString(R.string.astronomical_unit_format), distance_from_earth)
    }
}




