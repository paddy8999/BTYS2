package io.github.paddy8999.btys2;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import io.github.paddy8999.btys2.R;

public class HomeFragment extends Fragment {

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
            DatabaseHandler.TableInfo.COLUMN_NAME_DATE + " DESC";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DatabaseHandler dbHelper = new DatabaseHandler(getActivity());
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



        try{
            long temp = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM entry_table", null);
             lengthOfDatabase = (int) temp;
        }catch(CursorIndexOutOfBoundsException e){

        }



        int noOfFragments;

        if(lengthOfDatabase<=10){
            noOfFragments = lengthOfDatabase;
        }else{
            noOfFragments = 10;
        }

        int x;
        for(x = 0; x<noOfFragments; x++){
            cursor.moveToPosition(x);
            Bundle bundle = new Bundle();
            bundle.putString("date", cursor.getString(1));
            bundle.putString("user", cursor.getString(2));
            bundle.putString("notes", cursor.getString(3));
            bundle.putString("weight", cursor.getString(4));
            bundle.putString("sp02", cursor.getString(5));
            bundle.putString("pulse", cursor.getString(6));
            bundle.putString("bloodPressureSys", cursor.getString(7));
            bundle.putString("bloodPressureDia", cursor.getString(8));
            bundle.putString("temperature", cursor.getString(9));
            Fragment frag = new HomeListRowFragment();
            frag.setArguments(bundle);
            fragmentTransaction.add(R.id.home_list, frag, x+"");

        }




        fragmentTransaction.commit();

        FrameLayout frame = (FrameLayout) v.findViewById(R.id.home_frame);





        return v;
    }
}


