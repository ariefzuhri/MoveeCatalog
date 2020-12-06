package com.ariefzuhri.moveecatalog;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.ariefzuhri.moveecatalog.discover.DiscoverFragment;
import com.ariefzuhri.moveecatalog.favorite.FavoriteFragment;
import com.ariefzuhri.moveecatalog.movie.MovieFragment;
import com.ariefzuhri.moveecatalog.tvshow.TVShowFragment;

// ViewPager digunakan untuk pindah antartab dengan swipe
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;

    SectionsPagerAdapter(Context mContext, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContext = mContext;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.movie,
            R.string.tvshow,
            R.string.discover,
            R.string.favorite
    };

    @NonNull
    @Override
    // Menentukan jumlah tab dan menyusun urutan tab
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MovieFragment();
                break;

            case 1:
                fragment = new TVShowFragment();
                break;

            case 2:
                fragment = new DiscoverFragment();
                break;

            case 3:
                fragment = new FavoriteFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    // Memberikan judul pada masing-masing tab
    public CharSequence getPageTitle(int position){
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    // Jumlah tab yang ingin ditampilkan
    public int getCount() {
        return 4;
    }
}
