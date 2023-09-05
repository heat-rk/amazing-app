package ru.heatalways.amazingasfuckapplication.data.common.cache

/**
 * Utility class for caching data. Use this class in repositories to prevent
 * a lot of requests to server or in other use-cases
 */
class InMemoryCacheContainer<T>(
    /**
     * The life time of cache
     */
    private val cacheLifeTime: Long = CACHE_LIFE_TIME
) {

    /**
     * Cached data
     */
    private var cache: T? = null

    /**
     * The last cache write time
     */
    private var lastUpdateTime = 0L

    /**
     * Returns true, if the cache has been expired.
     */
    val isExpired get() =
        System.currentTimeMillis() - lastUpdateTime >= cacheLifeTime || cache == null

    /**
     * Returns true, if the cache has not been expired.
     */
    val isNotExpired get() = !isExpired

    var value: T?
        get() {
            if (isExpired) cache = null
            return cache
        }
        set(value) {
            lastUpdateTime = System.currentTimeMillis()
            cache = value
        }

    fun getValueOrSave(value: T): T {
        return this.value ?: value.also { this.value = value }
    }

    fun clear() {
        cache = null
    }

    companion object {
        private const val CACHE_LIFE_TIME = 15000L
    }
}