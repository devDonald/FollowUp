package com.godlife.followup.first_timers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.godlife.followup.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.Calendar;

public class AddFirstTimers extends AppCompatActivity {
    private EditText mName, mDob,mAddress,mEmail,mOccupation,mPhone,mSupervisor,mVisitDate;
    private Spinner mGender, mBornAgain,mMStatus,mFilledSpirit;
    private Button submit;

    private DatabaseReference firstReference;
    private String id;
    private ProgressDialog mProgress;
    private int mYear, mMonth, mDay, mmYear, mmMonth, mmDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_first_timers);

        mName = findViewById(R.id.et_first_name);
        mDob = findViewById(R.id.et_first_dob);
        mAddress = findViewById(R.id.et_first_address);
        mEmail = findViewById(R.id.et_first_email);
        mOccupation = findViewById(R.id.et_first_occupation);
        mPhone = findViewById(R.id.et_first_phone);
        mSupervisor = findViewById(R.id.et_first_follower);
        mVisitDate = findViewById(R.id.et_first_dov);
        mGender = findViewById(R.id.sp_first_gender);
        mBornAgain = findViewById(R.id.sp_born_again);
        mMStatus = findViewById(R.id.sp_first_status);
        mFilledSpirit = findViewById(R.id.sp_filled_with_holyghost);
        submit = findViewById(R.id.first_submit);

        mProgress = new ProgressDialog(this);

        firstReference = FirebaseDatabase.getInstance().getReference().child("FirstTimers");

        mDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddFirstTimers.this,
                        AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                mDob.setText(dayOfMonth+ "-"+ (monthOfYear + 1));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        mVisitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mmYear = c.get(Calendar.YEAR);
                mmMonth = c.get(Calendar.MONTH);
                mmDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddFirstTimers.this,
                        AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                mVisitDate.setText(dayOfMonth+ "-"+ (monthOfYear + 1)+ "-"+ year);
                            }
                        }, mmYear, mmMonth, mmDay);
                datePickerDialog.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mName.getText().toString().trim();
                final String dob = mDob.getText().toString().trim();
                final String gender=mGender.getItemAtPosition(
                        mGender.getSelectedItemPosition()).toString().trim();
                final String address=mAddress.getText().toString().trim();
                final String email = mEmail.getText().toString().trim();
                final String phone =mPhone.getText().toString().trim();
                final String marital_status = mMStatus.getItemAtPosition(
                        mMStatus.getSelectedItemPosition()).toString().trim();
                final String occupation = mOccupation.getText().toString().trim();
                final String supervisor = mSupervisor.getText().toString().trim();
                final String bornAgain = mBornAgain.getItemAtPosition(mBornAgain
                        .getSelectedItemPosition()).toString().trim();
                final String filled = mFilledSpirit.getItemAtPosition(mFilledSpirit
                        .getSelectedItemPosition()).toString().trim();
                final String dateOfVisit = mVisitDate.getText().toString().trim();



                if (TextUtils.isEmpty(name)){
                    mName.setError("Name is empty");
                } else if (TextUtils.isEmpty(address)){
                    mAddress.setError("address is empty");
                } else if (TextUtils.isEmpty(phone)){
                    mPhone.setError("Phone is empty");
                }else if (TextUtils.isEmpty(supervisor)){
                    mSupervisor.setError("Supervisor is Empty");
                }else if (marital_status.equalsIgnoreCase("Marital Status")){
                    mSupervisor.setError("invald marital status");
                } else{
                    mProgress.setMessage("Adding first Timer...");
                    mProgress.show();

                    id = firstReference.push().getKey();

                    FirstTimersModel model = new FirstTimersModel(name, dob,gender, address,
                            email, phone, occupation, marital_status,bornAgain,
                            filled,supervisor,dateOfVisit);
                    firstReference.child(dateOfVisit).child(id).setValue(model, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError!=null){
                                MDToast.makeText(AddFirstTimers.this,"data could not be saved",
                                        MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

                            } else {
                                MDToast.makeText(AddFirstTimers.this,"First timer added",
                                        MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();

                                mProgress.dismiss();
                                Intent first_timers = new Intent(AddFirstTimers.this, FirstTimers.class);
                                startActivity(first_timers);
                                finish();
                            }
                        }
                    });

                }
            }
        });

    }
}
