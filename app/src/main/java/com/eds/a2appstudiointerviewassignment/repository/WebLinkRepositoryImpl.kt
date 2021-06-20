package com.eds.a2appstudiointerviewassignment.repository

import com.eds.a2appstudiointerviewassignment.model.WebLink
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class WebLinkRepositoryImpl @Inject constructor(): WebLinkRepository {

    override suspend fun getWebLink(url: String): WebLink? {
        try {
            System.setProperty("https.proxyHost", url)
            System.setProperty("https.proxyPort", "8080")
            val document : Document
            try {
                document = Jsoup.connect(url).get()
            } catch (e: IOException) {
                return null
            }

            return WebLink(
                    webLink = url,
                    title = document.title(),
                    imageUrl = document.select("img").first().absUrl("src"))
        } catch (e: Exception) {
            return null
        }
    }
}