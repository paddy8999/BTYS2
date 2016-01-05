package io.github.paddy8999.btys2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
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
            DatabaseHandler.TableInfo.COLUMN_NAME_DATE + " DESC";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseHandler dbHelper = new DatabaseHandler(this.getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        View view;


        Cursor cursor = db.query(
                DatabaseHandler.TableInfo.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        cursor.moveToPosition(Integer.parseInt(getTag()));

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int dim16Dp = 16 * (getResources().getDisplayMetrics().densityDpi) / 160;
        linearLayoutParams.setMargins(dim16Dp, dim16Dp, dim16Dp, 0);


        LinearLayout linearLayout = new LinearLayout(this.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.setBackgroundColor(Color.WHITE);

        for(int i =1;i<10;i++){
            try{
                String a = cursor.getString(i);
                LinearLayout horLinearLayout1 = new LinearLayout(this.getContext());
                horLinearLayout1.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams horLinearLayout1Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                horLinearLayout1.setLayoutParams(horLinearLayout1Params);
                linearLayout.addView(horLinearLayout1);

                TextView timeLabel = new TextView(this.getContext());
                timeLabel.setGravity(Gravity.LEFT);
                switch (i) {
                    case 1:
                        timeLabel.setText("Date & Time");
                        break;
                    case 2:
                        timeLabel.setText("User");
                        break;
                    case 3:
                        timeLabel.setText("Notes");
                        break;
                    case 4:
                        timeLabel.setText("Weight");
                        break;
                    case 5:
                        timeLabel.setText("Sp02");
                        break;
                    case 6:
                        timeLabel.setText("Pulse");
                        break;
                    case 7:
                        timeLabel.setText("Blood Pressure Sys");
                        break;
                    case 8:
                        timeLabel.setText("Blood Pressure Dia");
                        break;
                    case 9:
                        timeLabel.setText("Temperature");
                        break;

                }

                timeLabel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1F));
                horLinearLayout1.addView(timeLabel);

                TextView timeStamp = new TextView(this.getContext());
                timeStamp.setGravity(Gravity.RIGHT);
                timeStamp.setText(a);
                timeStamp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1F));
                horLinearLayout1.addView(timeStamp);

            }catch(java.lang.NullPointerException e){

            }

        }


        view = linearLayout;
        return view;
        //return inflater.inflate(R.layout.fragment_home_list_row, container, false);
    }
}


