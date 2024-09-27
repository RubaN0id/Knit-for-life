package ru.knitforlife.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.kotlin.whenever
import ru.knitforlife.api.ColorApi
import ru.knitforlife.api.ColorIdResponse
import ru.knitforlife.api.Hex
import ru.knitforlife.api.Name
import ru.knitforlife.api.RGB
import ru.knitforlife.model.Color


@ExperimentalCoroutinesApi
class ColorsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    val api: ColorApi = mock()


    private val testDispatcher = UnconfinedTestDispatcher()

    private val view: ColorsViewModel = ColorsViewModel(api)

//    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()

    }

    @Test
    fun load() {
        runTest {
            view.load()
            Assert.assertEquals(10, view.colors.value?.size)
        }
    }

    @Test
    fun add() {
        runTest  {
            val colorIdResponse: ColorIdResponse = ColorIdResponse(
                hex = Hex("FFFFFF", "FFFFFF"),
                name = Name("Toast"),
                rgb = RGB(255, 255, 255)
            )

            whenever(api.getColorName(any())).thenReturn(colorIdResponse)

            view.add(color = Color("1", "", 255, 255, 255))

            Assert.assertEquals(1, view.colors.value?.size)
            Assert.assertEquals("Toast", view.colors.value?.get(0)?.name)
            verify(api, times(1)).getColorName(any())
        }
    }
}