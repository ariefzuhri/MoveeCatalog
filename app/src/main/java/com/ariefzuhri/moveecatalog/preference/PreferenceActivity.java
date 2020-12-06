package com.ariefzuhri.moveecatalog.preference;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ariefzuhri.moveecatalog.R;

public class PreferenceActivity extends AppCompatActivity {
    public static final int REQUEST_CHANGE_LANGUAGE = 100;
    public static final int RESULT_CHANGE_LANGUAGE = 101;
    public static final String EXTRA_CHANGE_LANGUAGE = "extra_change_language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        getSupportFragmentManager().beginTransaction().add(R.id.setting_holder, new PreferenceFragment()).commit();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.setting));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
