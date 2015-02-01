package org.bitbucket.myoworkouttracker;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.*;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Vector3;

class CurlDetector extends GestureDetector {
		private final double minX = -0.8, maxX = 0.35;

    public CurlDetector(int maxReps) {
        this.maxReps = maxReps;
    }

    protected void checkForGestureMatch(Vector3 accel, final Myo myo) {
        Log.v("TEST", "test");
        currentX = accel.x();
        if (currentX > maxX) {
            reachedTop = true;
        } else if (currentX < minX && reachedTop) {
            reachedTop = false;
            currentReps++;
            myo.vibrate(Myo.VibrationType.SHORT);
        }
        Log.v("REPS", "Current sets : " + currentSets + "reps : " + currentReps);

        // Determine horizontal form
        currentY = accel.y();

        // Determine twist form
        currentZ = accel.z();

    }

		@Override
		public double getError() {
			// Ignore the error towards the beginning of the rep.
			return getPercentX() < 0.2
				? 0.0
				: super.getError();
		}

    @Override
    public double getPercentX() {
				double range = maxX - minX;
        double num = Math.min(Math.max(currentX - minX, 0), range);

				return reachedTop
					? 0.5 + ((range - num) / range) / 2
					: (num / range) / 2;
    }
}
