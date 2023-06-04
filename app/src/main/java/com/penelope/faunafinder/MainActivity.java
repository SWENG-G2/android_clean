package com.penelope.faunafinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    private SwipeRefreshLayout swipeRefreshLayout;
    private AlertDialog.Builder alertBuilder;

    /**
     * Fetches the birds for the selected campus.
     */
    private void fetchBirds(AlertDialog.Builder alertBuilder) {
        RequestMaker requestMaker = new RequestMaker(getApplicationContext());

        String birdsUrl = testingUrl != null ? testingUrl :
                getString(R.string.serverURL) + String.format(Locale.UK, getString(R.string.birdsList), campusId);
        // Network request
        requestMaker.query(birdsUrl, new Result() {
            @Override
            public void onSuccess(String response) {
                ListItemClickAction listItemClickAction = id -> {
                    Intent birdIntent = new Intent(getApplicationContext(), BirdActivity.class);
                    birdIntent.putExtra("birdId", id);
                    startActivity(birdIntent);
                };
                slidesRecyclerViewAdapter = UIUtils.populateList(response, mainActivity,
                        SlideFactory.BIRD_SLIDE, listItemClickAction, 5,
                        new PresentationParser());

                // Stop refresh animation
                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(VolleyError volleyError) {
                UIUtils.networkProblem(mainActivity, alertBuilder).show();
                volleyError.printStackTrace();

                // Stop refresh animation
                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.setRefreshing(false);
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

        // Initialise dialogue builder
        alertBuilder = new AlertDialog.Builder(this);

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

        // Search functionality
        SearchView searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        searchView.setOnClickListener(v -> searchView.setIconified(false));

        // Set refresh listener
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (campusId != NO_CAMPUS_SELECTED) {
                fetchBirds(alertBuilder);
            } else
                swipeRefreshLayout.setRefreshing(false);
        });
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.bordeaux));

        if (campusId == NO_CAMPUS_SELECTED) {
            // Show select location hint
            findViewById(R.id.select_location_tv).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int newCampusId = sharedPreferences.getInt(getString(R.string.campusId), NO_CAMPUS_SELECTED);
        if (newCampusId != campusId) {
            campusId = newCampusId;
        }

        if (campusId != NO_CAMPUS_SELECTED) {
            // Hide select location hint
            findViewById(R.id.select_location_tv).setVisibility(View.GONE);
            // Show loading spinner
            if (swipeRefreshLayout != null)
                swipeRefreshLayout.setRefreshing(true);
            fetchBirds(alertBuilder);
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
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            Intent chooseLocationIntent = new Intent(this, CampusSelectionActivity.class);
            startActivity(chooseLocationIntent);
        } else if (itemId == R.id.action_about) { // About button
            Intent chooseLocationIntent = new Intent(this, AboutUsActivity.class);
            startActivity(chooseLocationIntent);
        } else if (campusId != NO_CAMPUS_SELECTED) { // Other possibility is only refresh
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(true);
            }
            fetchBirds(alertBuilder);
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