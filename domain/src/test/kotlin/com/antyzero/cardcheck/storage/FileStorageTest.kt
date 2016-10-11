package com.antyzero.cardcheck.storage

import org.junit.After

class FileStorageTest : StorageTest(object : StorageFactory<FileStorage>() {
    override fun create(key: String): FileStorage {
        return FileStorage(key)
    }
}) {

    @After
    fun tearDown() {
        (storage as FileStorage).delete()
    }
}