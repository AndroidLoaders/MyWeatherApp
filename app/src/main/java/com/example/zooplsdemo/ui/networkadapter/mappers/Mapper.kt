package com.example.zooplsdemo.ui.networkadapter.mappers

interface Mapper<From, To> {
    fun mapTo(from: From): To
}