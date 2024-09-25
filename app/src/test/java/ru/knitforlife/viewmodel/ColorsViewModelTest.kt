package ru.knitforlife.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class ColorsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    private val view: ColorsViewModel =ColorsViewModel()

//    @Before
//    fun init(){
//        view= ColorsViewModel()
//    }

    @Test
    fun load() {
        runBlocking {
//            whenever(view.load()).thenCallRealMethod()
//            whenever(view.colors).thenCallRealMethod()
//            whenever(view.colors.value).thenCallRealMethod()
            view.load()
            Assert.assertEquals(10, view.colors.value?.size)
        }
    }

    @Test
    fun add() {
    }
}