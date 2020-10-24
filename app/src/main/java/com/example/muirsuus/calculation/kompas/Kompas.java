package com.example.muirsuus.calculation.kompas;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.muirsuus.calculation.kompas.KompasClass;
import com.example.muirsuus.calculation.kompas.SOTWFormater;
import com.example.muirsuus.R;

public class Kompas extends Fragment {

    private static final String TAG = "CompassActivity";

    private KompasClass kompass;
    private ImageView arrowView;
    private TextView sotwLabel;  // SOTW is for "side of the world"

    private float currentAzimuth;
    private SOTWFormater sotwFormater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_kompas, container,
                false);
        sotwFormater = new SOTWFormater(this);
        sotwLabel = (TextView)rootView.findViewById(R.id.sotw_label);
        arrowView = (ImageView)rootView.findViewById(R.id.main_image_hands);

        setupCompass();
        return rootView;
    }

    public void onStart() {
        super.onStart();
        Log.d(TAG, "start compass");
        kompass.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        kompass.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        kompass.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "stop compass");
        kompass.stop();
    };

    private void setupCompass() {
        kompass = new KompasClass(this.getContext());
        KompasClass.CompassListener cl = getCompassListener();
        kompass.setListener(cl);
    }

    private void adjustArrow(float azimuth) {
        Log.d(TAG, "will set rotation from " + currentAzimuth + " to "
                + azimuth);

        Animation an = new RotateAnimation(-currentAzimuth, -azimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        currentAzimuth = azimuth;

        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);

        arrowView.startAnimation(an);
    }

    private void adjustSotwLabel(float azimuth) {
        sotwLabel.setText(sotwFormater.format(azimuth));
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public KompasClass.CompassListener getCompassListener() {
        return new KompasClass.CompassListener() {
            @Override
            public void onNewAzimuth(final float azimuth) {
                // UI updates only in UI thread
                // https://stackoverflow.com/q/11140285/444966
                mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            adjustArrow(azimuth);
                            adjustSotwLabel(azimuth);
                        }
                    });
            }
        };
    };



}
