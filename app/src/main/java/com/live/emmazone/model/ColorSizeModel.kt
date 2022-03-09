package com.live.emmazone.model

class ColorSizeModel {
    var text: String? = null
    var isSelected: Boolean= false

    constructor()

    constructor(text: String) {
        this.text = text
    }
}