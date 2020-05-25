package com.delivr.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.delivr.R;
import com.delivr.backend.RetrofitClient;
import com.delivr.backend.postmodels.PostGetProfile;
import com.delivr.backend.responsemodels.ResponseUserProfile;
import com.delivr.utils.CheckNetwork;
import com.delivr.utils.Prefs;
import com.delivr.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frag_MyProfile extends Fragment implements View.OnClickListener {

    TextView toolbar_title, label_fullname, label_role, label_emailid, label_address, label_mobileno,
            label_phoneno, label_doj, label_fax, label_companyname, label_nric, label_ridertype,
            label_zipcode;
    Toolbar dash_toolbar;
    View label_company_view;
    EditText edt_fullname_prof;
    EditText edt_role_prof;
    EditText edt_emailid_prof;
    EditText edt_address_prof;
    EditText edt_mobile_prof;
    EditText edt_phoneno_prof;
    EditText edt_doj_prof;
    EditText edt_fax_prof;
    EditText edt_companyname_prof;
    EditText edt_nricacra_prof;
    EditText edt_ridertype_prof, edt_zipcode_prof;
    String fullname, role, emailid, address, mobile_no,
            phoneno, dateofjoin, fax, companyname,nricfin_acra, ridertype, zipcode, UserId;
    ProgressDialog progressDialog;
    Context context;
    private Call<ResponseUserProfile> callGetProfile;
    View view;
    //BottomNavigationView bottom_nav;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_myprofile, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        context = getActivity();


        if (!CheckNetwork.isInternetAvailable(getActivity()))  //if connection available
        {
            AlertBox(getResources().getString(R.string.error), getResources().getString(R.string.network), edt_fullname_prof);
        }
        toolbarInit(view);

        return view;
        }

    private void toolbarInit(View view) {
        dash_toolbar = getActivity().findViewById(R.id.dash_toolbar);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.txt_myprofile));
        dash_toolbar.setVisibility(View.VISIBLE);
        edt_fullname_prof = (EditText) view.findViewById(R.id.edt_fullname_prof);
        edt_role_prof = (EditText) view.findViewById(R.id.edt_role_prof);
        edt_emailid_prof = (EditText) view.findViewById(R.id.edt_emailid_prof);
        edt_address_prof = (EditText) view.findViewById(R.id.edt_address_prof);
        edt_mobile_prof = (EditText) view.findViewById(R.id.edt_mobile_prof);
        edt_phoneno_prof = (EditText) view.findViewById(R.id.edt_phoneno_prof);
        edt_doj_prof = (EditText) view.findViewById(R.id.edt_doj_prof);
        edt_fax_prof = (EditText) view.findViewById(R.id.edt_fax_prof);
        edt_companyname_prof = (EditText) view.findViewById(R.id.edt_companyname_prof);
        edt_nricacra_prof = (EditText) view.findViewById(R.id.edt_nricacra_prof);
        edt_ridertype_prof = (EditText) view.findViewById(R.id.edt_ridertype_prof);
        edt_zipcode_prof = (EditText) view.findViewById(R.id.edt_zipcode_prof);

        label_companyname = view.findViewById(R.id.label_companyname);
        label_company_view = view.findViewById(R.id.company_view);
        label_ridertype = view.findViewById(R.id.label_ridertype);
        label_phoneno = view.findViewById(R.id.label_phoneno);
        label_doj = view.findViewById(R.id.label_doj);
        label_fax = view.findViewById(R.id.label_fax);
        label_nric = view.findViewById(R.id.label_nric);
        /*bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        bottom_nav.setVisibility(View.VISIBLE);*/

        UserId = Prefs.getUserId();
        getProfile(UserId);
    }

    public void AlertBox(final String head, final String meg, final EditText ext_field) {
        LayoutInflater in = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vv = in.inflate(R.layout.alertbox, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(vv);
        dialog.setCancelable(false);
        TextView content = (TextView) vv.findViewById(R.id.content);
        TextView header = (TextView) vv.findViewById(R.id.header);
        header.setText(head);
        TextView no = (TextView) vv.findViewById(R.id.no);
        TextView yes = (TextView) vv.findViewById(R.id.yes);
        LinearLayout cancel = (LinearLayout) vv.findViewById(R.id.cancel);
        cancel.setVisibility(View.GONE);
        LinearLayout ok = (LinearLayout) vv.findViewById(R.id.ok);
        content.setText(meg);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (head.equalsIgnoreCase("Error")) {
                    getActivity().finish();
                } else if (head.equalsIgnoreCase("Alert")) {
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    if (ext_field != null) {
                        ext_field.requestFocus();
                    }

                }
            }
        });
        dialog.show();
    }

    private void getProfile(String UserId) {
        Log.e("rewards_app_new", "Member_Id: " + Prefs.getUserId());
        progressDialog.show();
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign = UserId + Strapikey + Strapicode;
        String StrSignature = Utils.SHA1(sign);
        callGetProfile = RetrofitClient.getInstance().getApiInterface().getProfile(
                new PostGetProfile(
                        UserId ,
                        Strapikey, StrSignature));

        callGetProfile.enqueue(new Callback<ResponseUserProfile>() {
            @Override
            public void onResponse(Call<ResponseUserProfile> call,
                                   Response<ResponseUserProfile> response) {
                progressDialog.dismiss();

                if (response.body() != null) {
                    if (response.body().getMessage().equalsIgnoreCase("success")) {
                        fullname = response.body().getFullname();
                        role = response.body().getRole();
                        emailid = response.body().getEmail();
                        address = response.body().getAddress();
                        mobile_no = response.body().getMobileno();
                        phoneno = response.body().getPhoneno();
                        dateofjoin = response.body().getDOJ();
                        fax = response.body().getFAX();
                        companyname = response.body().getCompanyName();
                        nricfin_acra = response.body().getNRICFIN_ACRA();
                        ridertype = response.body().getRiderType();
                        zipcode = response.body().getZipcode();

                        updateUI();



                    } else {
                        Utils.showMessageDialog(getActivity(),
                                getString(R.string.dialog_title_sorry),
                                response.body().getMessage());
                    }
                } else {
                    Utils.showMessageDialog(getActivity(),
                            getString(R.string.dialog_title_sorry),
                            response.body().getMessage());
                }
            }



            @Override
            public void onFailure(Call<ResponseUserProfile> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Utils.showGenericErrorDialog(getActivity());
            }
        });
    }
    public void updateUI() {
            edt_fullname_prof.setText(fullname);
            edt_role_prof.setText(role);
            edt_emailid_prof.setText(emailid);
            edt_address_prof.setText(address);
            edt_mobile_prof.setText(mobile_no);
            edt_phoneno_prof.setText(phoneno);
            edt_doj_prof.setText(dateofjoin);
            edt_fax_prof.setText(fax);
            edt_companyname_prof.setText(companyname);
            edt_nricacra_prof.setText(nricfin_acra);
            edt_ridertype_prof.setText(ridertype);
            edt_zipcode_prof.setText(zipcode);
        if (role.equalsIgnoreCase("Rider")) {
            label_companyname.setVisibility(View.GONE);
            edt_companyname_prof.setVisibility(View.GONE);
            label_company_view.setVisibility(View.GONE);
            edt_doj_prof.setVisibility(View.VISIBLE);
            label_doj.setVisibility(View.VISIBLE);
            label_ridertype.setVisibility(View.VISIBLE);
            label_phoneno.setText("Emergency Contact No");
            label_fax.setText("Alternate Phone No");
            label_nric.setText("NRICFIN");
        } else {
            label_companyname.setVisibility(View.VISIBLE);
            edt_companyname_prof.setVisibility(View.VISIBLE);
            label_company_view.setVisibility(View.VISIBLE);
            label_ridertype.setVisibility(View.GONE);
            label_doj.setVisibility(View.GONE);
            edt_doj_prof.setVisibility(View.GONE);
            label_phoneno.setText("Landline No ");
            label_fax.setText("FAX ");
            label_nric.setText("ACRA-BRN ");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
