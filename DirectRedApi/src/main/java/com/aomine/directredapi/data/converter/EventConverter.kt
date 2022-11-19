package com.aomine.directredapi.data.converter

import android.os.Build
import com.aomine.directredapi.data.model.SportEvent
import com.aomine.directredapi.data.model.SportListResponse
import com.aomine.directredapi.data.model.api.EventsResponse
import com.aomine.directredapi.data.model.api.MapParams
import com.aomine.directredapi.helper.RedCoroutinesHelper.apmap
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.net.URLEncoder

object EventConverter {

    private const val tableItemCSS = "table>tbody>tr"

    fun Element.convertInEvents():List<SportEvent> = select(tableItemCSS).apmap{
        val time = it.selectFirst("span")?.text()

        val nameElement = it.select("td")[2]

        val url = nameElement?.selectFirst("a")?.attr("href")?.correctUrl()

        val name = nameElement.selectFirst("b")?.text()

        val competitionName = nameElement.text().split(":").first()

        SportEvent(time, competitionName, name, url)
    }.toList()

    fun Document.convertInEvents():List<SportEvent> = select(tableItemCSS).apmap{

            val time = it.selectFirst("span")?.text()

            val nameElement = it.select("td")[2]

            val url = nameElement?.selectFirst("a")?.attr("href")?.correctUrl()

            val name = nameElement.selectFirst("b")?.text()

            val competitionName = nameElement.text().split(":").first()

            SportEvent(time, competitionName, name, url)
        }.toList().distinctBy { it.matchName }

    private fun String.correctUrl():String{
        if(startsWith("//")){
            return "https:$this"
        }

        return this
    }

    fun SportListResponse.removeInvalids(dbResponse: EventsResponse): SportListResponse {
        val newList = list?.toMutableList()

        dbResponse.events?.let { events ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                newList?.removeIf {
                    !events.contains(it.matchName)
                }
            } else {
               val toRemove = mutableListOf<SportEvent>()

                newList?.forEach {
                    if(!events.contains(it.matchName)){
                        toRemove.add(it)
                    }
                }

                newList?.removeAll(toRemove)
            }
        } ?: run {
            newList?.removeAll{
                true
            }
        }
        return SportListResponse(statusCode, errorMessage, newList)
    }

    fun List<MapParams>.formatPostToWebView():String{
        var postData = ""
        forEach {
            postData += "${it.key}=${URLEncoder.encode(it.value, "UTF-8")}&"
        }
        return postData
    }
}