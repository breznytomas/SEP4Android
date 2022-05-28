package com.example.sep4android.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.example.sep4android.Fragments.Helper.ImageViewPreference;
import com.example.sep4android.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private ImageViewPreference imageViewPreference;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        /* TODO add onClick events */
        imageViewPreference = (ImageViewPreference) findPreference("image_preference");
        if (imageViewPreference != null)
            imageViewPreference.setImageClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity) v.getContext();
                    activity.finish();
                    activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
    }
}
