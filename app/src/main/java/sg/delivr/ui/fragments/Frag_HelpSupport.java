package sg.delivr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import sg.delivr.R;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;


public class Frag_HelpSupport extends Fragment implements View.OnClickListener, SHAInterface,
        Intent_Constants {

    Toolbar dash_toolbar;
    TextView toolbar_title;
    EditText input_name, input_email, input_mobile, input_message;
    LinearLayout submit_help;
    String strName = "", strEmail = "", strMobile = "", strMessage = "";
    String str_emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_helpsupport, container, false);

        toolbarInit();
        initView(view);


        return view;
        }

    private void initView(View view) {
        input_name = view.findViewById(R.id.input_name);
        input_email = view.findViewById(R.id.input_email);
        input_mobile = view.findViewById(R.id.input_mobile);
        input_message = view.findViewById(R.id.input_message);
        submit_help = view.findViewById(R.id.submit_help);
        submit_help.setOnClickListener(this);
    }

    private void toolbarInit() {
        dash_toolbar = getActivity().findViewById(R.id.dash_toolbar);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.txt_myorders));
        dash_toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_help:
                strName = input_name.getText().toString().trim();
                strEmail = input_email.getText().toString().trim();
                strMobile = input_mobile.getText().toString().trim();
                strMessage = input_message.getText().toString().trim();

                if(strName.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter your Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strEmail.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter your Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strMobile.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter your Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strMessage.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please enter your Message", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!strEmail.trim().matches(str_emailPattern)) {
                    Toast.makeText(getActivity(), "Please enter valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getActivity(), "Help & Support will impletent Later.", Toast.LENGTH_SHORT).show();

                break;
        }
    }

}
