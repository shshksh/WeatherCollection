package com.mob.weathercollection;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.Field;

public class ChooseLocationDialog extends BottomSheetDialogFragment {

    String selectType;
    View requestView;
    NoticeDialogListener listener;

    public ChooseLocationDialog(View view, String selectType) {
        this.requestView = view;
        this.selectType = selectType;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        try {
            Field field = R.array.class.getField(selectType);
            String[] itemsArray = getResources().getStringArray(field.getInt(null));
            String[] ShowArray = new String[itemsArray.length];
            for (int i = 0; i < itemsArray.length; i++) {
                ShowArray[i] = itemsArray[i].split("_")[0];
            }
            builder.setItems(ShowArray, (dialogInterface, i) -> {
                listener.onDialogClick(requestView, itemsArray[i]);
            });
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return builder.create();
    }

    public interface NoticeDialogListener {
        public void onDialogClick(View requestView, String selectedItem);
    }
}
