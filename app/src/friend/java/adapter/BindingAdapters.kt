package adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.demo.swt.mystudyappshop.Activity.BigImageActivity
import com.facebook.drawee.view.SimpleDraweeView
import com.jaeger.ninegridimageview.NineGridImageView
import com.jaeger.ninegridimageview.NineGridImageViewAdapter
import java.util.*

@BindingAdapter("app:loadUrl")
fun SimpleDraweeView.loadBingPic(url: String?) {
    this.setImageURI(url)
}

@BindingAdapter("app:bindNineImg")
fun NineGridImageView<String>.bindImgList(list: List<String>?) {
    list ?: return
    val nineGridImageViewAdapter = object : NineGridImageViewAdapter<String>() {
        override fun onDisplayImage(context: Context, imageView: ImageView, s: String) {
            Glide.with(context).load(s).into(imageView)

        }

        override fun onItemImageClick(context: Context?, index: Int, list: MutableList<String>) {
            super.onItemImageClick(context, index, list)
            val intent = Intent(context, BigImageActivity::class.java)
            val bundle = Bundle()
            if (list.size > 0) {
                bundle.putStringArrayList("tulist", list as ArrayList<String>)
                bundle.putInt("pos", index)
            }
            intent.putExtras(bundle)
            context!!.startActivity(intent)
        }
    }
    setAdapter(nineGridImageViewAdapter)
    setImagesData(list)
}

