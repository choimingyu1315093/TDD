package com.example.tdd.ui.screens.area

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.tdd.model.area.Area
import com.example.tdd.ui.common.ResourceState
import org.junit.Rule
import org.junit.Test
import kotlin.code

class AreaScreenTest {

    @get:Rule
    val composeRule = createComposeRule() //테스트 환경에서 Compose UI를 쓰기 위해 호출해야 한다.

    @Test
    fun `loading표시`(){
        composeRule.setContent {
            AreaListContent(
                state = ResourceState.Loading,
                onRetry = {},
                onItemClick = {}
            )
        }
        composeRule.onNodeWithTag("loading").assertExists()
    }

    @Test
    fun `error표시`(){
        var retried = false

        composeRule.setContent {
            AreaListContent(
                state = ResourceState.Error("Network Error"),
                onRetry = {
                    retried = true
                },
                onItemClick = {}
            )
        }
        composeRule.onNodeWithTag("error").assertExists()
        composeRule.onNodeWithText("다시 시도").performClick()
        assert(retried)
    }

    @Test
    fun `empty표시`(){
        composeRule.setContent {
            AreaListContent(
                state = ResourceState.Success(emptyList()),
                onRetry = {},
                onItemClick = {}
            )
        }
        composeRule.onNodeWithTag("empty").assertExists()
        composeRule.onNodeWithText("데이터가 없습니다").assertExists()
    }

    @Test
    fun `list표시`(){
        val data = listOf(Area(1, "서울"), Area(2, "인천"))
        var item: Area? = null

        composeRule.setContent {
            AreaListContent(
                state = ResourceState.Success(data),
                onRetry = {},
                onItemClick = {item = it}
            )
        }
        composeRule.onNodeWithTag("list").assertExists() // 리스트가 화면에 있는지 확인
        composeRule.onNodeWithTag("row_1").assertExists().performClick() // "서울" 항목 클릭
        assert(item?.code == 1) // 클릭한 결과가 "서울"인지 검사)
    }
}