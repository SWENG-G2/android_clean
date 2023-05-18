package com.penelope.faunafinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.penelope.faunafinder.network.RequestMaker;
import com.penelope.faunafinder.network.Result;
import com.penelope.faunafinder.presentation.SlidesRecyclerViewAdapter;
import com.penelope.faunafinder.utils.ListItemClickAction;
import com.penelope.faunafinder.utils.UIUtils;
import com.penelope.faunafinder.xml.PresentationParser;
import com.penelope.faunafinder.xml.slide.SlideFactory;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {

    private static final int NO_CAMPUS_SELECTED = -1;

    private int campusId;
    private SharedPreferences sharedPreferences;
    private ConstraintLayout mainActivity;
    private SlidesRecyclerViewAdapter slidesRecyclerViewAdapter;
    private MainActivityLifecycleObserver mainActivityLifecycleObserver;
    private String testingUrl = null;

    /**
     * Fetches the birds for the selected campus.
     */
    private void fetchBirds() {
        RequestMaker requestMaker = new RequestMaker(getApplicationContext());

        String birdsUrl = testingUrl != null ? testingUrl :
                getString(R.string.serverURL) + String.format(Locale.UK, getString(R.string.birdsList), campusId);
        // Network request
        requestMaker.query(birdsUrl, new Result() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                ListItemClickAction listItemClickAction = id -> {
                    Intent birdIntent = new Intent(getApplicationContext(), BirdActivity.class);
                    birdIntent.putExtra("birdId", id);
                    startActivity(birdIntent);
                };
                slidesRecyclerViewAdapter = UIUtils.populateList(response, mainActivity,
                        SlideFactory.BIRD_SLIDE, listItemClickAction, 5,
                        new PresentationParser());
                // Hide progress loading spinner
                findViewById(R.id.loading).setVisibility(View.GONE);
            }

            @Override
            public void onError(VolleyError volleyError) {
                System.out.println(volleyError.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab testing url if injected
        testingUrl = getIntent().getStringExtra(getString(R.string.testingUrl));

        mainActivity = findViewById(R.id.main_activity);

        // Register lifecycle observer
        mainActivityLifecycleObserver = new MainActivityLifecycleObserver(getActivityResultRegistry(), this);
        getLifecycle().addObserver(mainActivityLifecycleObserver);

        // Set up FAB
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        // Set app bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide default title and use change location icon as home
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_twotone_edit_location_alt_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sharedPreferences = getSharedPreferences(getString(R.string.campusConfiguration), Context.MODE_PRIVATE);
        campusId = sharedPreferences.getInt(getString(R.string.campusId), NO_CAMPUS_SELECTED);

        if (campusId != NO_CAMPUS_SELECTED) {
            // Show loading spinner
            findViewById(R.id.loading).setVisibility(View.VISIBLE);
            fetchBirds();
        }
        // Show select location hint
        else findViewById(R.id.select_location_tv).setVisibility(View.VISIBLE);

        // Search functionality
        SearchView searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        searchView.setOnClickListener(v -> searchView.setIconified(false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        int newCampusId = sharedPreferences.getInt(getString(R.string.campusId), NO_CAMPUS_SELECTED);
        if (newCampusId != campusId) {
            campusId = newCampusId;
            // Hide select location hint
            findViewById(R.id.select_location_tv).setVisibility(View.GONE);
            // Show loading spinner
            findViewById(R.id.loading).setVisibility(View.VISIBLE);
            fetchBirds();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Load menu in app bar
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent chooseLocationIntent = new Intent(this, CampusSelectionActivity.class);
            startActivity(chooseLocationIntent);
        } else { // Other possibility can only be the about button
            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            mainActivityLifecycleObserver.loadBirdFromStorage();
        }
    }
}