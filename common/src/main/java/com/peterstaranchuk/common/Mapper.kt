package com.peterstaranchuk.common

interface Mapper<FROM, TO> {

    fun map(from: FROM): TO

    fun mapAll(all: List<FROM>): List<TO> {
        return all.map {
            map(it)
        }
    }

}