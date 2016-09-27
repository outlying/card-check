package com.antyzero.cardcheck.storage

abstract class StorageFactory<T> where T : Storage {

    abstract fun create(key: String = "DEFAULT"): T


}