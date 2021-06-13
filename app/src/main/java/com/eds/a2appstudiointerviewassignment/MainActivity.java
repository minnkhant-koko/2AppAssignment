package com.eds.a2appstudiointerviewassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.eds.a2appstudiointerviewassignment.model.WebLink;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MainActivity extends AppCompatActivity implements AddWebLinkDialog.AddWebLinkDialogListener{

    private final String TAG = "MainActivity";
    private WebLinkListViewModel viewModel;
    private StatusDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(WebLinkListViewModel.class);
        dialog = new StatusDialog(true, "Parsing the web link");
        dialog.setCancelable(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, WeblinkListFragment.getFragment(), WeblinkListFragment.TAG);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            displayUrlPrompt();
            return true;
        } else if(item.getItemId() == R.id.menu_reshuffle) {
            viewModel.shuffle();
            return true;
        } else if (item.getItemId() == R.id.menu_resort) {
            viewModel.sort();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void displayUrlPrompt() {
        AddWebLinkDialog dialog = new AddWebLinkDialog();
        dialog.show(getSupportFragmentManager(), AddWebLinkDialog.TAG);
    }

    private void displayErrorDialog() {
        StatusDialog dialog = new StatusDialog(false, "Sorry, we can't parse this link");
        dialog.show(getSupportFragmentManager(), StatusDialog.TAG);
    }

    private void displayLoadingDialog() {
        dialog.show(getSupportFragmentManager(), StatusDialog.TAG);
    }

    private void dismissLoadingDialog() {
        dialog.dismiss();
    }

    private class FetchingWebLinks extends AsyncTask<String, Void, WebLink> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            displayLoadingDialog();
        }

        @Override
        protected WebLink doInBackground(String... urls) {
            try {
                String url = urls[0];

                System.setProperty("https.proxyHost", url);
                System.setProperty("https.proxyPort", "8080");
                Document document;
                document = Jsoup.connect(url).get();
                String title = document.title();

                Element imageElement = document.select("img").first();
                String imageUrl = imageElement.absUrl("src");

                return new WebLink(url, title, imageUrl);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(WebLink webLink) {
            super.onPostExecute(webLink);
            dismissLoadingDialog();
            if (webLink == null) {
                displayErrorDialog();
            } else {
                viewModel.addItem(webLink);
            }
        }
    }

    @Override
    public void clickPositiveButton(String url) {
        new FetchingWebLinks().execute(url);
    }
}