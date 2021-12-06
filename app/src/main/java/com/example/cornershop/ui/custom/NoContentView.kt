package com.example.cornershop.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.cornershop.R

//,
class NoContentView@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {
    var view:View? = null

    init {
        view = LayoutInflater.from(context).inflate(R.layout.no_content_view, this, false)
        val set = ConstraintSet()
        addView(view)
        set.clone(this)
        view?.let {
            set.match(it, this)
        }
        view?.visibility = View.GONE
    }


    fun show(shouldShow: Boolean){
        if(shouldShow) {
            view?.visibility = View.VISIBLE
        }else{
            view?.visibility = View.GONE
        }
    }

    fun setup(title:String,subtTitle:String){
        view?.findViewById<TextView>(R.id.no_content_title)?.text = title
        view?.findViewById<TextView>(R.id.no_content_subtitle)?.text = subtTitle
    }
}

fun ConstraintSet.match(view: View, parentView: View) {
    this.connect(view.id, ConstraintSet.TOP, parentView.id, ConstraintSet.TOP)
    this.connect(view.id, ConstraintSet.START, parentView.id, ConstraintSet.START)
    this.connect(view.id, ConstraintSet.END, parentView.id, ConstraintSet.END)
    this.connect(view.id, ConstraintSet.BOTTOM, parentView.id, ConstraintSet.BOTTOM)
}
