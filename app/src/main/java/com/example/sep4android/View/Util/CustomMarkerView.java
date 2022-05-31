package com.example.sep4android.View.Util;

import android.content.Context;
import android.widget.TextView;

import com.example.sep4android.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomMarkerView extends MarkerView {

    private TextView tvContent;
    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM hh:mm", Locale.ENGLISH);
        tvContent.setText(mFormat.format(new Date((long) e.getX())) +": " +e.getY());
        super.refreshContent(e,highlight);
    }

    private MPPointF mOffset;
    @Override
    public MPPointF getOffset() {
        if(mOffset==null){
            mOffset = new MPPointF(-(getWidth()/2),-getHeight());
        }
        return mOffset;
    }
}
