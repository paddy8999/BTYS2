package io.github.paddy8999.btys2;
        import android.app.DatePickerDialog;
        import android.app.Fragment;
        import android.app.TimePickerDialog;
        import android.content.ContentValues;
        import android.content.DialogInterface;
        import android.database.Cursor;
        import android.database.CursorIndexOutOfBoundsException;
        import android.database.DatabaseUtils;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.text.InputType;
        import android.text.format.Time;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.TimePicker;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Locale;

        import io.github.paddy8999.btys2.R;

public class InputFragment extends Fragment {

    private String date = "";
    private String time= "";
    private String notes= "";
    private String user= "";
    private Float weight= null;
    private Float sp02= null;
    private Float pulse= null;
    private Float bloodPressureSYS= null;
    private Float bloodPressureDIA= null;
    private Float temperatue= null;
    private String dateFinal= "";

    private EditText editTextDate;
    private EditText editTextTime;
    private EditText editTextNotes;
    private AutoCompleteTextView autoCompleteTextViewUser;
    private EditText editTextWeight;
    private EditText editTextSp02;
    private EditText editTextPulse;
    private EditText editTextBloodPressureSYS;
    private EditText editTextBloodPressureDia;
    private EditText editTextTemperature;
    private Button doneButton;


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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_input, container, false);

        editTextTime = (EditText) view.findViewById(R.id.edit_text_time);
        editTextDate = (EditText) view.findViewById(R.id.edit_text_date);
        autoCompleteTextViewUser = (AutoCompleteTextView) view.findViewById(R.id.user_autocomplete_text_view);
        editTextNotes = (EditText) view.findViewById(R.id.edit_text_notes);
        editTextWeight = (EditText)view.findViewById(R.id.edit_text_weight);
        editTextSp02 = (EditText) view.findViewById(R.id.edit_text_sp02);
        editTextPulse = (EditText) view.findViewById(R.id.edit_text_pulse);
        editTextBloodPressureSYS = (EditText) view.findViewById(R.id.edit_text_blood_pressure_sys);
        editTextBloodPressureDia = (EditText) view.findViewById(R.id.edit_text_blood_pressure_dia);
        editTextTemperature = (EditText) view.findViewById(R.id.edit_text_temperature);
        doneButton = (Button)view.findViewById(R.id.done_button);


        //view.findViewById(R.id.done_button).requestFocus();

        DatabaseHandler dbHelper = new DatabaseHandler(this.getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHandler.TableInfo.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                DatabaseHandler.TableInfo._ID                                 // The sort order
        );

        cursor.moveToLast();
        int itemId=0;
        long temp = 0;



        try
        {

            itemId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHandler.TableInfo._ID)));
            Log.d("length of database",""+temp);
        }

        catch(CursorIndexOutOfBoundsException e)
        {Log.d("length of database","0");}

        cursor.moveToFirst();

        boolean addToArrayList;

        ArrayList<String> usersTemp = new ArrayList<>();
        //usersTemp.add("Padraig");
        for(int i = 0; i<itemId;i++) {
            Log.d("for",""+i);
            addToArrayList = true;
            if (!(usersTemp.isEmpty())) {
                for (int j = 0; j < usersTemp.size(); j++) {
                    if (cursor.getString(cursor.getColumnIndex(DatabaseHandler.TableInfo.COLUMN_NAME_USER_NAME)).equals(usersTemp.get(j))) {
                        addToArrayList = false;
                    }
                }
            }
            if (addToArrayList) {
                usersTemp.add(cursor.getString(cursor.getColumnIndex(DatabaseHandler.TableInfo.COLUMN_NAME_USER_NAME)));
                Log.d("auto",cursor.getString(cursor.getColumnIndex(DatabaseHandler.TableInfo.COLUMN_NAME_USER_NAME)));
            }
            cursor.moveToNext();
        }

        String[] users = new String[usersTemp.size()];
        users=usersTemp.toArray(users);

        //Create Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.select_dialog_singlechoice, users);

        //Set the number of characters the user must type before the drop down list is shown
        autoCompleteTextViewUser.setThreshold(1);
        //Set the adapter
        autoCompleteTextViewUser.setAdapter(adapter);

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        editTextDate.setText(dateFormatter.format(Calendar.getInstance().
                        getTime()));

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextDate.setInputType(InputType.TYPE_NULL);
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        editTextDate.setText(dateFormatter.format(newDate.getTime()));}
                    }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                datePicker.show();

            }
        });


        final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

        editTextTime.setText(timeFormatter.format(Calendar.getInstance().getTime()));

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextTime.setInputType(InputType.TYPE_NULL);
                Calendar newCalendar = Calendar.getInstance();
                TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar newDate1 = Calendar.getInstance();
                        newDate1.set(newDate1.get(Calendar.YEAR), newDate1.get(Calendar.MONTH), newDate1.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                        editTextTime.setText(timeFormatter.format(newDate1.getTime()));
                    }

                }, newCalendar.get(Calendar.HOUR), newCalendar.get(Calendar.MINUTE), true);
                timePicker.show();
            }
        });


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputIntoDatabase(v);
            }
        });


        return view;
    }


    public void inputIntoDatabase (View view){
        date = editTextDate.getText().toString();
        time = editTextTime.getText().toString();
        user = autoCompleteTextViewUser.getText().toString();
        notes = editTextNotes.getText().toString();
        try {
            weight = Float.parseFloat(editTextWeight.getText().toString());
        } catch (java.lang.NumberFormatException e) {
            weight = null;
        }
        try {
            sp02 = Float.parseFloat(editTextSp02.getText().toString());
        } catch (java.lang.NumberFormatException e) {
            sp02 = null;
        }
        try {
            pulse = Float.parseFloat(editTextPulse.getText().toString());
        } catch (java.lang.NumberFormatException e) {
            pulse = null;
        }
        try {
            bloodPressureSYS = Float.parseFloat(editTextBloodPressureSYS.getText().toString());
        } catch (java.lang.NumberFormatException e) {
            bloodPressureSYS = null;
        }
        try {
            bloodPressureDIA = Float.parseFloat(editTextBloodPressureDia.getText().toString());
        } catch (java.lang.NumberFormatException e) {
            bloodPressureDIA = null;
        }
        try {
            temperatue = Float.parseFloat(editTextTemperature.getText().toString());
        } catch (java.lang.NumberFormatException e) {
            temperatue = null;
        }

        if (((date.equals("")) || (time.equals(""))) || (user.equals(""))) {
            Snackbar.make(view, "User is null", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            char[] dateTemp = new char[16];
            date.getChars(6, 10, dateTemp, 0);
            dateTemp[4] = '-';
            date.getChars(3, 5, dateTemp, 5);
            dateTemp[7] = '-';
            date.getChars(0, 2, dateTemp, 8);
            dateTemp[10] = ' ';
            time.getChars(0, 5, dateTemp, 11);
            dateFinal = new String(dateTemp);

            DatabaseHandler dbHelper = new DatabaseHandler(view.getContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseHandler.TableInfo.COLUMN_NAME_DATE, dateFinal);
            values.put(DatabaseHandler.TableInfo.COLUMN_NAME_USER_NAME, user);
            values.put(DatabaseHandler.TableInfo.COLUMN_NAME_NOTES, notes);
            values.put(DatabaseHandler.TableInfo.COLUMN_NAME_WEIGHT, weight);
            values.put(DatabaseHandler.TableInfo.COLUMN_NAME_SP02, sp02);
            values.put(DatabaseHandler.TableInfo.COLUMN_NAME_PULSE_RATE, pulse);
            values.put(DatabaseHandler.TableInfo.COLUMN_NAME_BLOOD_PRESSURE_SYS, bloodPressureSYS);
            values.put(DatabaseHandler.TableInfo.COLUMN_NAME_BLOOD_PRESSURE_DIA, bloodPressureDIA);
            values.put(DatabaseHandler.TableInfo.COLUMN_NAME_TEMPERATURE, temperatue);

            db.insert(
                    DatabaseHandler.TableInfo.TABLE_NAME,
                    null,
                    values);
        }


    }


}