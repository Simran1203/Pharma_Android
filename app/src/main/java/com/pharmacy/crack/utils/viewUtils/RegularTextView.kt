package com.pharmacy.crack.utils.viewUtils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.pharmacy.crack.R

class RegularTextView: AppCompatTextView {
    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        init()
    }

    private fun init() {
        if (!isInEditMode) {
            var tf: Typeface? = Typeface.createFromAsset(context.assets, "fonts/" + context.getString(
                    R.string.regular_font))

            typeface = tf
        }
    }
}