package ru.easycode.zerotoheroandroidtdd.data

import ru.easycode.zerotoheroandroidtdd.presentation.core.Now

interface Repository {

    interface Change: Delete {
        fun update(id: Long, newText: String)
    }

    interface Read {
        fun list(): List<Item>
    }

    interface Add {
        fun add(value: String): Long
    }

    interface Delete: Get {
        fun delete(id: Long)
    }

    interface Get {
        fun item(id: Long): Item
    }

    interface Mutable: Read, Add

    interface All: Mutable, Change

    class Base(private val dataSource: ItemsDao, private val now: Now) : All {
        override fun list(): List<Item> =
            dataSource.list().map { Item(it.id, it.text) }

        override fun add(value: String): Long {
            val time = now.nowMillis()
            dataSource.add(ItemCache(time, value))
            return time
        }

        override fun delete(id: Long) {
            dataSource.delete(id)
        }

        override fun item(id: Long): Item = Item(id, dataSource.item(id).text)
        override fun update(id: Long, newText: String) {
            dataSource.update(ItemCache(id, newText))
        }
    }
}