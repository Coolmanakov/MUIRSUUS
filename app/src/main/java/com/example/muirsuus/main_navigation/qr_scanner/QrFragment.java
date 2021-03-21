package com.example.muirsuus.main_navigation.qr_scanner;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.example.muirsuus.MainActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.databinding.ActivityQrBinding;
import com.google.zxing.Result;

public class QrFragment extends Fragment {
    private CodeScanner codeScanner;
    private ActivityQrBinding binding;
    private final MainActivity mainActivity = new MainActivity();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_qr, container, false);
        getActivity().setTitle("Сканнер");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);
        ((MainActivity) getActivity()).resetActionBar(false,
                DrawerLayout.LOCK_MODE_UNLOCKED);
        codeScannerDo();
    }

    private void codeScannerDo() {
        codeScanner = new CodeScanner(getContext(), binding.scannerView);
        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);


        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NavController navController = NavHostFragment.findNavController(QrFragment.this);
                        Bundle bundle = new Bundle();
                        bundle.putString("point", result.getText());
                        navController.navigate(R.id.action_qrActivity_to_informationFragment, bundle);
                    }
                });
            }
        });

        codeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Exception error) {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("mLog", "QrFragment: Camera initialization error " + error.getMessage());
                    }
                });
            }
        });
        binding.scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    public void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
}