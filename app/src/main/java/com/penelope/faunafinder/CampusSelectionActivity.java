package com.penelope.faunafinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.penelope.faunafinder.network.RequestMaker;
import com.penelope.faunafinder.network.Result;
import com.penelope.faunafinder.presentation.SlidesRecyclerViewAdapter;
import com.penelope.faunafinder.utils.ListItemClickAction;
import com.penelope.faunafinder.utils.UIUtils;
import com.penelope.faunafinder.xml.slide.SlideFactory;


public class CampusSelectionActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SharedPreferences sharedPreferences;
    private ConstraintLayout campusSelectionActivity;
    private SlidesRecyclerViewAdapter slidesRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_selection);

        campusSelectionActivity = findViewById(R.id.campus_selection_activity);

        sharedPreferences = getSharedPreferences(getString(R.string.campusConfiguration), Context.MODE_PRIVATE);

        // Set app bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide default title and display back arrow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RequestMaker requestMaker = new RequestMaker(getApplicationContext());

        // Grab testing url if injected
        String testingUrl = getIntent().getStringExtra(getString(R.string.testingUrl));

        String campusListUrl = testingUrl != null ? testingUrl :
                getString(R.string.serverURL) + getString(R.string.campusesList);

        requestMaker.query(campusListUrl, new Result() {
            @Override
            public void onSuccess(String string) {
                ListItemClickAction listItemClickAction = id -> {
                    sharedPreferences.edit().putInt(getString(R.string.campusId), id).apply();
                    finish();
                };
                slidesRecyclerViewAdapter =
                        UIUtils.populateList(string, campusSelectionActivity,
                                SlideFactory.BASIC_SLIDE, listItemClickAction, 0);
            }

            @Override
            public void onError(VolleyError volleyError) {
                System.out.println(volleyError.getMessage());
            }
        });

        // Search functionality
        SearchView searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        searchView.setOnClickListener(v -> searchView.setIconified(false));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (slidesRecyclerViewAdapter != null)
            slidesRecyclerViewAdapter.getFilter().filter(newText);
        return false;
    }
}