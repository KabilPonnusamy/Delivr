package sg.delivr.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import sg.delivr.R;


import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;


public class Utils {
    public static void showMessageDialog(Activity activity, String title, String message) {

        try {
            if (!TextUtils.isEmpty(title)) {
                new MaterialDialog.Builder(activity)
                        .title(title)
                        .content(message)
                        .positiveText(R.string.btn_ok)
                        .show();


            } else {
                new MaterialDialog.Builder(activity)
                        .content(message)
                        .positiveText(R.string.btn_ok)
                        .show();
            }
        } catch (Exception e) {
            // The exception is most likely:
            // Bad window token,
            // you cannot show a dialog before an Activity is created or after it's hidden.
            e.printStackTrace();
        }
    }

    public static void showGenericErrorDialog(Activity context) {

        try {
            if (context != null) {
                new MaterialDialog.Builder(context)
                        .title(R.string.dialog_title_sorry)
                        .content(R.string.dialog_error)
                        .positiveText(R.string.btn_ok)
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertToHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);

        java.util.Formatter fmt = new java.util.Formatter(sb);
        for (byte b : data) {
            fmt.format("%02x", b);
        }

        return sb.toString().toUpperCase();
    }

    public static String SHA1(String text) {
        byte[] sha1hash = new byte[40];
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            sha1hash = md.digest();
        } catch (Exception e) {
        }
        return convertToHex(sha1hash);
    }


    public static void showGenericErrorDialog(Context context) {

        try {
            if (context != null) {
                new MaterialDialog.Builder(context)
                        .title(R.string.dialog_title_sorry)
                        .content(R.string.dialog_error)
                        .positiveText(R.string.btn_ok)
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setupEditTextLabelsNew(final EditText editText, final TextView editTextLabel,
                                              final String hint, final int hintColorRes) {
        if (editText.getParent() != null) {
            ((View) editText.getParent()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.requestFocus();
                    InputMethodManager imm = (InputMethodManager)
                            editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            });
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String working = s.toString();
                boolean isValid = true;
                if (working.length() == 2 && before == 0) {
                    if (Integer.parseInt(working) < 1 || Integer.parseInt(working) > 12) {
                        isValid = false;
                    } else {
                        working += "/";
                        editText.setText(working);
                        editText.setSelection(working.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                editTextLabel.setTextColor(
                        ContextCompat.getColor(editTextLabel.getContext(), hintColorRes));
                editTextLabel.setText(hint);
				/*if (s.length() == 3) {
					s.append('/');
				}*/

                if (TextUtils.isEmpty(s.toString())) {
                    editTextLabel.setVisibility(View.GONE);
                } else {
                    //editTextLabel.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static Typeface getFontRalewayExtraBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans_ExtraBold.ttf");
    }


    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return android.util.Base64.encodeToString(stream.toByteArray(), android.util.Base64.NO_WRAP);
    }

}
