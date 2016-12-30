package com.compassecg.test720.compassecg.Home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.compassecg.test720.compassecg.APP;
import com.compassecg.test720.compassecg.R;
import com.test720.auxiliary.Utils.BaseFragment;

/**
 * Created by hp on 2016/12/6.
 */

public class MainFragment extends BaseFragment {


    public static MainFragment maninfrag=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        maninfrag = this;

        APP.cityName = APP.baiduCity;

    }
}
