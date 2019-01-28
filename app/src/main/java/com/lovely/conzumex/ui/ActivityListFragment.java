package com.lovely.conzumex.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovely.conzumex.R;
import com.lovely.conzumex.viewmodel.ActivityViewModel;

import static com.lovely.conzumex.constants.AppConstant.ACTIVITY_IMAGE;
import static com.lovely.conzumex.constants.AppConstant.ACTIVITY_NAME;
import static com.lovely.conzumex.constants.AppConstant.BUNDLE_ISPOPUP;

/**
 * ActivityListFragment
 */
public class ActivityListFragment
        extends DialogFragment
        implements ActivityListAdapter.OnViewHolderItemClick {

    private OnFragmentInteractionListener mListener;
    private Activity activity;

    private ActivityViewModel activityViewModel;

    private RecyclerView rvActivityList;
    private ActivityListAdapter activityListAdapter;

    private boolean isPopUp;

    private LinearLayoutCompat llActivityList;

    public ActivityListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // fetch bundle values if anything is passed via bundle

            if (getArguments().getBoolean(BUNDLE_ISPOPUP, false)) {
                isPopUp = true;
            } else {
                isPopUp = false;
            }
        }


        // fetch the values from recycler view
        if (!isPopUp) {
            activityViewModel = ((MainActivity) activity).getViewModel();
        } else {
            activityViewModel = ((DetailActivity) activity).getViewModel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (isPopUp) {
            Dialog dialog = getDialog();

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            llActivityList.setBackgroundResource(R.color.white);

        } else {
            llActivityList.setBackgroundResource(R.drawable.activity_list_white_background);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_activity_list, container, false);

        init(view);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void init(View view) {

        // intialising recycler view
        llActivityList = view.findViewById(R.id.llActivityList);
        rvActivityList = view.findViewById(R.id.rvActivityList);

        activityListAdapter = new ActivityListAdapter(this);
        rvActivityList.setAdapter(activityListAdapter);
        rvActivityList.setLayoutManager(new LinearLayoutManager(activity));

        activityListAdapter.updateList(activityViewModel.getActivityListDummy());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            if (!getArguments().getBoolean(BUNDLE_ISPOPUP, false)) {
                activity = (MainActivity) context;
            } else {
                activity = (DetailActivity) context;
            }
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        activity = null;
    }

    @Override
    public void onClickOfItem(String img, String activityName) {

        if (!isPopUp) {
            Intent intent = new Intent(activity, DetailActivity.class);
            intent.putExtra(ACTIVITY_IMAGE, img);
            intent.putExtra(ACTIVITY_NAME, activityName);

            startActivity(intent);
        } else {

            Bundle bundle = new Bundle();
            bundle.putString(ACTIVITY_IMAGE, img);
            bundle.putString(ACTIVITY_NAME, activityName);
            mListener.onItemClicked(bundle);

            dismiss();
        }
    }

    /**
     * OnFragmentInteractionListener
     */
    public interface OnFragmentInteractionListener {

        void onItemClicked(Bundle bundle);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commit();
        } catch (IllegalStateException e) {
            Log.d("dialog exception", "Exception", e);
        }
    }
}
