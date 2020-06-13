package sg.delivr.ui.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sg.delivr.R;
import sg.delivr.backend.RetrofitClient;
import sg.delivr.backend.postmodels.PostDoCreateOrder;
import sg.delivr.backend.responsemodels.ResponseCreateOrder;
import sg.delivr.ui.activity.SearchLocation;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;
import sg.delivr.utils.CheckNetwork;
import sg.delivr.utils.Prefs;
import sg.delivr.utils.Utils;


public class Frag_MyOrders_New extends Fragment implements View.OnClickListener, SHAInterface,
        Intent_Constants {


    Toolbar dash_toolbar;
    TextView toolbar_title;
    ProgressDialog progressDialog;
    LinearLayout pickup_layout, delivery_layout, delivery_datas_layout;
    TextView pickup_txt, delivery_txt;
    ImageView delivery_sel_icon, pickup_sel_icon;
    int dVisible = 0;
    EditText edt_dlvryaddr_name, edt_dlvryaddr_email,  edt_dlvryaddr_unitno, edt_dlvryaddr_contact,
            edt_fooddetails, edt_delivryinstruction;
    LinearLayout submit_createorder;
    static TextView edt_pickupdatetime, txt_pickuptime;
    private Call<ResponseCreateOrder> callCreateOrder;
    String pickupsender_name, SenderId, pickupcompanyname, pickupsameas, pickupadddress, pickupadddress_unitno, pickupadddress_contact,
            deliverysameas, deliveryaddress_name, deliveryzipcode,consignmentType,serviceType,delivery_co,status,credit,
            deliveryaddress_email, deliveryaddress, deliveryaddress_unitno, deliveryaddress_contact,
            fooddetails, deliveryinstruction, result_status;
    String userId;
    private static String format = "", pickupdatetime = "", selectedpickupdate = "", selectedpickuptime = "";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_myorders_new, container, false);

        toolbarInit();
        initView(view);
        initiateProgress();

        return view;
    }

    private void initView(View view) {
        userId = Prefs.getUserId();

        pickup_layout = view.findViewById(R.id.pickup_layout);
        delivery_layout = view.findViewById(R.id.delivery_layout);
        delivery_datas_layout = view.findViewById(R.id.delivery_datas_layout);
        delivery_txt = view.findViewById(R.id.delivery_txt);
        pickup_txt = view.findViewById(R.id.pickup_txt);
        pickup_sel_icon = view.findViewById(R.id.pickup_sel_icon);
        delivery_sel_icon = view.findViewById(R.id.delivery_sel_icon);
        delivery_datas_layout.setVisibility(View.GONE);
        edt_dlvryaddr_name = view.findViewById(R.id.edt_dlvryaddr_name);
        edt_dlvryaddr_email = view.findViewById(R.id.edt_dlvryaddr_email);
        edt_dlvryaddr_unitno = view.findViewById(R.id.edt_dlvryaddr_unitno);
        edt_dlvryaddr_contact = view.findViewById(R.id.edt_dlvryaddr_contact);
        edt_delivryinstruction = view.findViewById(R.id.edt_delivryinstruction);
        edt_fooddetails = view.findViewById(R.id.edt_fooddetails);
        edt_pickupdatetime = view.findViewById(R.id.edt_pickupdatetime);
        txt_pickuptime = view.findViewById(R.id.txt_pickuptime);
        submit_createorder = view.findViewById(R.id.submit_createorder);
        submit_createorder.setOnClickListener(this);
        edt_pickupdatetime.setOnClickListener(this);
        txt_pickuptime.setOnClickListener(this);
        pickupdatetime = "";
        setValues();
       // pickup_txt.setOnClickListener(this);
        delivery_txt.setOnClickListener(this);
        delivery_sel_icon.setOnClickListener(this);
        pickup_sel_icon.setOnClickListener(this);
    }

    private void setValues() {
        Log.e("delivrApp", "Company Name:" + Prefs.getUserCompanyName() + "Address" + Prefs.getUserAddress() +
                "Unit No:" + Prefs.getUserUnitNo() + "PostalCode:" + Prefs.getUserPostalCode() + "Contact:" + Prefs.getUserMobileNo());
        pickup_txt.setText(Prefs.getUserAddress());
        pickupsender_name = Prefs.getSenderName();
        pickupcompanyname = Prefs.getUserCompanyName();
    }

    private void resetValues() {
        pickup_txt.setText(Prefs.getUserAddress());
        delivery_txt.setText("");
        edt_dlvryaddr_name.setText("");
        edt_dlvryaddr_contact.setText("");
        edt_dlvryaddr_email.setText("");
        edt_dlvryaddr_unitno.setText("");
        edt_pickupdatetime.setText("");
        txt_pickuptime.setText("");
        edt_fooddetails.setText("");
        edt_delivryinstruction.setText("");
    }


    private void toolbarInit() {
        dash_toolbar = getActivity().findViewById(R.id.dash_toolbar);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.txt_myorders));
        dash_toolbar.setVisibility(View.VISIBLE);
    }

    private void initiateProgress() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        if (!CheckNetwork.isInternetAvailable(getActivity()))  //if connection available
        {
            AlertBox(getResources().getString(R.string.error), getResources().getString(R.string.network));
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pickup_txt:
                Intent pickupIntent = new Intent(getActivity(), SearchLocation.class);
                startActivityForResult(pickupIntent, MYORDERS_to_PICKUP);
                break;

            case R.id.delivery_txt:
                Intent deliveryIntent = new Intent(getActivity(), SearchLocation.class);
                startActivityForResult(deliveryIntent, MYORDERS_to_DELIVERY);
                break;

            case R.id.delivery_sel_icon:
                if(dVisible == 0) {
                    dVisible = 1;
                    delivery_datas_layout.setVisibility(View.VISIBLE);

                } else {
                    dVisible = 0;
                    delivery_datas_layout.setVisibility(View.GONE);

                }

                break;

            case R.id.pickup_sel_icon:

                break;
            case R.id.submit_createorder:
                validation();
                break;
            case R.id.edt_pickupdatetime:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
                break;
            case R.id.txt_pickuptime:
                DialogFragment newFragment1 = new TimePickerFragment();
                newFragment1.show(getFragmentManager(), "timePicker");
                break;
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            // pickerDialog.getDatePicker().setSpinnersShown(true);
            //  pickerDialog.getDatePicker().setCalendarViewShown(false);
            //  pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // pickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            return pickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Calendar c = Calendar.getInstance();

            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            edt_pickupdatetime.setText("");

            String myFormat = "MMM dd yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            selectedpickupdate = "" + sdf.format(c.getTime());
            Log.e("delivrApp", "Selected PickUp Date" + selectedpickupdate);
            txt_pickuptime.performClick();
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            showTime(hour, min);
        }
    }

    public static void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        selectedpickuptime = "" + hour + ":" + min + " "+ format;
        Log.e("delivrApp", "Selected PickUp Time" + selectedpickuptime);
        edt_pickupdatetime.setText(selectedpickupdate + " " + selectedpickuptime);
        pickupdatetime = selectedpickupdate + " " + selectedpickuptime;
        txt_pickuptime.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }

    private void validation() {
        pickupsender_name = Prefs.getSenderName();
        SenderId = Prefs.getUserId();
        pickupcompanyname = Prefs.getUserCompanyName();
        pickupsameas = "1";
        pickupadddress_unitno = Prefs.getUserUnitNo();
        pickupadddress_contact = Prefs.getUserMobileNo();
        pickupadddress = pickup_txt.getText().toString().trim();
        deliverysameas = "0";
        consignmentType = Prefs.getMerchAuthConsignmentType();
        serviceType = Prefs.getMerchAuthServiceType();
        delivery_co = "0";
        status = "New";
        credit = "0";

        deliveryaddress_name = edt_dlvryaddr_name.getText().toString().trim();
        deliveryaddress_email = edt_dlvryaddr_email.getText().toString().trim();
        deliveryaddress = delivery_txt.getText().toString().trim();
        deliveryaddress = deliveryaddress.replaceAll("\\s+"," ");
        String[] splitStr = deliveryaddress.trim().split("\\s+");
        int strlength = splitStr.length;
        if (strlength >0) {
            deliveryzipcode = splitStr[strlength-1].toString();
        }
        Log.e("delivrApp", "selected Address:" + deliveryaddress + "Zipcode" + deliveryzipcode);
        deliveryaddress_unitno = edt_dlvryaddr_unitno.getText().toString().trim();
        deliveryaddress_contact = edt_dlvryaddr_contact.getText().toString().trim();
        //pickupdatetime = edt_pickupdatetime.getText().toString().trim();
        fooddetails = edt_fooddetails.getText().toString().trim();
        deliveryinstruction = edt_delivryinstruction.getText().toString().trim();
        if (deliveryaddress.isEmpty() && deliveryaddress.equalsIgnoreCase("")) {
            AlertBox(getResources().getString(R.string.alert), getResources().getString(R.string.enterdlvryaddr), edt_dlvryaddr_name);
            return;
        }
        if (deliveryaddress_name.isEmpty() && deliveryaddress_name.equalsIgnoreCase("")) {
            AlertBox(getResources().getString(R.string.alert), getResources().getString(R.string.enterdlvryaddrname), edt_dlvryaddr_name);
            return;
        }
        if (pickupdatetime.isEmpty() && pickupdatetime.equalsIgnoreCase("")) {
            AlertBox(getResources().getString(R.string.alert), getResources().getString(R.string.enterdatetime), edt_dlvryaddr_name);
            return;
        }
        DocreateOrder();
    }

    private void DocreateOrder() {
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign =  userId + Strapikey + Strapicode;
        String StrSignature = SHAInterface.SHA1(sign);
        Log.e("delivrApp", "Signature: " + StrSignature);
        Log.e("delivrApp", "Sign: " + sign);
        Log.e("delivrApp", "StraipKey: " + Strapikey);
        Log.e("delivrApp", "StraipCode: " + Strapicode);

        progressDialog.show();
        callCreateOrder = RetrofitClient.getInstance().getApiInterface().doCreateOrder(
                new PostDoCreateOrder( "","",Prefs.getUserId(),pickupsameas, Prefs.getUserPostalCode(),
                        pickupadddress_unitno,pickupsender_name,pickupcompanyname,pickupadddress_contact,deliverysameas,
                        deliveryzipcode,deliveryaddress_unitno,deliveryaddress_name,deliveryaddress_contact,"",
                        consignmentType,serviceType,pickupdatetime,fooddetails,deliveryinstruction,delivery_co,status,"",
                        Prefs.getMerchAuthPrice(), credit, "",Prefs.getMerchAuthPrice(),"",
                        Prefs.getUserId(), Strapikey, StrSignature));

        callCreateOrder.enqueue(new Callback<ResponseCreateOrder>() {
            @Override
            public void onResponse(Call<ResponseCreateOrder> call,
                                   final Response<ResponseCreateOrder> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                        result_status = response.body().getStatusMessage();
                    AlertBox_result(getResources().getString(R.string.alert), result_status, edt_dlvryaddr_name);

                } else {
                    Utils.showMessageDialog(getActivity(),
                            getString(R.string.dialog_title_sorry),
                            "No Data found");
                }
            }


            @Override
            public void onFailure(Call<ResponseCreateOrder> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(getActivity());
            }
        });

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
                    ext_field.requestFocus();
                }
            }
        });
        dialog.show();
    }

    public void AlertBox_result(final String head, final String meg, final EditText ext_field) {
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
                    resetValues();
                } else {
                    dialog.dismiss();
                    resetValues();
                    ext_field.requestFocus();
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*if(requestCode == FRAG_MYORDERS_to_SEARCH_LOCATION) {
            if(resultCode == SEARCH_success) {
                String location_value = data.getStringExtra("location_value");
                Log.e("delivrApp", "Location: " + location_value);
                txt_address.setText(location_value);
            }
        }*/

        if(requestCode == MYORDERS_to_PICKUP) {
            if(resultCode == LOCATION_success) {
                String location_value = data.getStringExtra("location_value");
                pickup_txt.setText(location_value);
            }
        }

        if(requestCode == MYORDERS_to_DELIVERY) {
            if(resultCode == LOCATION_success) {
                String location_value = data.getStringExtra("location_value");
                delivery_txt.setText(location_value);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
