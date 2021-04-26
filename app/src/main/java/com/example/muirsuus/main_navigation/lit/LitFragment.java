package com.example.muirsuus.main_navigation.lit;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muirsuus.MainActivity;
import com.example.muirsuus.R;
import com.example.muirsuus.adapters.BooksAdapter;
import com.example.muirsuus.adapters.GridAdapter;
import com.example.muirsuus.databinding.GridPdfItemBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LitFragment extends Fragment {

    private static final String LOG_TAG = "mLog " + LitFragment.class.getCanonicalName();
    private static final int EXT_STORAGE_PERMISSION_CODE = 101;

    private static final Comparator<books> ALPHABETICAL_COMPARATOR = new Comparator<books>() {
        @Override
        public int compare(books a, books b) {
            return a.getBook_name().compareTo(b.getBook_name());
        }
    };
    private String tmpFolder = "";
    private final List<books> booksList = new ArrayList<>();
    //private LitFragmentBinding binding;
    //private PdfItemBinding pdfBinding;
    private GridPdfItemBinding pdfItemBinding;
    private List<String> booksNames = new ArrayList<>();
    private GridAdapter gridAdapter;
    private BooksAdapter.ListItemClickListener listener;
    private BooksAdapter booksAdapter;
    private LitViewModel litViewModel;
    private List<String> booksPhotos;


    /**
     * Метод реализующий логику фильтра
     *
     * @param models - исходный список книг
     * @param query  - строка, которую ввёл пользователь
     * @return список книг, которые подходят пользователю
     */
    private static List<books> filter(List<books> models, String query) {
        //преобразуем строчку к нижнему регистру
        final String lowerCaseQuery = query.toLowerCase();

        // в этот список запишем, подходяшие книги
        final List<books> filteredModelList = new ArrayList<>();

        for (books model : models) {
            // преобразуем название книги к нижнему регистру
            final String text = model.getBook_name().toLowerCase();

            //если такая строчка есть в названии книги, то добавляем название книги в отфильтрованный список
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    /**
     * Метод, который убирает клавиатуру на фрагменте
     */
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        pdfItemBinding = DataBindingUtil.inflate(inflater, R.layout.grid_pdf_item, container, false);
        pdfItemBinding.setLifecycleOwner(this);
        setHasOptionsMenu(true);//обязательно для того, чтобы была возможно раздувать(inflate) action bar
        return pdfItemBinding.getRoot();
    }
    /*---------------------------------------------Разрешения приложения----------------------------------------------------------------*/
    /*---------------------------------------------Копируем книги из assets в FilesDir--------------------------------------------------*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //set Title for fragment
        getActivity().setTitle("Литература");

        //Устанавливаем стрелочку навигации
        ((MainActivity) getActivity()).resetActionBar(true,
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        tmpFolder = getContext().getFilesDir() + File.separator;
        /*---------------------------------------------Разрешения приложения----------------------------------------------------------------*/
        if (ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXT_STORAGE_PERMISSION_CODE);
            Log.d(LOG_TAG, "After getting permission: " + Manifest.permission.WRITE_EXTERNAL_STORAGE + " " + ContextCompat.checkSelfPermission(
                    getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE));

        } else {
            // We were granted permission already before
            Log.d(LOG_TAG, "Already has permission to write to external storage");
        }
        /*---------------------------------------------Разрешения приложения----------------------------------------------------------------*/
        booksNames = getPDFFromAssets(); //получаем книги из assets
        /*---------------------------------------------Слушатель нажатия на книжку----------------------------------------------------------------*/
        listener = new BooksAdapter.ListItemClickListener() {
            @Override
            public void OnItemClickListener(int i) {


                //получаем имя книги из адаптера и прибавляем ".pdf",
                // чтобы можно было перейти интетом
                String bookName = booksAdapter.getSortedList().get(i).getBook_name() + ".pdf";
                // copy the file to external storage accessible by all
                copyFileFromAssets(bookName); //копируем книги из assets в FilesDir


                // PDF reader code
                Uri uri = null;
                tmpFolder = getContext().getFilesDir() + File.separator;
                File file = new File(tmpFolder
                        + bookName);
                Log.d(LOG_TAG, "file " + bookName + " " + file.getAbsolutePath());

                uri = FileProvider.getUriForFile(getContext(),
                        getString(R.string.file_provider_authority),
                        file);
                Log.i(LOG_TAG, "Launching viewer " + bookName + " " + file.getAbsolutePath());

                //Intent intent = new Intent(Intent.ACTION_VIEW, FileProvider.getUriForFile(v.getContext(), "org.eicsanjose.quranbasic.fileprovider", file));

                Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    Log.i(LOG_TAG, "Starting pdf viewer activity");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
                /*---------------------------------------------Слушатель нажатия на книжку----------------------------------------------------------------*/
            }

        };
        setUpViewModel();
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) > 0) {
            out.write(buffer, 0, read);
        }
        in.close();
        out.flush();
        out.close();
    }

    /*---------------------------------------------Разрешения приложения----------------------------------------------------------------*/
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        Log.d(LOG_TAG, "Request for write permission to external storage result:" + permissions[0] + " " + grantResults[0]);
        // Now let us make sure our cache dir exists. This would not work if user denied. But then again
        // in that case the whole app will not work. Add error checking
        File tmpDir = new File(tmpFolder);
        if (!tmpDir.exists()) {
            Log.d(LOG_TAG, "Tmp dir to store pdf does not exist");
            tmpDir.mkdir();
            Log.d(LOG_TAG, "Tmpdir created " + tmpDir.exists());
        } else {
            Log.d(LOG_TAG, "Tmpdir already exists " + tmpDir.exists());
        }

    }
    /*---------------------------------------------Копируем книги из assets в FilesDir--------------------------------------------------*/

    /**
     * Метод проверяет есть ли во внешнем хранилище книга,
     * если такой нет, то перепесивыаем из assets во внешнее хранилище
     */
    private void copyFileFromAssets(String fileName) {
        Log.i(LOG_TAG, "Copy file from asset:" + fileName);

        AssetManager assetManager = getContext().getAssets();

        // file to copy to from assets
        File cacheFile = new File(tmpFolder + fileName);
        InputStream in = null;
        OutputStream out = null;

        try {
            Log.d(LOG_TAG, "Copying from assets folder to cache folder");
            if (cacheFile.exists()) {
                // already there. Do not copy
                Log.d(LOG_TAG, "Cache file exists at:" + cacheFile.getAbsolutePath());
                return;
            } else {
                Log.d(LOG_TAG, "Cache file does NOT exist at:" + cacheFile.getAbsolutePath());
                // TODO: There should be some error catching/validation etc before proceeding
                in = assetManager.open("books/" + fileName);
                out = new FileOutputStream(cacheFile);
                copyFile(in, out);

            }
        } catch (IOException ioe) {
            Log.e(LOG_TAG, "Error in copying file from assets " + fileName);
            ioe.printStackTrace();

        }

    }

    /**
     * Получаем список книг из assets
     *
     * @return pdfFiles - список названий книг с .pdf
     */
    private List<String> getPDFFromAssets() {
        List<String> pdfFiles = new ArrayList<>();
        AssetManager assetManager = getContext().getAssets();

        try {
            for (String name : assetManager.list("books")) {
                // include files which end with pdf only
                if (name.toLowerCase().endsWith("pdf")) {

                    pdfFiles.add(name);
                }
            }
        } catch (IOException ioe) {
            Log.e(LOG_TAG, "Could not read files from assets folder");
            Toast.makeText(getContext(),
                    "Could not read files from assets folder",
                    Toast.LENGTH_LONG)
                    .show();
        }

        return pdfFiles;
    }

    /*---------------------------------------------Устанавливаем адаптер в recyclerView--------------------------------------------------*/
    private void setUpViewModel() {
        booksNames = getPDFFromAssets();
        Collections.sort(booksNames);

        litViewModel = new LitViewModel(getContext(), booksNames);
        litViewModel.getPhotos().observe(pdfItemBinding.getLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                booksAdapter = new BooksAdapter(getContext(), listener, ALPHABETICAL_COMPARATOR);
                for (int i = 0; i < booksNames.size(); i++) {
                    booksAdapter.setBook(new books(booksNames.get(i), strings.get(i)));
                }
                pdfItemBinding.recycler.setAdapter(booksAdapter);

            }
        });
    }

    /*---------------------------------------------Устанавливаем адаптер в recyclerView--------------------------------------------------*/
    /*---------------------------------------------Работа с SearchView-------------------------------------------------------------------*/
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);


        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        //устанавливаем слушатель запросов пользователя
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * Вызывается, когда текст запроса изменяется пользователем.
             * @param query - новое содержимое текстового поля запроса.
             * @return false, если SearchView должен выполнять действие по умолчанию, показывая любые предложения, если они доступны,
             *             true, если действие было обработано слушателем.
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            /**
             * Вызывается, когда пользователь отправляет запрос.
             * Это могло произойти из-за нажатия клавиши на клавиатуре или из-за нажатия кнопки отправки.
             * Слушатель может переопределить стандартное поведение, вернув истину, чтобы указать, что он обработал запрос на отправку.
             * В противном случае верните false, чтобы SearchView обработал отправку, запустив любое связанное намерение.
             *
             * @param query - текст запроса, который должен быть отправлен
             *
             * @return Значение true, если запрос был обработан слушателем,
             *          значение false, чтобы позволить SearchView выполнить действие по умолчанию.
             */
            @Override
            public boolean onQueryTextChange(String query) {
                //из адаптера берём исходную версию списка книг и фильтруем именно её
                final List<books> filteredModelList = filter(booksAdapter.getExSortedList(), query);
                booksAdapter.replaceAll(filteredModelList);
                pdfItemBinding.recycler.scrollToPosition(0);
                pdfItemBinding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        hideKeyboardFrom(getContext(), pdfItemBinding.getRoot());
                    }
                });
                return true;
            }
        });
    }
    /*---------------------------------------------Работа с SearchView-------------------------------------------------------------------*/

}