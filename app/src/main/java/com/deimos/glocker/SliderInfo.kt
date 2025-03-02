package com.deimos.glocker

data class SliderInfo(val position: Float, var onSliderChange: (Float) -> Unit)