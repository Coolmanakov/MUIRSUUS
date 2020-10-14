package com.example.muirsuus.classes;

import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.muirsuus.R;

import java.util.ArrayList;
import java.util.List;

public class IndependentMethods  {

    public static void loadImageFromData(String namePhoto, ImageView imageView1) {
        String path = Environment.getExternalStorageDirectory().toString();
        String imagePath = path + "/AudioArmy/PhotoForDB/"+namePhoto+".jpg";
        imageView1.setImageURI(Uri.parse(imagePath));
    }

    public static List<String> get_list_images(String links){
        List<String> temp = new ArrayList<String>();
        char[] charLinks = links.toCharArray();
        char findСomma = 44;
        int start =0;
        for (int i = 0; i < charLinks.length; i++){
            if (charLinks[i] == findСomma || i+1 == charLinks.length){
                char[] dst;
                if(i+1 == charLinks.length){
                    dst=new char[i+1 - start];
                    links.getChars(start, i+1, dst, 0);
                }else {
                    dst=new char[i - start];
                    links.getChars(start, i, dst, 0);
                }
                links.getChars(start, i, dst, 0);
                temp.add(String.valueOf(dst));
                start = i+1;
            }
        }
        return temp;
    }



}
