package de.logerbyte.moneyminder.mapper

interface BaseMapper<FROM, TO> {
    fun map(from: FROM): TO
}
