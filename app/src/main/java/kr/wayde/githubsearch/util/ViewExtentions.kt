package kr.wayde.githubsearch.util

import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView

@BindingAdapter("app:imageUrl")
fun loadImage(view: SimpleDraweeView, imageUrl: String) {
    view.setImageURI(imageUrl)
}