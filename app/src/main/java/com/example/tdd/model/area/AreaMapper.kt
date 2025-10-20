package com.example.tdd.model.area

class AreaMapper {
    fun dtoToList(areaDto: AreaDto): List<Area> {
        val items = areaDto.response?.body?.items?.item ?: return emptyList()
        //mapNotNull: map하되 결과가 null이면 뺀다.
        return items.mapNotNull { i ->
            val code = i.code?.toIntOrNull() ?: return@mapNotNull null
            val name = i.name?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
            Area(code, name)
        }
    }
}