package com.marius.valeyou.market_place.Drawer.Home.PostAd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marius.valeyou.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Edu_subcategory extends Fragment {

    View view;
    ListView lv;

    String[] list = {"Distance Learning Courses","Hobby Classes","Career Counseling","Play Schools - Creche",
                "Text books & Study Material","Workshops","Study Abroad Consultants","Schools & School Tuitions",
                "Entrance Exam Coaching","Competitive Exams Coaching","Vocational Skill Training","Certifications & Training"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edu_subcatg, container, false);

        PostFreeAd.tb_title.setText("Education & Training");
        PostFreeAd.tv.setText("Select Sub-Category");

        lv = (ListView) view.findViewById(R.id.lv_id);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(), R.layout.home_subcatg_item, R.id.tv_id, list);
        lv.setAdapter(adp);

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        PostFreeAd.tb_title.setText("Post Free Ad");
        PostFreeAd.tv.setText("Select Category");
    }

}