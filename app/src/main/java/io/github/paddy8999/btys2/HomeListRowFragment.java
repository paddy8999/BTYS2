package io.github.paddy8999.btys2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class HomeListRowFragment extends Fragment {

    private String[] projection = {
            DatabaseHandler.TableInfo._ID,
            DatabaseHandler.TableInfo.COLUMN_NAME_DATE,
            DatabaseHandler.TableInfo.COLUMN_NAME_USER_NAME,
            DatabaseHandler.TableInfo.COLUMN_NAME_NOTES,
            DatabaseHandler.TableInfo.COLUMN_NAME_WEIGHT,
            DatabaseHandler.TableInfo.COLUMN_NAME_SP02,
            DatabaseHandler.TableInfo.COLUMN_NAME_PULSE_RATE,
            DatabaseHandler.TableInfo.COLUMN_NAME_BLOOD_PRESSURE_SYS,
            DatabaseHandler.TableInfo.COLUMN_NAME_BLOOD_PRESSURE_DIA,
            DatabaseHandler.TableInfo.COLUMN_NAME_TEMPERATURE
    };
    private String sortOrder =
            DatabaseHandler.TableInfo.COLUMN_NAME_DATE + " ASC LIMIT 1";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseHandler dbHelper = new DatabaseHandler(this.getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHandler.TableInfo.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        cursor.moveToPosition(Integer.parseInt(getTag().toString()));

        //ViewGroup placeholder = (ViewGroup)this.findViewById(placeholderId);


        //TextView date = (TextView)getView().findViewById(R.id.text_view_date_home);
        //date.setText(cursor.getString(1));

        //HomeListRowFragment fragmentObj = (HomeListRowFragment)getFragmentManager().findFragmentById(R.id.text_view_date_home);
        //fragmentObj.
        return inflater.inflate(R.layout.fragment_home_list_row, container, false);
    }
}


