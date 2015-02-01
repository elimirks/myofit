package org.bitbucket.myoworkouttracker;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import org.bitbucket.myoworkouttracker.GestureDetector;

import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;

import org.androidannotations.annotations.*;

@NoTitle
@WindowFeature({ Window.FEATURE_NO_TITLE, Window.FEATURE_INDETERMINATE_PROGRESS })
@Fullscreen
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    private DeviceListener mListener = new AbstractDeviceListener() {
        // onConnect() is called whenever a Myo has been connected.
        @Override
        public void onConnect(Myo myo, long timestamp) {
            WorkoutActivity_.intent(MainActivity.this).start();
        }
    };

    @AfterViews
    void startMyo() {
        Hub hub = Hub.getInstance();
        if (hub.init(this)) {
            Log.d("ACCEL", "Initialized Myo");
            // TODO handle connection errors.
            hub.attachToAdjacentMyo();
            hub.addListener(mListener);
        } else {
            Log.d("NAO", "Could not initialize the Hub.");
        }
    }



}

