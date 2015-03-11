package com.rubyhuntersky.gx.support;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author wehjin
 * @since 2/10/15.
 */

public class EditTextDialogFragment extends DialogFragment {

    public static final String IS_NEW_ARG_KEY = "is-new-arg-key";
    public static final String TEXT_ARG_KEY = "text-arg-key";
    private EditText editText;

    public static EditTextDialogFragment newInstance(String text, boolean isNew) {
        Bundle bundle = new Bundle();
        bundle.putString(TEXT_ARG_KEY, text);
        bundle.putBoolean(IS_NEW_ARG_KEY, isNew);
        EditTextDialogFragment fragment = new EditTextDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        editText = new EditText(getActivity()) {

            @Override
            public boolean onKeyPreIme(int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    getDialog().dismiss();
                    return true;
                }
                return super.onKeyPreIme(keyCode, event);
            }
        };
        editText.setInputType(
              InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setText(getArguments().getString(TEXT_ARG_KEY));
        editText.setTag("dialog");
        editText.setBackgroundColor(Color.WHITE);
        editText.setGravity(Gravity.TOP | Gravity.START);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getDialog().getWindow()
                               .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing.
            }

            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString();
                Listener listener = getListener();
                if (listener != null) {
                    listener.onEditTextDialogTextChange(newText);
                }
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                dismiss();
                return false;
            }
        });
        return editText;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Listener listener = getListener();
        if (listener != null) {
            listener.onEditTextDialogDismiss(editText.getText().toString());
        }
        super.onDismiss(dialog);
    }

    private Listener getListener() {
        Activity activity = getActivity();
        return activity instanceof Listener ? (Listener) activity : null;
    }

    @Override
    public void onStart() {
        super.onStart();
        editText.requestFocus();
        if (getArguments().getBoolean(IS_NEW_ARG_KEY)) {
            editText.selectAll();
        } else {
            editText.setSelection(editText.getText().length());
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public interface Listener {
        void onEditTextDialogTextChange(String text);
        void onEditTextDialogDismiss(String text);
    }
}
