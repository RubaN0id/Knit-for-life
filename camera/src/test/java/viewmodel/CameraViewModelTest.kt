package viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyArray
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.knitforlife.database.dao.ColorDao
import ru.knitforlife.database.dto.Color
import ru.knitforlife.database.repository.ColorRepository
import ru.knitforlife.network.api.ColorApi
import ru.knitforlife.network.api.ColorIdResponse
import ru.knitforlife.network.api.Hex
import ru.knitforlife.network.api.Name
import ru.knitforlife.network.api.RGB

@ExperimentalCoroutinesApi
class CameraViewModelTest {
    val colorApi:ColorApi = mock()
    val colorDao:ColorDao = mock()
    val colorRepository: ColorRepository = ColorRepository(colorDao)

    private val cameraViewModel: CameraViewModel  = CameraViewModel(colorApi,colorRepository)

    private val testDispatcher = UnconfinedTestDispatcher()
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()

    }

    @Test
    fun save() {
        runTest {

            val colorIdResponse: ColorIdResponse = ColorIdResponse(
                hex = Hex("FFFFFF", "FFFFFF"),
                name = Name("Toast"),
                rgb = RGB(255, 255, 255)
            )

            whenever(colorApi.getColorName(any())).thenReturn(colorIdResponse)
            cameraViewModel.color.value = "#FFFFFF"

            cameraViewModel.save()
            verify(colorApi, times(1)).getColorName(any())
            verify(colorDao, times(1)).insertAll(*anyArray<Color>())
        }
    }

}