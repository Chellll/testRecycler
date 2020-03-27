package com.example.recyclertest.mock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclertest.R;

import java.util.ArrayList;
import java.util.List;

public class MockAdapter extends RecyclerView.Adapter<MockHolder> {

    private List<Mock> mMockList = new ArrayList<>();

    @NonNull
    @Override
    public MockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View inflate = inflater.inflate(R.layout.li_mock, parent, false);
        return new MockHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MockHolder holder, int position) {
        holder.bind(mMockList.get(position));
    }


    @Override
    public int getItemCount() {
        return mMockList.size();
    }

    public void addData(List<Mock> mocks, boolean refresh){

        if(refresh){
            mMockList.clear();
        }

        mMockList.addAll(mocks);
        notifyDataSetChanged();
    }
}
