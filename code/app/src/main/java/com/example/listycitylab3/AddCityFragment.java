package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public class AddCityFragment extends DialogFragment {
 // so bundles allow you to store information in packages if they need to be changed
    // private statics for bundle arguments
    private static String argCity = "";
    private static String argPos = "pos";
    private static String isEditArg = "isEdit";

    // editing information
    private City currentCity;
    private int editPos;
    private boolean iseditMode;

    interface  AddCityDialogListener {
        void addCity(City city);
        void editCity(City city, int pos);
    }

    // constructor for editing when given params
    public static AddCityFragment newInstance(City city, int pos) {
        AddCityFragment frag = new AddCityFragment(); // new frag
        Bundle args = new Bundle(); // create bundle arguments
        args.putSerializable(argCity, (Serializable) city); // "compressing" city object
        args.putInt(argPos, pos);
        args.putBoolean(isEditArg, true);
        frag.setArguments(args);
        return frag;
    }
    // constructor for editing with no params
    public static AddCityFragment newInstance() {
        AddCityFragment frag = new AddCityFragment();
        Bundle args = new Bundle();
        args.putBoolean(isEditArg, false);
        frag.setArguments(args);
        return frag;
    }
    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + "must implement AddCityDialogListener");
        }

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get data from bundle args
        Bundle args = getArguments();
        if (args != null) {
            iseditMode = args.getBoolean(isEditArg, false);
            if (iseditMode) {
                currentCity = (City) args.getSerializable(argCity);
                editPos = args.getInt(argPos, -1);
            }
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        // creating the form box
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // if is edit mode and city exits
        if (iseditMode && currentCity != null) {
            builder.setTitle("Edit City");
            editCityName.setText(currentCity.getName());
            editProvinceName.setText(currentCity.getProvince());
        } else {
            builder.setTitle("Add a city");
        }

        return builder
                // return view with conditional
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(iseditMode? "Update" : "Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (!cityName.isEmpty() && !provinceName.isEmpty()) {
                        City city = new City(cityName, provinceName);
                        if (iseditMode) {
                            listener.editCity(city, editPos);
                        } else {
                            listener.addCity(city);
                        }
                    }
                })
                .create();
    }
}
