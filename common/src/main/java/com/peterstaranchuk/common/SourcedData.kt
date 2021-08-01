package com.peterstaranchuk.common

data class SourcedData<T>(val data : T, val dataSource: DataSource)

enum class DataSource {
    CLOUD, CACHE, MEMORY
}