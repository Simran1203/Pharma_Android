package com.pharmacy.crack.utils.viewUtils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.style.BackgroundColorSpan
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.pharmacy.crack.R
import com.pharmacy.crack.R.color.rose_pink

class RegularEditText: AppCompatEditText {
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