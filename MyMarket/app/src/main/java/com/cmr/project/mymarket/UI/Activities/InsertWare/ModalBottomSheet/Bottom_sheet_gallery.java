package com.cmr.project.mymarket.UI.Activities.InsertWare.ModalBottomSheet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.cmr.project.mymarket.R;
import com.cmr.project.mymarket.UI.Activities.AddNewCarrier;
import com.cmr.project.mymarket.UI.Activities.InsertWare.ListImageSelectedActivity;
import com.cmr.project.mymarket.UI.Activities.InsertWare.Picture;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class Bottom_sheet_gallery extends BottomSheetDialogFragment {

    public  List<Picture> pictures;

    public Bottom_sheet_gallery(ArrayList<Picture> picturesSelected) {
        this.pictures = new ArrayList<>();
        this.pictures.addAll(picturesSelected);
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        View v = inflater.inflate(R.layout.bottom_sheet_gallery, container, false);
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        RecyclerView recyclerView_produit = getView().findViewById(R.id.my_recycler_bottomsheet);
        BottomSheet_Adapter bottomSheet_adapter = new BottomSheet_Adapter(this.getContext(), pictures);
        recyclerView_produit.setLayoutManager((new GridLayoutManager(this.getContext(),3)));
        recyclerView_produit.setAdapter(bottomSheet_adapter);

        ItemTouchHelperImpl swipeAndDragHelper = new ItemTouchHelperImpl(bottomSheet_adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeAndDragHelper);
        touchHelper.attachToRecyclerView(recyclerView_produit);

        Button imageViewSendDetail = getView().findViewById(R.id.submit_selectedImg_bottomsheet);
        imageViewSendDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Botttooomm Sheett
                if(AddNewCarrier.isCallFromCarrier){
                    Intent intentDetail = new Intent(Bottom_sheet_gallery.this.getActivity(), AddNewCarrier.class);
                    intentDetail.putParcelableArrayListExtra("listpicture", (ArrayList<? extends Parcelable>) pictures);
                    AddNewCarrier.isCallFromCarrier = false;
                    startActivity(intentDetail);

                }else{
                    Intent intentDetail = new Intent(Bottom_sheet_gallery.this.getActivity(), ListImageSelectedActivity.class);
                    intentDetail.putParcelableArrayListExtra("listpicture", (ArrayList<? extends Parcelable>) pictures);
                    startActivity(intentDetail);
                }


            }
        });

    }
}
