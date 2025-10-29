package com.example.tdd.model.area

import com.example.tdd.model.common.AppException
import com.example.tdd.model.common.ErrorHandler
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals
import kotlin.test.fail

//통합테스트(Retrofit + Gson + 가짜 네트워크)
class AreaRepositoryIntegrationTest {
    private lateinit var server: MockWebServer
    private lateinit var api: AreaApiService
    private val mapper = AreaMapper()
    private val errorHandler = ErrorHandler()

    @Before
    fun setup(){
        server = MockWebServer().apply { start() }

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(AreaApiService::class.java)
    }

    @Test
    fun `200 응답 - Success`() = runTest {
        val json = """
            {
              "response": {
                "header": {"resultCode":"0000","resultMsg":"OK"},
                "body": {
                  "items": {
                    "item": [
                      {"code": "1", "name": "서울", "rnum": 1},
                      {"code": "2", "name": "부산", "rnum": 2}
                    ]
                  },
                  "numOfRows": 10,
                  "pageNo": 1,
                  "totalCount": 2
                }
              }
            }
        """.trimIndent()

        server.enqueue(MockResponse().setResponseCode(200).setBody(json))

        val repo = AreaRepositoryImpl(api, mapper, errorHandler)
        val result = repo.getAreaCode("AND", "APP", "SERVICEKEY")
        assertEquals(listOf(Area(1, "서울"), Area(2, "부산")), result)
    }

    @Test
    fun `500 응답 - HttpException 발생시 AppException 으로 래핑`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("{}"))

        val repo = AreaRepositoryImpl(api, mapper, errorHandler)

        try {
            repo.getAreaCode(os = "AND", app = "APP", serviceKey = "SERVICEKEY")
        } catch (e: AppException) {
            assertEquals("Server Error 500", e.message)
        }
    }

    @After
    fun tearDown(){
        server.shutdown()
    }
}