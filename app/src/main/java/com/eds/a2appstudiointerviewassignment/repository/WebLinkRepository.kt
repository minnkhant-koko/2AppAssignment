package com.eds.a2appstudiointerviewassignment.repository

import com.eds.a2appstudiointerviewassignment.model.WebLink

interface WebLinkRepository {

    suspend fun getWebLink(url: String): WebLink?
}