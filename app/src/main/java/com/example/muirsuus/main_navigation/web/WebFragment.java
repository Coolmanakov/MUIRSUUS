package com.example.muirsuus.main_navigation.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.muirsuus.MainActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.databinding.FragmentWebBinding;

@SuppressLint("StaticFieldLeak")
public class WebFragment extends Fragment {
    public static final String WEB_SIZE = "webSizeValue";
    public static final String ONLINE_WEB_PAGES = "loadOnlinePage";
    private static final String LOG_TAG = "mLog " + WebFragment.class.getCanonicalName();
    private final String ONLINE_URL = "http://91.210.170.162:80";
    private final String OFFLINE_URL = "http://192.168.20.100:80";
    public float WEB_SIZE_FLOAT = 50;
    private FragmentWebBinding binding;
    private SharedPreferences webSizePreferences;
    private SharedPreferences onlineWebPreferences;

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("АСУУС");
        webSizePreferences = getActivity().getSharedPreferences(WEB_SIZE, Context.MODE_PRIVATE);
        webSizePreferences.edit();
        //получаем сохранённое значение размера веб-страницы
        if (webSizePreferences.contains(WEB_SIZE)) {
            WEB_SIZE_FLOAT = webSizePreferences.getFloat(WEB_SIZE, WEB_SIZE_FLOAT);
            if (WEB_SIZE_FLOAT == 0) {
                WEB_SIZE_FLOAT = 50;
            }
        }

        //меняем стрелку в actionBar на hamburger
        ((MainActivity) getActivity()).resetActionBar(false,
                DrawerLayout.LOCK_MODE_UNLOCKED);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web, container, false);
        binding.web.setWebViewClient(new WebViewClient());
        binding.web.getSettings().setJavaScriptEnabled(true);
        binding.web.getSettings().setDomStorageEnabled(true);
        binding.web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //устанавливаем размеры при загрузке страницы
                binding.web.setInitialScale(Math.round(WEB_SIZE_FLOAT));
                super.onPageStarted(view, url, favicon);
            }
        });
        onlineWebPreferences = getActivity().getSharedPreferences(ONLINE_WEB_PAGES, Context.MODE_PRIVATE);
        if (onlineWebPreferences.contains(ONLINE_WEB_PAGES)) {
            if (onlineWebPreferences.getBoolean(ONLINE_WEB_PAGES, false)) {
                binding.web.loadUrl(ONLINE_URL);
            } else {
                binding.web.loadUrl(OFFLINE_URL);
            }
        } else {
            binding.web.loadUrl(OFFLINE_URL);
            Log.e(LOG_TAG, "В mySharedPreferences нет bool значения для загрузки веб-страницы");
        }


        return binding.getRoot();
    }


}
