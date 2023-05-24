package com.example.core.flow

interface LazyFlowSubjectFactory {

    /**
     * Create a new instance of [LazyFlowSubject]
     * @see DefaultLazyFlowSubjectFactory
     */
    fun <T> create(loader: ValueLoader<T>): LazyFlowSubject<T>

}