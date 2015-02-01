package org.bitbucket.myoworkouttracker;

import android.gesture.Gesture;
import android.util.Log;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Vector3;

abstract class GestureDetector {
    protected boolean reachedTop = false;
    protected int currentReps = 0;
    protected int currentSets = 0;
    protected double currentX = 0;
    protected double currentY = 0;
    protected double currentZ = 0;

    protected int maxReps;

    public void onData(long timestamp, Vector3 accel, Myo myo) {
        Log.v("ACCEL",
                String.format("%d, %f, %f, %f", timestamp, accel.x(), accel.y(), accel.z()));

        checkForGestureMatch(accel, myo);
    }

    public int getCurrentReps() {
        return currentReps;
    }

    public int getCurrentSets() {
        return currentSets;
    }

    public double getCurrentX() {
        return currentX;
    }

    public double getCurrentY() {
        return currentY;
    }

    public double getCurrentZ() {
        return currentZ;
    }

		public double getError() {
			return Math.min(Math.abs(currentZ), 1.0);
		}

    public int getMaxReps(){
        return maxReps;
    }

    public abstract double getPercentX();

    protected abstract void checkForGestureMatch(Vector3 accel, Myo myo);
}
