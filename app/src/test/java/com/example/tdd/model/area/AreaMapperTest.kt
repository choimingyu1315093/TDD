package com.example.tdd.model.area

import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertEquals

//단위 테스트
class AreaMapperTest {

    private val mapper = AreaMapper()

    @Test
    fun `items가 null이면 빈 리스트`(){
        val dto = AreaDto(
            response = AreaDto.Response(
                body = null,
                header = null
            )
        )

        val result = mapper.dtoToList(dto)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `code와 name이 제대로 들어 왔을 때, Area로 변환`(){
        val dto = AreaDto(
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

        val result = mapper.dtoToList(dto)
        assertEquals(listOf(Area(1, "서울"), Area(2, "부산")), result)
    }

    @Test
    fun `code가 숫자가 아니고 name이 비어있을 때, Area로 변환`(){
        val dto = AreaDto(
            response = AreaDto.Response(
                body = AreaDto.Response.Body(
                    items = AreaDto.Response.Body.Items(
                        item = listOf(
                            AreaDto.Response.Body.Items.Item(
                                code = "A",
                                name = "서울"
                            ),
                            AreaDto.Response.Body.Items.Item(
                                code = "2",
                                name = ""
                            ),
                            AreaDto.Response.Body.Items.Item(
                                code = "3",
                                name = "대구"
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

        val result = mapper.dtoToList(dto)
        assertEquals(listOf(Area(3, "대구")), result)
        assertEquals(1, result.size)
    }
}