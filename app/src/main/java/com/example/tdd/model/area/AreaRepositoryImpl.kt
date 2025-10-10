package com.example.tdd.model.area

import com.example.tdd.model.common.ErrorHandler

class AreaRepositoryImpl(
    private val api: AreaApiService,
    private val mapper: AreaMapper,
    private val errorHandler: ErrorHandler
): AreaRepository {
    override suspend fun getAreaCode(os: String, app: String, serviceKey: String): List<Area> = emptyList()
}