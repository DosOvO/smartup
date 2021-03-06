package de.unibremen.smartup.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.unibremen.smartup.R;
import de.unibremen.smartup.adapter.RecyclerAlarmAdapter;
import de.unibremen.smartup.model.Alarm;
import de.unibremen.smartup.viewmodel.AlarmViewModel;

public class ClockFragment extends Fragment {

    private final static String SHARED_PREF_NAME = "de.unibremen.smartup";
    RecyclerView listView;
    FloatingActionButton fab;
    private AlarmViewModel alarmViewModel;

    public ClockFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.clock_content, container, false);
        listView = (RecyclerView)view.findViewById(R.id.clock_list);
        fab = (FloatingActionButton)view.findViewById(R.id.add_alarm);

        Intent intent = getActivity().getIntent();
        final RecyclerAlarmAdapter adapter = new RecyclerAlarmAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);
        alarmViewModel.getAllAlarms().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(@Nullable List<Alarm> alarms) {
                adapter.setWords(alarms);
                Log.d("CLOCK", "Item List: " + adapter.getItemCount());
            }
        });
        Alarm alarm = (Alarm)intent.getSerializableExtra("newAlarm");
        if (alarm != null) {
            alarmViewModel.insert(alarm);
            intent.removeExtra("newAlarm");
        }

        //setOnScrollListenerForListView();

        return view;
    }

//    private void setOnScrollListenerForListView() {
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {}
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                final int lastItem = firstVisibleItem + visibleItemCount;
//                if((lastItem == totalItemCount)) {
//                    fab.hide();
//                } else {
//                    fab.show();
//                }
//            }
//        });
//    }
}
