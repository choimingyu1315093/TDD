package com.example.tdd.model.area

class AreaRepositoryImpl(
    private val api: AreaApiService,
    private val mapper: AreaMapper
): AreaRepository {
    override suspend fun getAreaCode(os: String, app: String, serviceKey: String): List<Area> = emptyList()
}