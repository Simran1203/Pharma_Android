package com.pharmacy.crack.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import com.pharmacy.crack.R
import kotlinx.android.synthetic.main.layout_bonus_top.view.*

class BonusTopLayout: FrameLayout {

    init {
        inflate(context, R.layout.layout_bonus_top, this)
    }

    var title: String? by TextViewBonus(txtRemain)
    var icon: Drawable? by ImageViewBonus(imgMainTopBonus)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        attrs ?: return

    }

}