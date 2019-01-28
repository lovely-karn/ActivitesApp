package com.lovely.conzumex.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lovely.conzumex.R;
import com.lovely.conzumex.viewmodel.ActivityViewModel;

import static com.lovely.conzumex.constants.AppConstant.BUNDLE_ISPOPUP;

public class MainActivity extends AppCompatActivity
        implements ActivityListFragment.OnFragmentInteractionListener {

    private FragmentManager fragmentManager;
    private ActivityViewModel activityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get fragment manager
        getFragmentManagerInstance();

        // now attach fragment
        attachFrag();

        // attaching viewModel ->
        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);
    }

    private void getFragmentManagerInstance() {

        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
    }

    public ActivityViewModel getViewModel() {

        return activityViewModel;
    }

    public void attachFrag() {

        // create ActivityListFragment->
        ActivityListFragment fragment = new ActivityListFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean(BUNDLE_ISPOPUP, false);

        fragment.setArguments(bundle);

        // attach activity list fragment
        fragmentManager.beginTransaction()
                .add(R.id.flContainer, fragment)
                .commit();
    }


    @Override
    public void onItemClicked(Bundle bundle) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
