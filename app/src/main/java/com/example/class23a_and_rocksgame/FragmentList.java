package com.example.class23a_and_rocksgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textview.MaterialTextView;

public class FragmentList extends Fragment {

    public static String KEY_NAME = "KEY_NAME";
    public static String KEY_SCORE = "KEY_SCORE";

    private MaterialTextView list_LBL_title;
    private MaterialTextView list_LBL_array;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        findViews(view);

        list_LBL_array.setText(list_LBL_array.getText() + "\n" + KEY_NAME + " " + KEY_SCORE);
        return view;
    }


    private void findViews(View view) {
        list_LBL_title = view.findViewById(R.id.list_LBL_title);
        list_LBL_array = view.findViewById(R.id.list_LBL_array);
    }

}
