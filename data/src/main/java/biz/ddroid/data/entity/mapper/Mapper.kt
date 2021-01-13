package biz.ddroid.data.entity.mapper

interface Mapper<S, R> {

    fun map(source: S): R
}