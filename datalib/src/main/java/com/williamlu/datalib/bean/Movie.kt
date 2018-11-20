package com.williamlu.datalib.bean

/*
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
class Movie {
    var id: String? = null
    var alt: String? = null
    var year: String? = null
    var title: String? = null
    var original_title: String? = null
    var genres: List<String>? = null
    var casts: List<Cast>? = null
    var directors: List<Cast>? = null
    var images: Avatars? = null
    val rating: Rating? = null

    inner class Rating {
        var average: Float = 0.toFloat()
    }

    inner class Cast {
        var id: String? = null
        var name: String? = null
        var alt: String? = null
        var avatars: Avatars? = null

        override fun toString(): String {
            return "cast.id=$id cast.name=$name | "
        }
    }

    inner class Avatars {
        var small: String? = null
        var medium: String? = null
        var large: String? = null
    }
}