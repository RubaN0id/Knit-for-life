package ru.knitforlife.color.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import ru.knitforlife.color.api.ColorApi
import ru.knitforlife.color.model.Color

@ExperimentalCoroutinesApi
class ColorsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    val api: ColorApi = mock()
    val observer: StateFlow<String?> = mock()


    private val testDispatcher = UnconfinedTestDispatcher()
    private val view: ColorsViewModel = ColorsViewModel(api,observer)

//    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()

    }

//    @Test
//    fun load() {
//        runTest {
//            view.load()
//            Assert.assertEquals(10, view.colorFlow.value?.size)
//        }
//    }

    @Test
    fun add() {
        runTest  {
//            val colorIdResponse: ColorIdResponse = ColorIdResponse(
//                hex = Hex("FFFFFF", "FFFFFF"),
//                name = Name("Toast"),
//                rgb = RGB(255, 255, 255)
//            )
//
//            whenever(api.getColorName(any())).thenReturn(colorIdResponse)

            view.add(color = Color("1", "Toast", 255, 255, 255))

            Assert.assertEquals(1, view.colorFlow.value?.size)
            Assert.assertEquals("Toast", view.colorFlow.value?.get(0)?.name)
            verify(api, times(1)).getColorName(any())
        }
    }
}