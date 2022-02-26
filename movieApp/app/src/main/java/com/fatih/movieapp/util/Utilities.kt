package com.fatih.movieapp.util

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

@BindingAdapter("android:imageUrl")
fun downloadImage(view:ImageView,url:String ){
        try {

            view.alpha=0f
            Picasso.get().load(url).noFade().into(view,object :Callback{
                override fun onSuccess() {
                        view.animate().setDuration(300).alpha(1f).start()
                }
                override fun onError(e: java.lang.Exception?) {
                }

            })
        }catch (e:Exception){

        }
}
@BindingAdapter("android:imageUrlColorless")
fun downloadImageColorless(view:ImageView,url:String? ){
    try {
        view.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f)})
        view.alpha=0f
        Picasso.get().load(url).noFade().into(view,object :Callback{
            override fun onSuccess() {
                view.animate().setDuration(600).alpha(0.3f).start()
            }
            override fun onError(e: java.lang.Exception?) {
            }

        })
    }catch (e:Exception){

    }
}




