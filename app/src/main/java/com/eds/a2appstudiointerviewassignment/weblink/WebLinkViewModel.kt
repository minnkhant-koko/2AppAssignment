package com.eds.a2appstudiointerviewassignment.weblink

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eds.a2appstudiointerviewassignment.model.WebLink
import com.eds.a2appstudiointerviewassignment.repository.WebLinkRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WebLinkViewModel @Inject constructor(private val repository: WebLinkRepositoryImpl) : ViewModel(){

    private var sorting: Sorting? = null

    private val _list = MutableLiveData<List<WebLink>>()
    val list : LiveData<List<WebLink>>
        get() = _list

    fun setItems(list: List<WebLink>) {
        _list.postValue(list)
    }

    suspend fun processWebLinkUrl(url: String): WebLink? =
        repository.getWebLink(url)

    fun addItem(webLink: WebLink) {
        val oldList = _list.value
        if (oldList != null && oldList.contains(webLink)) return
        val newList: MutableList<WebLink> = oldList?.toMutableList() ?: mutableListOf()
        newList.add(webLink)
        setItems(newList)
    }

    fun delete(webLink: WebLink) {
        val oldList = _list.value
        val newList: MutableList<WebLink> = oldList?.toMutableList() ?: mutableListOf()
        newList.remove(webLink)
        setItems(newList)
    }

    fun shuffle() {
        // reset the sorting since shuffle would change the order
        sorting = null
        val oldList = _list.value
        val newList: List<WebLink> = oldList?.toMutableList() ?: ArrayList<WebLink>()
        val list = newList.shuffled()
        setItems(list)
    }

    fun sort() {
        val oldList = _list.value
        val newList: List<WebLink> = oldList?.let { ArrayList(it) } ?: ArrayList<WebLink>()
        val list: List<WebLink>
        if (sorting == null || sorting == Sorting.DESC) {
            list = newList.sortedBy { webLink -> webLink.title }
            sorting = Sorting.ASC
        } else {
            list = newList.sortedByDescending { webLink -> webLink.title }
            sorting = Sorting.DESC
        }
        setItems(list)
    }

    enum class Sorting {
        ASC, DESC
    }
}