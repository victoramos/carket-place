package com.example.victor.carketplace.view.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.victor.carketplace.R;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class ConfirmationDialog extends DialogFragment {
    private Integer mAmount = 1;
    private Integer mTotal = 0;

    public Subject<Integer> resultSubject = PublishSubject.create();

    public static ConfirmationDialog newInstance(String title, Integer total) {
        ConfirmationDialog frag = new ConfirmationDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("total", total);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_confirmation_dialog, container);

        final TextView amount = view.findViewById(R.id.text_amount);
        TextView title = view.findViewById(R.id.title);
        ImageView buttonAdd = view.findViewById(R.id.image_add);
        ImageView buttonRemove = view.findViewById(R.id.image_remove);
        ImageView buttonClose = view.findViewById(R.id.image_close);
        Button confirm = view.findViewById(R.id.button_confirm);

        confirm.setOnClickListener((v) -> resultSubject.onNext(mAmount));

        buttonAdd.setOnClickListener(v -> amount.setText(String.valueOf(add())));
        buttonRemove.setOnClickListener(v -> amount.setText(String.valueOf(remove())));
        buttonClose.setOnClickListener(v -> ConfirmationDialog.this.dismiss());

        if(getArguments() != null){
            title.setText(getArguments().getString("title"));
            mTotal = getArguments().getInt("total");
        }

        return view;
    }

    private Integer add(){
        if(mAmount.equals(mTotal)){
            return mAmount;
        }

        mAmount++;
        return mAmount;
    }

    private Integer remove(){
        if(mAmount == 1){
            return mAmount;
        }

        mAmount--;
        return mAmount;
    }
}
