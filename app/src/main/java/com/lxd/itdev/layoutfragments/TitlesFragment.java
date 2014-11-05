package com.lxd.itdev.layoutfragments;


import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.ListView;

/**
 * Created by itdev on 14-11-5.
 */
public class TitlesFragment extends ListFragment {

    boolean mDualPane;
    int mCurCheckPosition = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> connectArrayToListView = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                SuperHeroInfo.NAMES);

        setListAdapter(connectArrayToListView);

        View detailsFrame = getActivity().findViewById(R.id.details);

        mDualPane = detailsFrame != null && detailsFrame.getVisibility()
                == View.VISIBLE;

        if(savedInstanceState != null){

            mCurCheckPosition = savedInstanceState.getInt("curChoice",0);
        }
        //mDualPane = true;

        if(mDualPane) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(mCurCheckPosition);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("curChoice",mCurCheckPosition);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        showDetails(position);

    }

    void showDetails(int index) {
        mCurCheckPosition = index;

        if(mDualPane) {

            getListView().setItemChecked(index,true);
            DetailsFragment details = (DetailsFragment)
                    getFragmentManager().findFragmentById(R.id.details);

            if(details == null || details.getShownIndex() != index) {
                details = DetailsFragment.newInstance(index);

                FragmentTransaction ft =
                        getFragmentManager().beginTransaction();
                ft.replace(R.id.details,details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }


        } else {
            Intent intent = new Intent();

            intent.setClass(getActivity(),DetailsActivity.class);
            intent.putExtra("index",index);

            startActivity(intent);

        }

    }
}
























