package com.happy.radiostation.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.happy.radiostation.R;
import com.happy.radiostation.data.DataHelper;
import com.happy.radiostation.data.RadioData;
import com.happy.radiostation.event.RadioEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChanelListFragment extends Fragment {

    @BindView(R.id.radioRecylerView)
    RecyclerView mRadioRecyclerView;
    private RadioAdapter radioAdapter;
    private DataHelper dataHelper;
    private EventListener eventListener;
    private boolean favorite;

    public static ChanelListFragment newInstance() {
        ChanelListFragment fragment = new ChanelListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        eventListener = (EventListener) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dataHelper = DataHelper.getDataHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chanel_list, container, false);
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRadioRecyclerView.setLayoutManager(layoutManager);
        radioAdapter = new RadioAdapter();
        mRadioRecyclerView.setAdapter(radioAdapter);
        ItemClickSupport.addTo(mRadioRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                eventListener.onNewItemPlay(radioAdapter.getItem(position));
                hideSoftKeyBoard(recyclerView);
            }
        });
        showAllList();
        return view;
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void updateAdapter(String searchText) {
        ArrayList<RadioData> radioDatas = dataHelper.getRadioDatas();
        ArrayList<RadioData> datas = new ArrayList<>();
        if (searchText.length() <= 0) {
            showAllList();
            return;
        }
        for (RadioData data : radioDatas) {
            final String name = data.getName().toLowerCase();
            if (name.contains(searchText.toLowerCase())) {
                datas.add(data);
            }
        }
        radioAdapter.setDatas(datas);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                showAllList();
                hideSoftKeyBoard(searchView);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateAdapter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            addItem();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addItem() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.ittem_add, null, false);
        final EditText nameEditText = (EditText) view.findViewById(R.id.name);
        final EditText urlEditText = (EditText) view.findViewById(R.id.url);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.customizedAlertSlideUpAndDown);
        builder.setTitle("Add New Radio")
                .setView(view)
                .setCancelable(true)
                .setIcon(R.drawable.ic_radio_black_title_24dp)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String text = nameEditText.getText().toString();
                        String url = urlEditText.getText().toString();
                        if (text != null && text.length() > 0 && url != null && url.length() > 0) {
                            RadioData radioData = new RadioData(text, "Own station", url);
                            dataHelper.addNewRadio(radioData);
                            showAllList();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRadioListFilterEvent(RadioEvent event) {
        if (event.getEvent() == RadioEvent.Event.FAVORITE_LIST) {
            showFavorite();
        } else if (event.getEvent() == RadioEvent.Event.ALL_LIST) {
            showAllList();
        } else if (event.getEvent() == RadioEvent.Event.LIST_REFRESH) {
            if (favorite) {
                showFavorite();
            } else {
                showAllList();
            }
        } else if (event.getEvent() == RadioEvent.Event.PLAY) {
            radioAdapter.setPlayItem(event.getData(), true);

        } else if (event.getEvent() == RadioEvent.Event.STOP) {
            radioAdapter.setPlayItem(event.getData(), false);
        }
    }

    public void showAllList() {
        favorite = false;
        radioAdapter.setDatas(dataHelper.getRadioDatas());
    }

    public void showFavorite() {
        favorite = true;
        radioAdapter.setDatas(dataHelper.getFavRadioDatas());
    }

    private void hideSoftKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
