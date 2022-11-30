package com.udacity.asteroidradar

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.main.AsteroidsRecyclerViewAdapter
import com.udacity.asteroidradar.main.RequestStatus
import kotlinx.android.synthetic.main.fragment_main.view.*

@BindingAdapter(value = ["bind:asteroidsList", "bind:loadingStatus"])
fun bindAsteroidsList(
    recyclerView: RecyclerView,
    data: List<AsteroidBrief>?,
    loadingStatus: RequestStatus?
) {
    loadingStatus?.let {
        if (loadingStatus == RequestStatus.DONE) {
            recyclerView.visibility = View.VISIBLE
        } else recyclerView.visibility = View.GONE
    }
    data?.let {
        val adapter = recyclerView.adapter as AsteroidsRecyclerViewAdapter
        adapter.submitList(data)
    }

}

@BindingAdapter(value = ["bind:pictureOfDayUrl", "bind:contentDescription"])
fun bindPictureOfDayUrl(imageView: ImageView, url: String?, contentDescription: String?) {
    url?.let {
        val context = imageView.context
        Picasso.with(context).load(Uri.parse(url)).into(imageView)
        imageView.contentDescription = String.format(
            context.getString(R.string.nasa_picture_of_day_content_description_format),
            contentDescription
        )
    }

}

@BindingAdapter("hazard_status")
fun bindHazardStatus(imageView: ImageView, hazard_status: Boolean) {
    val context = imageView.context
    if (hazard_status) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = context.getString(R.string.potentially_hazardous_asteroid)

    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = context.getString(R.string.not_hazardous_asteroid)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindAsteroidStatusImage(imageView: ImageView, hazard_status: Boolean) {
    val context = imageView.context
    if (hazard_status) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = context.getString(R.string.potentially_hazardous_asteroid)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = context.getString(R.string.not_hazardous_asteroid)
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

@BindingAdapter("errorTextLoadingStatus")
fun bindErrorText(textView: TextView, loadingStatus: RequestStatus?) {
    loadingStatus?.let {
        if (loadingStatus == RequestStatus.ERROR)
            textView.visibility = View.VISIBLE
        else
            textView.visibility = View.GONE
    }
}

@BindingAdapter("progressBarLoadingStatus")
fun bindLoadingProgressBar(progressBar: ProgressBar, loadingStatus: RequestStatus?) {
    loadingStatus?.let {
        if (loadingStatus == RequestStatus.LOADING)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }
}




