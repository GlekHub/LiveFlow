package com.glekhub.liveflow

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun testFlows() = runBlocking {
        val numbers = 1..10
        //val flow: Flow<Int> = flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val flow: Flow<Int> = numbers.asFlow()

        println("Print only even numbers multiplied by 10: ")
        flow
            .filter { it % 2 == 0 }
            .map { it * 10 }
            .collect {
                println(it)
            }

        println("Print only odd number: ")
        flow
            .filter { it % 2 == 1 }
            .collect {
                println(it)
            }
    }
}