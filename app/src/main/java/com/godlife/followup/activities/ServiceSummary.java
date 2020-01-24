package com.godlife.followup.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.godlife.followup.R;
import com.godlife.followup.models.LyricsModel;
import com.godlife.followup.models.ServiceModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServiceSummary extends AppCompatActivity {

    private String service_tag, service_minister, service_nugget, serviceTime, serviceDate, timestamp;
    private EditText et_tag, et_minister, et_nugget;
    private Button submit_summary;
    private KProgressHUD hud;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore serviceDB;
    private FirebaseFirestoreSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_summary);


        et_tag = findViewById(R.id.add_service_tag);
        et_nugget = findViewById(R.id.service_action_point);
        et_minister = findViewById(R.id.service_minister);
        submit_summary =findViewById(R.id.submit_sevice_update);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        serviceDate= currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        serviceTime= currentTime.format(calendar.getTime());

        SimpleDateFormat getdate = new SimpleDateFormat("ddMMyyyyhhmmssSSS");
        timestamp = getdate.format(new Date());



                hud = KProgressHUD.create(ServiceSummary.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait")
                .setDetailsLabel("Posting Update")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(Color.BLACK)
                .setAutoDismiss(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        serviceDB = FirebaseFirestore.getInstance();
        settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        serviceDB.setFirestoreSettings(settings);


        submit_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service_tag = et_tag.getText().toString().trim();
                service_minister= et_minister.getText().toString().trim();
                service_nugget = et_nugget.getText().toString().trim();

                try {

                    if (!service_tag.isEmpty() && !service_minister.isEmpty()){
                        hud.show();

                        Map<String, Object> data1 = new HashMap<>();
                        data1.put("service_date", serviceDate);
                        data1.put("service_time", serviceTime);
                        data1.put("service_nugget", service_nugget);
                        data1.put("service_minister", service_minister);
                        data1.put("service_tag", service_tag);
                        data1.put("time", FieldValue.serverTimestamp());


                        serviceDB.collection("Updates")
                                .add(data1)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        hud.dismiss();

                                        MDToast.makeText(ServiceSummary.this,"Posted",MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
                                        et_nugget.setText(" ");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        hud.dismiss();
                                        MDToast.makeText(getApplicationContext(), "Failed!",MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();

                                    }
                                });

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



    }
}
