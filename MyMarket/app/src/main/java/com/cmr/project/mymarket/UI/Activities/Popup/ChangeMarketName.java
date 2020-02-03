package com.cmr.project.mymarket.UI.Activities.Popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.cmr.project.mymarket.R;

import java.util.ArrayList;

public class ChangeMarketName extends AppCompatDialogFragment {

    Spinner spinner_marketName;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_change_market,null);



        builder.setView(view)
                .setTitle(getResources().getString(R.string.changeMarketNameTitle))
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String marketChosen = spinner_marketName.getSelectedItem().toString();
                        saveMarketInApp(marketChosen);
                    }
                });
        handleSpinnerMarketName(view);
        return builder.create();
    }

    private void saveMarketInApp(String marketChosen) {
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences("saveMarketName",Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("marketName", marketChosen);
        prefsEditor.apply();
    }

    public void handleSpinnerMarketName(View view){
        ArrayList<String> listDropdownItems = new ArrayList<>();
        listDropdownItems.add(getResources().getString(R.string.marche_mokolo));
        listDropdownItems.add(getResources().getString(R.string.marche_mfoundi));
        listDropdownItems.add(getResources().getString(R.string.marche_central));
        listDropdownItems.add(getResources().getString(R.string.marche_mvog_bi));
        listDropdownItems.add(getResources().getString(R.string.marche_mvog_betsi));
        listDropdownItems.add(getResources().getString(R.string.marche_de_huitieme));
        listDropdownItems.add(getResources().getString(R.string.marche_de_nsam));
        listDropdownItems.add(getResources().getString(R.string.marche_de_nkol_eton));
        listDropdownItems.add(getResources().getString(R.string.marche_de_mendong));
        listDropdownItems.add(getResources().getString(R.string.marche_de_melen));
        listDropdownItems.add(getResources().getString(R.string.marche_de_ekounou));
        listDropdownItems.add(getResources().getString(R.string.marche_de_biyem_assi));

        ArrayAdapter<String> adapterDropdown = new ArrayAdapter<String>(this.getContext(),R.layout.support_simple_spinner_dropdown_item,listDropdownItems);
        spinner_marketName = view.findViewById(R.id.market_name_change_dropdown);
        spinner_marketName.setAdapter(adapterDropdown);
    }

}
