package com.example.muirsuus.main_navigation.lit;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.muirsuus.R;
import com.example.muirsuus.databinding.ActivityPdfBinding;

import java.io.File;
import java.io.IOException;

public class PdfActivity extends AppCompatActivity implements View.OnClickListener {


    private final int currentPage = 0;
    private String path;
    private ActivityPdfBinding binding;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page curPage;
    private ParcelFileDescriptor descriptor;
    private float currentZoomLevel = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pdf);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        path = getIntent().getStringExtra("fileName");
        setTitle(getIntent().getStringExtra("keyName"));


        // устанавливаем слушатели на кнопки
        binding.btnPrevious.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.zoomin.setOnClickListener(this);
        binding.zoomout.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStart() {
        super.onStart();
        try {
            openPdfRenderer();
            displayPage(currentPage);
        } catch (Exception e) {
            Toast.makeText(this, "PDF-файл защищен паролем.", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPdfRenderer() {
        File file = new File(path);
        descriptor = null;
        pdfRenderer = null;
        try {
            descriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            pdfRenderer = new PdfRenderer(descriptor);
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayPage(int index) {
        if (pdfRenderer.getPageCount() <= index) return;
        // закрываем текущую страницу
        if (curPage != null) curPage.close();
        // открываем нужную страницу
        curPage = pdfRenderer.openPage(index);
        // определяем размеры Bitmap
        int newWidth = (int) (getResources().getDisplayMetrics().widthPixels * curPage.getWidth() / 72
                * currentZoomLevel / 40);
        int newHeight =
                (int) (getResources().getDisplayMetrics().heightPixels * curPage.getHeight() / 72
                        * currentZoomLevel / 64);
        Bitmap bitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Matrix matrix = new Matrix();
        float dpiAdjustedZoomLevel = currentZoomLevel * DisplayMetrics.DENSITY_MEDIUM
                / getResources().getDisplayMetrics().densityDpi;
        matrix.setScale(dpiAdjustedZoomLevel, dpiAdjustedZoomLevel);
        curPage.render(bitmap, null, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // отображаем результат рендера
        binding.imgView.setImageBitmap(bitmap);
        // проверяем, нужно ли делать кнопки недоступными
        int pageCount = pdfRenderer.getPageCount();
        binding.btnPrevious.setEnabled(0 != index);
        binding.btnNext.setEnabled(index + 1 < pageCount);
        binding.zoomout.setEnabled(currentZoomLevel != 2);
        binding.zoomin.setEnabled(currentZoomLevel != 12);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPrevious: {
                // получаем индекс предыдущей страницы
                int index = curPage.getIndex() - 1;
                displayPage(index);
                break;
            }
            case R.id.btnNext: {
                // получаем индекс следующей страницы
                int index = curPage.getIndex() + 1;
                displayPage(index);
                break;
            }
            case R.id.zoomout: {
                // уменьшаем зум
                --currentZoomLevel;
                displayPage(curPage.getIndex());
                break;
            }
            case R.id.zoomin: {
                // увеличиваем зум
                ++currentZoomLevel;
                displayPage(curPage.getIndex());
                break;
            }
        }
    }

    @Override
    public void onStop() {
        try {
            closePdfRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    private void closePdfRenderer() throws IOException {
        if (curPage != null) curPage.close();
        if (pdfRenderer != null) pdfRenderer.close();
        if (descriptor != null) descriptor.close();
    }
}