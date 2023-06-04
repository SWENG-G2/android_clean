package com.penelope.faunafinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.penelope.faunafinder.network.RequestMaker;
import com.penelope.faunafinder.network.Result;
import com.penelope.faunafinder.utils.UIUtils;
import com.penelope.faunafinder.xml.PresentationParser;
import com.penelope.faunafinder.xml.slide.SlideFactory;

import java.util.Locale;


public class BirdActivity extends AppCompatActivity {
    private ConstraintLayout birdActivity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AlertDialog.Builder alertBuilder;

    private void setUpAppBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Display back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Hide title
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void populateUIAndContent(String contentXML) {
        UIUtils.populateList(contentXML, birdActivity, SlideFactory.DETAIL_SLIDE,
                null, 0, new PresentationParser());
        setUpAppBar();
    }

    private void fetchBird(int birdId, AlertDialog.Builder alertBuilder) {
        RequestMaker requestMaker = new RequestMaker(getApplicationContext());

        // Grab testing url if injected
        String testingUrl = getIntent().getStringExtra(getString(R.string.testingUrl));

        String birdUrl = testingUrl != null ? testingUrl :
                getString(R.string.serverURL) + String.format(Locale.UK, "bird/%d", birdId);
        requestMaker.query(birdUrl, new Result() {
            @Override
            public void onSuccess(String xml) {
                populateUIAndContent(xml);

                // Stop refresh animation
                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onError(VolleyError volleyError) {
                UIUtils.networkProblem(birdActivity, alertBuilder).show();
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
        setContentView(R.layout.activity_bird);

        int birdId = getIntent().getIntExtra("birdId", -1);
        String birdXML = getIntent().getStringExtra("birdXML");

        if (birdId == -1 && birdXML == null)
            finish();

        birdActivity = findViewById(R.id.bird_activity);

        // Set App bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialise dialogue builder
        alertBuilder = new AlertDialog.Builder(this);

        // Set refresh listener
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchBird(birdId, alertBuilder);
        });
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.bordeaux));

        if (birdXML == null) {
            fetchBird(birdId, alertBuilder);
        } else
            populateUIAndContent(birdXML);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}