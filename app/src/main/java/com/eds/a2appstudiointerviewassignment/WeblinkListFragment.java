package com.eds.a2appstudiointerviewassignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eds.a2appstudiointerviewassignment.model.WebLink;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeblinkListFragment extends Fragment {

    public static final String TAG = "WebLink_List";
    private WebLinkListViewModel viewModel;

    public static WeblinkListFragment getFragment() {
        return new WeblinkListFragment();
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weblink_list, container, false);
        view.setTag(TAG);

        viewModel = new ViewModelProvider(requireActivity()).get(WebLinkListViewModel.class);

        WebLinkListAdapter adapter = new WebLinkListAdapter(webLink -> {
            viewModel.delete(webLink);
        });

        RecyclerView recyclerView = view.findViewById(R.id.webLinkRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel.getItems().observe(getViewLifecycleOwner(), webLinks -> adapter.submitList(webLinks));

        return view;
    }
}
