package com.elemental.templateapplication.features.Gallery.data

data class GalleryMedia(
    var name: String,
    var title: String,
    var resImg: Int,
    var isSelected: Boolean = false
)