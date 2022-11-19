package com.aomine.directredapi.helper


import android.util.Log
import kotlinx.coroutines.*


object RedCoroutinesHelper {

    /**
     * Launch coroutine in secondary thread
     */
    fun <T> T.ioSafe(work: suspend (CoroutineScope.(T) -> Unit)) : Job {
        val value = this

        return CoroutineScope(Dispatchers.IO).launch{
            try {
                work(value)
            }catch (throwable: Throwable){
                logerror(throwable)
            }
        }
    }

    /**
     * Log error orderly
     */
    private fun logerror(throwable: Throwable) {
        Log.d("Safe", "-------------------------------------------------------------------")
        Log.d("Safe", "safeLaunch: " + throwable.localizedMessage)
        Log.d("Safe", "safeLaunch: " + throwable.message)
        throwable.printStackTrace()
        Log.d("Safe", "-------------------------------------------------------------------")
    }

    /**
     * Launch coroutine in Main Thread
     */
    fun <T> T.main(work: suspend (CoroutineScope.(T) -> Unit)) : Job{
        val value = this

        return CoroutineScope(Dispatchers.Main).launch {
            try {
                work(value)
            }catch (throwable: Throwable){
                logerror(throwable)
            }
        }
    }

    fun <A, B> List<A>.apmap(f: suspend (A) -> B): List<B> = runBlocking {
        map { async { f(it) } }.map { it.await() }
    }
}