package com.penelope.faunafinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

public class AboutUsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Toggle hide title + display back arrow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button licensingBtn = findViewById(R.id.licensing_btn);
        licensingBtn.setOnClickListener(view -> {
            // When the user clicks the button to see the licenses:
            startActivity(new Intent(view.getContext(), OssLicensesMenuActivity.class));
        });

        Button usageBtn = findViewById(R.id.usage_btn);
        usageBtn.setOnClickListener(view -> {
            // When the user clicks the button to see 'how to use the app' page:
            Intent introIntent = new Intent(view.getContext(), HowTo.class);
            introIntent.putExtra(getString(R.string.runIntro), true);
            startActivity(introIntent);
        });

        Button creatorsBtn = findViewById(R.id.creators_btn);
        creatorsBtn.setOnClickListener(view -> {
            // When the user clicks button to see the 'about the creators' page:
            startActivity(new Intent(view.getContext(), AboutUsCreatorsActivity.class));
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
