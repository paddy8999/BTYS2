package io.github.paddy8999.btys2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeListRowFragment extends Fragment {
    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home_list_row, container, false);

        TextView dateValue = (TextView) v.findViewById(R.id.dateValue);
        dateValue.setText(getArguments().getString("date"));

        TextView userValue = (TextView) v.findViewById(R.id.userValue);
        userValue.setText(getArguments().getString("user"));

        setTextViewValues("notes", R.id.notesValue);
        setTextViewValues("weight", R.id.weightValue);
        setTextViewValues("sp02", R.id.sp02Value);
        setTextViewValues("pulse", R.id.pulseValue);
        setTextViewValuesBloodPressure("bloodPressureSys", R.id.sysValue,"bloodPressureDia", R.id.diaValue);
        setTextViewValues("temperature", R.id.temperatureValue);

        return v;
    }

    private void setTextViewValues(String varName, int id){
        String temp = getArguments().getString(varName);
        TextView textView = (TextView) v.findViewById(id);
        if(temp!=null&&!(temp.isEmpty())){

            textView.setText(temp);
        }else{
            LinearLayout a  = (LinearLayout) textView.getParent();
            a.setVisibility(View.GONE);
        }

    }

    private void setTextViewValuesBloodPressure(String varNameSys, int idSys, String varNameDia, int idDia){
        String sys = getArguments().getString(varNameSys);
        String dia = getArguments().getString(varNameDia);
        TextView textViewSys = (TextView) v.findViewById(idSys);
        TextView textViewDia = (TextView) v.findViewById(idDia);

        if((sys==null||sys.isEmpty())&&(dia==null||dia.isEmpty())){
            LinearLayout a  = (LinearLayout) textViewDia.getParent().getParent();
            a.setVisibility(View.GONE);
            return;
        }
        textViewDia.setText("???");
        textViewSys.setText("???");

        if(sys!=null&&!(sys.isEmpty())){
            textViewSys.setText(sys);
        }

        if(dia!=null&&!(dia.isEmpty())){
            textViewDia.setText(dia);
        }
        return;
    }

}


