package com.williamlu.gankio.model

/**
 * @Author: WilliamLu
 * @Data: 2019/1/17
 * @Description:
 */
data class ClassifyDataBean(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)