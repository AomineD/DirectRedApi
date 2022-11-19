package com.aomine.directred

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aomine.directredapi.RedDirect
import com.aomine.directredapi.data.model.SportEvent
import com.aomine.directredapi.helper.RedCoroutinesHelper.ioSafe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.runInterruptible

const val TAG = "MAIN"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RedDirect.instance.loadSportEvents { response ->
            if(response.statusCode == 200){
                Log.e(TAG, "onCreate: RESPONSE!" )
                response.list?.forEach {
                    Log.e(TAG, "onCreate: ${it.matchName}" )
                }
                loadUrlFromApi(response.list?.last())
            }
        }
    }

    private fun loadUrlFromApi(get: SportEvent?) {
        Log.e(TAG, "loadUrlFromApi: loading url from api ${get!=null}" )



        RedDirect.instance.loadPlayersFromApi {
            Log.e(TAG, "players: ${it.players?.size}" )
        }


        ioSafe {
            delay(5000)
            get?.let {  sport ->
                Log.e(TAG, "matchName to load: ${get.matchName}" )
                RedDirect.instance.loadEventFromApi(sport){
                    Log.e(TAG, "message: ${it.status?.name}" )
                }
            }
        }

    }
}