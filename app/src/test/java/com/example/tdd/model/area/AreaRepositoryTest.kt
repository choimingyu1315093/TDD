package com.example.tdd.model.area

import android.util.Log
import com.example.tdd.model.common.ErrorHandler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FakeAreaApiService: AreaApiService {
    override suspend fun getAreaCode(
        mobileOS: String,
        mobileApp: String,
        type: String,
        serviceKey: String
    ): AreaDto {
        return AreaDto(
            response = AreaDto.Response(
                body = AreaDto.Response.Body(
                    items = AreaDto.Response.Body.Items(
                        item = listOf(
                            AreaDto.Response.Body.Items.Item(
                                code = "1",
                                name = "서울"
                            ),
                            AreaDto.Response.Body.Items.Item(
                                code = "2",
                                name = "부산"
                            )
                        )
                    ),
                    numOfRows = null,
                    pageNo = null,
                    totalCount = null
                ),
                header = null
            )
        )
    }

}

class AreaRepositoryTest {

    val api = FakeAreaApiService()
    val mapper = AreaMapper()
    val errorHandler = ErrorHandler()
    val repo = AreaRepositoryImpl(api, mapper, errorHandler)

    @Test
    fun `성공 테스트`() = runTest { //runTest는 코루틴을 테스트하기 위한 전용 함수다.
        val result = repo.getAreaCode("AND", "APP", "ServiceKey")
        assertEquals(listOf(Area(1, "서울"), Area(2, "부산")), result)
    }
}