package com.example.victor.carketplace.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import com.example.victor.carketplace.R;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class UnknownErrorDialog {
    private Subject<Void> resultSubject = PublishSubject.create();

    public AlertDialog getDialog(Context context){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.unknow_error_title)
                .setMessage(R.string.unknow_error_body);

        dialog.setPositiveButton(R.string.unknow_error_positive_button, (dialog1, id) -> {
            dialog1.dismiss();
            resultSubject.onComplete();
        });
        return dialog.create();
    }

    public Observable<Void> getObservable(){
        return resultSubject;
    }
}
