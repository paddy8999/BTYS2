package io.github.paddy8999.btys2;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import io.github.paddy8999.btys2.R;

public class HistoryFragment extends Fragment {

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

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
        int lengthOfDatabase=0;



        try
        {
            long temp = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM entry_table", null);
            lengthOfDatabase = (int) temp;
        }

        catch(CursorIndexOutOfBoundsException e)
        {
            Log.d("length of database", "0");}




        int noOfFragments = lengthOfDatabase;


        int x;
        for(x = 0; x<noOfFragments; x++){
            fragmentTransaction.add(R.id.home_list, new HomeListRowFragment(), x+"");
            Log.d("frag", "x=" + x);
        }
        Log.d("frag", "x=" + x);


        fragmentTransaction.commit();

        FrameLayout frame = (FrameLayout) v.findViewById(R.id.home_frame);
        Log.d("hf", "" + frame.getWidth());




        return v;
    }
}

