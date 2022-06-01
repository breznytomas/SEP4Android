package com.example.sep4android.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.sep4android.Fragments.Helper.ImageViewPreference;
import com.example.sep4android.R;
import com.example.sep4android.View.AboutUsActivity;
import com.example.sep4android.View.AddEventActivity;
import com.example.sep4android.View.AddBoardActivity;

public class SettingsFragment extends PreferenceFragmentCompat {

    private ImageViewPreference imageViewPreference;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        Preference add_board_preference = findPreference("add_board_preference");
        add_board_preference.setOnPreferenceClickListener(pref -> {
            startActivity(new Intent(getContext(), AddBoardActivity.class));
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return false;
        });

        /* TODO prone to error, be careful while implementing the add method as it needs the boardID */
        Preference add_event_preference = findPreference("add_event_preference");
        add_event_preference.setOnPreferenceClickListener(pref -> {
            startActivity(new Intent(getContext(), AddEventActivity.class));
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return false;
        });

        Preference about_us_preference = findPreference("contact_preference");
        about_us_preference.setOnPreferenceClickListener(pref -> {
            startActivity(new Intent(getContext(), AboutUsActivity.class));
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return false;
        });

        Preference delete_account_preference = findPreference("delete_account_preference");
        delete_account_preference.setOnPreferenceClickListener(pref -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setTitle("Delete Account");
            builder.setMessage("Are you sure that you want to delete your account?!");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        });

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
