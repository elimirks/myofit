package org.bitbucket.myoworkouttracker;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.content.Intent;
import android.content.Context;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import android.graphics.Color;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.androidannotations.annotations.*;

import lecho.lib.hellocharts.view.LineChartView;

@EActivity(R.layout.activity_stats)
public class StatsActivity extends ActionBarActivity {
    private List<Rep> reps;

    @ViewById
    ListView repList;

    @AfterViews
    void setListAdapter() {
        reps = (ArrayList<Rep>) getIntent().getSerializableExtra("repList");
        RepListAdapter adapter = new RepListAdapter(reps);
        repList.setAdapter(adapter);
    }

    @ItemClick
    public void repList(int position) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PopupWindow pw = new PopupWindow(
                inflater.inflate(R.layout.popup_info, null, false),
                (int) (width * .8),
                (int) (height * .8),
                true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOutsideTouchable(true);
        pw.showAtLocation(this.findViewById(R.id.stats), Gravity.CENTER, 0, 0);

        TextView rn = (TextView) pw.getContentView().findViewById(R.id.repNum);
        TextView sn = (TextView) pw.getContentView().findViewById(R.id.setNum);
        TextView d = (TextView)  pw.getContentView().findViewById(R.id.duration);

        rn.setText("Rep: " + reps.get(position).getRepNum());
        sn.setText("Set: " + reps.get(position).getSetNum());
        d.setText(reps.get(position).getTimeString());

        Log.v("CLICK", "click - " + position + " " + reps.get(position).getTime() / 1000);
    }
}


class RepListAdapter extends BaseAdapter {
    List<Rep> reps;

    public RepListAdapter(List<Rep> reps) {
        this.reps = reps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RepItemView repView = convertView == null
                ? RepItemView_.build(parent.getContext())
                : (RepItemView) convertView;

        repView.bind(getItem(position));
        return repView;
    }

    @Override
    public int getCount() {
        return reps.size();
    }

    @Override
    public Rep getItem(int position) {
        return reps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}


@EViewGroup(R.layout.rep_item)
class RepItemView extends RelativeLayout {
    @ViewById
    TextView timeText, repText, setText;

    public RepItemView(Context context) {
        super(context);
    }

    public void bind(Rep rep) {
        timeText.setText(rep.getDurationString());
        repText.setText(Integer.toString(rep.getRepNum()));
        setText.setText(Integer.toString(rep.getSetNum()));
    }
}

