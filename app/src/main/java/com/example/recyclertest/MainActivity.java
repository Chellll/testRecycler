package com.example.recyclertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecyclerFragment.newInstance())
                    .commit();
        }

    }

    @Override
    public void OnItemClick(String id) {

        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                new String[]{id, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)},
                null
        );

        if(cursor != null && cursor.moveToFirst()){
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();

            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + number)));
        }

        Toast.makeText(this, "ID " + id, Toast.LENGTH_SHORT).show();
    }
}
