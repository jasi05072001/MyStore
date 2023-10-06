package com.jasmeet.myStore.utils

fun trimToLength(input: String, maxLength: Int): String {
    return if (input.length <= maxLength) {
        input
    } else {
        input.substring(0, maxLength) + "..."
    }
}
