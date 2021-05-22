package de.logerbyte.moneyminder.entities.mapper

interface BaseMapper<FROM, TO> {
    fun map(from: FROM): TO
}
