package com.peterstaranchuk.common

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import java.io.Serializable

data class ViewVisibility private constructor(private var value: Visibility) : BaseObservable(),
    Serializable {

    fun get(): Visibility {
        return value
    }

    private fun set(value: Visibility) {
        if (this.value != value) {
            this.value = value
            notifyChange()
        }
    }

    fun visible() {
        set(Visibility.VISIBLE)
    }

    fun invisible() {
        set(Visibility.INVISIBLE)
    }

    fun gone() {
        set(Visibility.GONE)
    }

    companion object {
        fun visible(): ViewVisibility {
            return ViewVisibility(Visibility.VISIBLE)
        }

        fun invisible(): ViewVisibility {
            return ViewVisibility(Visibility.INVISIBLE)
        }

        fun gone(): ViewVisibility {
            return ViewVisibility(Visibility.GONE)
        }
    }

}

enum class Visibility {
    VISIBLE, INVISIBLE, GONE
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visibility: ViewVisibility) {
    when (visibility.get()) {
        Visibility.VISIBLE -> view.visibility = View.VISIBLE
        Visibility.INVISIBLE -> view.visibility = View.INVISIBLE
        Visibility.GONE -> view.visibility = View.GONE
    }
}