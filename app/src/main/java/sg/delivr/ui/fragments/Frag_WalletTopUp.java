package sg.delivr.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.stripe.android.Stripe;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sg.delivr.Common.StoredDatas;
import sg.delivr.R;
import sg.delivr.backend.RetrofitClient;
import sg.delivr.backend.postmodels.PostMerchantAuth;
import sg.delivr.backend.postmodels.PostWalletPaymentIntent;
import sg.delivr.backend.responsemodels.ResponseGetWallet;
import sg.delivr.backend.responsemodels.ResponsePaymentIntentClientSecret;
import sg.delivr.ui.activity.CheckOutActivity;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;
import sg.delivr.utils.CheckNetwork;
import sg.delivr.utils.Prefs;
import sg.delivr.utils.Utils;


public class Frag_WalletTopUp extends Fragment implements View.OnClickListener, SHAInterface,
        Intent_Constants {
    Toolbar dash_toolbar;
    TextView toolbar_title, total_amount;
    EditText edt_amount;
    String userId, checkout_amount, total_creditamount;
    ProgressDialog progressDialog;
    LinearLayout submit_walletcheckout;
    private Call<ResponseGetWallet> callGetWalletAmount;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_wallet_topup, container, false);

        toolbarInit();
        initView(view);
        initiateProgress();

        return view;
        }

    private void initiateProgress() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        if (!CheckNetwork.isInternetAvailable(getActivity()))  //if connection available
        {
            AlertBox(getResources().getString(R.string.error), getResources().getString(R.string.network));
        }
        getWalletCreditAmount(userId);

    }


    private void initView(View view) {
        userId = Prefs.getUserId();
        total_amount = view.findViewById(R.id.total_amount);
        submit_walletcheckout = view.findViewById(R.id.submit_walletcheckout);
        edt_amount = view.findViewById(R.id.edt_amount);
        submit_walletcheckout.setOnClickListener(this);
        total_amount.setText(Prefs.getWalletTotalCreditAmount());
    }

    private void toolbarInit() {
        dash_toolbar = getActivity().findViewById(R.id.dash_toolbar);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.txt_wallet_topup));
        dash_toolbar.setVisibility(View.VISIBLE);
    }

    private void getWalletCreditAmount(String UserId) {
        Log.e("rewards_app_new", "Member_Id: " + Prefs.getUserId());
        progressDialog.show();
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign = UserId + Strapikey + Strapicode;
        String StrSignature = Utils.SHA1(sign);

        callGetWalletAmount = RetrofitClient.getInstance().getApiInterface().getWallet(
                new PostMerchantAuth(UserId,Strapikey,StrSignature));

        callGetWalletAmount.enqueue(new Callback<ResponseGetWallet>() {
            @Override
            public void onResponse(Call<ResponseGetWallet> call,
                                   Response<ResponseGetWallet> response) {
                progressDialog.dismiss();

                if (response.body() != null) {
                    total_creditamount = response.body().getCreditAmt();
                    Prefs.setWalletTotalCreditAmount(total_creditamount);
                    total_amount.setText(total_creditamount);
                } else {
                    Utils.showGenericErrorDialog(getActivity());
                }
            }
            @Override
            public void onFailure(Call<ResponseGetWallet> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Utils.showGenericErrorDialog(getActivity());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_walletcheckout:
                checkout_amount = edt_amount.getText().toString();
                if (checkout_amount.equalsIgnoreCase("")) {
                    AlertBox_text(getResources().getString(R.string.alert), getResources().getString(R.string.entertopupamount), edt_amount);
                    return;
                }

                StoredDatas.getInstance().setCheckOutAmount(checkout_amount);
                edt_amount.setText("");
                Intent checkout_intent = new Intent(getActivity(), CheckOutActivity.class);
                startActivity(checkout_intent);
                break;

        }
    }

    public void AlertBox(final String head, final String meg) {
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
                }
            }
        });
        dialog.show();
    }

    public void AlertBox_text(final String head, final String meg, final EditText ext_field) {
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
                    ext_field.requestFocus();
                }
            }
        });
        dialog.show();
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RIDER_MYJOBS_to_MYJOB_DETAILS) {
            if(resultCode == MYJOBSAQ_success) {
                String listAQPos = data.getStringExtra("AQPosition");
                String jobstatus = data.getStringExtra("AQStatus");
                int position = Integer.parseInt(listAQPos);
                Log.e("delivrApp", "LastPos: " + position  + "Job Status:" +  jobstatus);

                if (jobstatus.equalsIgnoreCase("Del")) {
                    riderQueues.remove(position);
                    myjobsAdapter.notifyDataSetChanged();
                } else {
                    String chkstatus = "";
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Please wait..");
                    progressDialog.setCancelable(false);
                    if (jobstatus.equalsIgnoreCase("Pic")) {
                        gatherRiders();
                    }
                    riderQueues.get(position).setStatus(jobstatus);
                    StoredDatas.getInstance().setRiderQueues(riderQueues);
                    riderQueues = StoredDatas.getInstance().getRiderQueues();
                    Log.e("delivrApp", "Inside My Jobs get current data : Check Status:" + chkstatus + "New Status:"  + riderQueues.get(position).getStatus()+
                            "Status Code:" +riderQueues.get(position).getStatusCode() + "Delivery_CO" + riderQueues.get(position).getDelivery_CO());
                    if (riderQueues.get(position).getStatusCode().equals("RidAcc") || riderQueues.get(position).getStatusCode().equals("PassAcc")
                            || riderQueues.get(position).getStatusCode().equals("PassRej")) {
                        chkstatus = riderQueues.get(position).getLastStatusCode();
                    } else {
                        chkstatus = riderQueues.get(position).getStatusCode();
                    }
                    Log.e("delivrApp", "Inside My Jobs else : Check Status:" + chkstatus + "New Statusd:"  + riderQueues.get(position).getStatus() + "Status Code:" +
                            riderQueues.get(position).getLastStatusCode() + "Delivery_CO" + riderQueues.get(position).getDelivery_CO());
                    myjobsAdapter.notifyDataSetChanged();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
