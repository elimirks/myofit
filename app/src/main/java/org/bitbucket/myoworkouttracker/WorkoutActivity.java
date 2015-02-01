package org.bitbucket.myoworkouttracker;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

import android.widget.Toast;
import android.widget.TextView;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Vector3;

import java.util.List;
import java.util.ArrayList;

import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_workout)
public class WorkoutActivity extends ActionBarActivity {
    private SemiCircleDrawable semiCircleDrawable =
            new SemiCircleDrawable(0xffff0000, 0x22000000, 0xffffffff, 0);

    private int totalRepCount = 8;
    private boolean active = true;
    protected int timer = 0;
    protected GestureDetector mGestureDetector = new CurlDetector(totalRepCount);
    protected ArrayList<Rep> repList = new ArrayList<Rep>();
    protected Rep currentRepObj = new Rep(1, 1, System.currentTimeMillis());
    protected final long startTime = System.currentTimeMillis();

    @ViewById
    TextView repCounterText, setCounterText;

    @AfterViews
    void setupCircleIndicator() {
        repCounterText.setBackgroundDrawable(semiCircleDrawable);
    }

    private DeviceListener mListener = new AbstractDeviceListener() {

        @Override
        public void onAccelerometerData(final Myo myo, long timestamp, Vector3 accel) {
            if (active) {
                mGestureDetector.onData(timestamp, accel, myo);

                long time = System.currentTimeMillis();
                int cReps = currentRepObj.getRepNum();
                int cSets = currentRepObj.getSetNum();
                int mReps = mGestureDetector.currentReps;
                int mSets = mGestureDetector.currentSets;

                if (mSets == cSets - 1 && mReps == cReps - 1) {
                    currentRepObj.addX(time, accel.x());
                    currentRepObj.addZ(time, accel.z());
                    Log.v("REP", "stamp - " + time + " " + currentRepObj.getSetNum() + " " + currentRepObj.getSetNum() + " " + accel.x() + " " + accel.z());
                }
                else {
                    currentRepObj.setDuration(time - startTime);
                    currentRepObj.setEndTime(time);
                    repList.add(currentRepObj);
                    currentRepObj = new Rep(mSets + 1, mReps + 1, time);
                }

                if (mReps >= totalRepCount) {

                    myo.vibrate(Myo.VibrationType.LONG);
                    myo.vibrate(Myo.VibrationType.LONG);

                    mGestureDetector.currentReps = 0;
                    mGestureDetector.currentSets++;
                    setCounterText.setText("sets completed: " + mSets);

                    updateUI();

                    active = false;

                    semiCircleDrawable.setRimColor(0xff0000ff);

                    new CountDownTimer(10000, 1) {

                        public void onTick(long millisUntilFinished) {
                            timer = (int) (millisUntilFinished / 1000);

                            repCounterText.setText(Integer.toString(timer) + "s");
                            float percentComplete = (float) (millisUntilFinished) / 10000;
                            int angle = (int) (percentComplete * 360);
                            semiCircleDrawable.setAngle(angle);
                        }

                        public void onFinish() {
                            active = true;
                            semiCircleDrawable.setRimColor(0xffff0000);
                            myo.vibrate(Myo.VibrationType.LONG);
                        }

                    }.start();
                } else {
                    updateUI();
                }
            }

        }
    };

    @UiThread
    void updateUI() {
            int currentRepCount = mGestureDetector.currentReps;
            int currentSetCount = mGestureDetector.currentSets;
            repCounterText.setText(Integer.toString(currentRepCount));
            setCounterText.setText("sets completed: " + currentSetCount);
            float percentComplete = (float) (currentRepCount + mGestureDetector.getPercentX()) / totalRepCount;
            int angle = (int) (percentComplete * 360);
            semiCircleDrawable.setAngle(angle);
            semiCircleDrawable.setCenterColor(generateAccuracyColor());
    }

    private int generateAccuracyColor() {
        double accuracy = Math.pow(mGestureDetector.getError(), 2);
        int intensity = 0x77;
        int other = (int) (0xff - intensity * accuracy);
        return 0xffff0000 | (other << 8) | other;
    }

    @AfterViews
    void startMyo() {
        Hub.getInstance().addListener(mListener);
    }

    @Click
    void doneButton() {
        Hub.getInstance().removeListener(mListener);
        Intent intent = new Intent(WorkoutActivity.this, StatsActivity_.class);
        intent.putExtra("repList", repList);
        startActivity(intent);
    }
}

