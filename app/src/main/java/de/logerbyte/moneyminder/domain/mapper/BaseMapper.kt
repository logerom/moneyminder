package de.logerbyte.moneyminder.domain.mapper

interface BaseMapper<FROM, TO> {
    fun map(from: FROM): TO
}
