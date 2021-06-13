package com.eds.a2appstudiointerviewassignment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eds.a2appstudiointerviewassignment.model.WebLink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WebLinkListViewModel extends ViewModel {

    private Sorting sorting = null;
    public WebLinkListViewModel() {}

    private final MutableLiveData<List<WebLink>> _list = new MutableLiveData<>();

    public void setItems(List<WebLink> list) {
        _list.postValue(list);
    }

    public void addItem(WebLink webLink){
        List<WebLink> oldList = _list.getValue();

        if (oldList != null && oldList.contains(webLink)) {
            return;
        }

        List<WebLink> newList = oldList != null ? new ArrayList<>(oldList) : new ArrayList<>();
        newList.add(webLink);
        setItems(newList);
    }

    public void delete(WebLink webLink) {
        List<WebLink> oldList = _list.getValue();
        List<WebLink> newList = oldList != null ? new ArrayList<>(oldList) : new ArrayList<>();
        newList.remove(webLink);
        setItems(newList);
    }

    public void shuffle() {
        // reset the sorting since shuffle would change the order
        sorting = null;
        List<WebLink> oldList = _list.getValue();
        List<WebLink> newList = oldList != null ? new ArrayList<>(oldList) : new ArrayList<>();
        Collections.shuffle(newList);
        setItems(newList);
    }

    public void sort() {
        List<WebLink> oldList = _list.getValue();
        List<WebLink> newList = oldList != null ? new ArrayList<>(oldList) : new ArrayList<>();
        if (sorting == null || sorting == Sorting.DESC) {
            newList.sort((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
            sorting = Sorting.ASC;
        } else {
            newList.sort((o1, o2) -> o2.getTitle().compareTo(o1.getTitle()));
            sorting = Sorting.DESC;
        }
        setItems(newList);
    }

    public LiveData<List<WebLink>> getItems() {
        return _list;
    }

    enum Sorting {
        ASC, DESC
    }
}
