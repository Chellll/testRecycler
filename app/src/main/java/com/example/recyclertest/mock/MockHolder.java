package com.example.recyclertest.mock;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclertest.R;

public class MockHolder extends RecyclerView.ViewHolder {

    private TextView mName;
    private TextView mValue;

    public MockHolder (View itemView){
        super(itemView);

        mName = itemView.findViewById(R.id.tv_name);
        mValue = itemView.findViewById(R.id.tv_value);
    }


    public void bind(Mock mock) {

        mName.setText(mock.getName());
        mValue.setText(mock.getValue());

    }
}
