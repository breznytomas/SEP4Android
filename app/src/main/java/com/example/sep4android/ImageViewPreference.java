package com.example.sep4android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

public class ImageViewPreference extends Preference {

    private ImageView imageView;
    View.OnClickListener imageClickListener;

    public ImageViewPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //onBindViewHolder() will be called after we call setImageClickListener() from SettingsFragment
    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        imageView = (ImageView) holder.findViewById(R.id.back_button_settings);
        imageView.setOnClickListener(imageClickListener);
    }

    public void setImageClickListener(View.OnClickListener onClickListener) {
        imageClickListener = onClickListener;
    }
}
