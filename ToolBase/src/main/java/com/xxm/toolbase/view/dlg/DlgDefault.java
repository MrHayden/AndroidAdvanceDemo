package com.xxm.toolbase.view.dlg;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDButton;
import com.xxm.toolbase.R;

public class DlgDefault extends MaterialDialog {

    DlgDefault(Builder builder) {
        super(builder);
        MDButton positiveButton = getActionButton(DialogAction.POSITIVE);
        MDButton negativeButton = getActionButton(DialogAction.NEGATIVE);
        MDButton neutralButton = getActionButton(DialogAction.NEUTRAL);
        setTypeface(positiveButton, builder.getRegularFont());
        setTypeface(negativeButton, builder.getRegularFont());
        setTypeface(neutralButton, builder.getRegularFont());
        setTypeface(title, builder.getRegularFont());

        negativeButton.setTextColor(builder.getContext().getResources().getColor(R.color.transparent));
        positiveButton.setTextColor(builder.getContext().getResources().getColor(R.color.transparent));
        neutralButton.setTextColor(builder.getContext().getResources().getColor(R.color.transparent));
    }
}
