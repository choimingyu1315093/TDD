package com.example.tdd.viewmodel.area

import app.cash.turbine.test
import com.example.tdd.model.area.Area
import com.example.tdd.model.area.AreaRepository
import com.example.tdd.model.common.ErrorHandler
import com.example.tdd.ui.common.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals

//private val result: Result<List<Area>> 외부에서 결과를 미리 넣어줄 수 있게 만든 변수.
class FakeRepoImpl(private val result: Result<List<Area>>): AreaRepository{
    override suspend fun getAreaCode(
        os: String,
        app: String,
        serviceKey: String
    ): List<Area> {
        return result.getOrThrow() //성공이면 데이터를 가져오고 실패하면 예외처리
    }
}

class AreaViewModelTest {
    private val dispatcher = StandardTestDispatcher() //코루틴 순서를 직접 제어하기 위한 리모컨

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `refresh 호출 시, Loading 다음 Success`() = runTest {
        val repo = FakeRepoImpl(Result.success(listOf(Area(1, "서울"))))
        val errorHandler = ErrorHandler()
        val viewModel = AreaViewModel(repo, errorHandler)

        viewModel.state.test {
            assertEquals(ResourceState.Loading, awaitItem()) //Loading

            viewModel.refresh()

            advanceUntilIdle() //모든 비동기 작업이 끝날 때까지 진행 시켜!!(리모컨 실행 버튼)

            val success = awaitItem() as ResourceState.Success //Success
            assertEquals(listOf(Area(1, "서울")), success.data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `refresh 호출 시, Loading 다음 Error`() = runTest {
        val repo = FakeRepoImpl(Result.failure(IOException("network error")))
        val errorHandler = ErrorHandler()
        val viewModel = AreaViewModel(repo, errorHandler)

        viewModel.state.test {
            assertEquals(ResourceState.Loading, awaitItem())

            viewModel.refresh()

            advanceUntilIdle()

            val error = awaitItem() as ResourceState.Error
            assertEquals("Network Error", error.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}