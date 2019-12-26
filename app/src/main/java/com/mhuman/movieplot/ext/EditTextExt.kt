package com.mhuman.movieplot.ext

import android.graphics.drawable.Drawable
import android.view.MotionEvent
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.onRightDrawableClicked(onClicked: (view: TextInputEditText) -> Unit) {
    this.setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is TextInputEditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}

fun TextInputEditText.makeClearableEditText(
    onIsNotEmpty: (() -> Unit)?,
    onClear: (() -> Unit)?,
    clearDrawable: Drawable
) {
    val updateRightDrawable = {
        this.setCompoundDrawables(
            null, null,
            if (text?.isNotEmpty()!!) clearDrawable else null,
            null
        )
    }
    updateRightDrawable()

    this.doAfterTextChanged {
        if (it?.isNotEmpty()!!) {
            onIsNotEmpty?.invoke()
        }
        updateRightDrawable()
    }
    this.onRightDrawableClicked {
        this.text?.clear()
        this.setCompoundDrawables(null, null, null, null)
        onClear?.invoke()
        this.requestFocus()
    }
}

private const val COMPOUND_DRAWABLE_RIGHT_INDEX = 2

fun TextInputEditText.makeClearableEditText(onIsNotEmpty: (() -> Unit)?, onCleared: (() -> Unit)?) {
    compoundDrawables[COMPOUND_DRAWABLE_RIGHT_INDEX]?.let { clearDrawable ->
        makeClearableEditText(onIsNotEmpty, onCleared, clearDrawable)
    }
}