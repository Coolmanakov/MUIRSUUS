package com.example.muirsuus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.example.muirsuus.databinding.ActivityQrBinding;
import com.google.zxing.Result;

public class QrActivity extends AppCompatActivity {
    private  static final int  CAMERA_REQUEST_CODE = 101;
    private CodeScanner codeScanner;
    private ActivityQrBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qr);
        setupPermissions();
        codeScannerDo();
    }

    private void codeScannerDo() {
        codeScanner = new CodeScanner(this,binding.scannerView);

        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.scannerTextView.setText(result.getText());
                    }
                });
            }
        });

        codeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Exception error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("mLog", "QrActivity: Camera initialization error" +error.getMessage() );
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
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
    private void  setupPermissions(){
        int permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        if( permission != PackageManager.PERMISSION_GRANTED){
            makeRequest();
        }
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.CAMERA},
                CAMERA_REQUEST_CODE);
    }


}