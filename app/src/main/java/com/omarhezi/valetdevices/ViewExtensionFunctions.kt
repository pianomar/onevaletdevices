package com.omarhezi.valetdevices

import android.view.View

fun View.showIf(condition: () -> Boolean) {
     visibility = if (condition.invoke()) View.VISIBLE else View.GONE
}