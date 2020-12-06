package com.ariefzuhri.moveecatalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.ariefzuhri.moveecatalog.preference.PreferenceActivity;
import com.google.android.material.tabs.TabLayout;
import java.util.Locale;
import static com.ariefzuhri.moveecatalog.preference.PreferenceActivity.EXTRA_CHANGE_LANGUAGE;
import static com.ariefzuhri.moveecatalog.preference.PreferenceActivity.REQUEST_CHANGE_LANGUAGE;
import static com.ariefzuhri.moveecatalog.preference.PreferenceActivity.RESULT_CHANGE_LANGUAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLanguage();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(sectionsPagerAdapter.getCount() - 1); // min 1
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0); // Menghilangkan shadow di bawah action bar
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHANGE_LANGUAGE){
            if (resultCode == RESULT_CHANGE_LANGUAGE){
                if (data != null){
                    boolean changeLanguage = data.getBooleanExtra(EXTRA_CHANGE_LANGUAGE, false);
                    if (changeLanguage){
                        recreate();
                        openSettings();
                    }
                }
            }
        }
    }

    public void loadLanguage(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String language = sp.getString(getResources().getString(R.string.key_language), getResources().getStringArray(R.array.language)[0]);

        if (language.equals(getResources().getStringArray(R.array.language)[0])){
            changeLocale(getResources().getStringArray(R.array.language_code)[0]);
        } else if (language.equals( getResources().getStringArray(R.array.language)[1])){
            changeLocale(getResources().getStringArray(R.array.language_code)[1]);
        } else if (language.equals( getResources().getStringArray(R.array.language)[2])) {
            changeLocale(getResources().getStringArray(R.array.language_code)[2]);
        }
    }

    private void changeLocale(String languageCode){
        if (languageCode.equals(getResources().getStringArray(R.array.language)[0]))
            languageCode = Locale.getDefault().getDisplayLanguage();

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    /**
     * Bagian menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_setting) {
            openSettings();
        }

        return true;
    }

    private void openSettings() {
        Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
        startActivityForResult(intent, REQUEST_CHANGE_LANGUAGE);
    }
}