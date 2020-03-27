package com.example.recyclertest;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.recyclertest.mock.MockAdapter;
import com.example.recyclertest.mock.MockGenerator;

import java.util.Random;

public class RecyclerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mRecycler;
    private final ContactsAdapter adapter = new ContactsAdapter();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mErrorView;

    private ContactsAdapter.OnItemClickListener mListener;

    private Random mRandom = new Random();

    public static RecyclerFragment newInstance() {
       return new RecyclerFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ContactsAdapter.OnItemClickListener){
            mListener = (ContactsAdapter.OnItemClickListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecycler = view.findViewById(R.id.rv);
        mSwipeRefreshLayout = view.findViewById(R.id.refresher);
        mErrorView = view.findViewById(R.id.error_view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(adapter);

        //adapter.addData(MockGenerator.generate(3),true);

        adapter.setListener(mListener);

    }

    @Override
    public void onRefresh() {
//        mSwipeRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                int count = mRandom.nextInt(4);
//
//                if(count == 0){
//                    showError();
//                }
//                else{
//                    showData(count);
//                }
//
//
//                if(mSwipeRefreshLayout.isRefreshing()){
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//            }
//        }, 2000);

        getLoaderManager().restartLoader(0, null, this);
    }

//    private void showError(){
//        mErrorView.setVisibility(View.VISIBLE);
//        mRecycler.setVisibility(View.GONE);
//    }
//
//    private void showData(int count){
//        adapter.addData(MockGenerator.generate(count), new Random().nextBoolean());
//        mErrorView.setVisibility(View.GONE);
//        mRecycler.setVisibility(View.VISIBLE);
//    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null,
                null,
                ContactsContract.Contacts._ID
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);

        if(mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }
}
