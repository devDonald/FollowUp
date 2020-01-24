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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNotices extends AppCompatActivity {

    private String title, details, startDate, end, noteDate;
    private EditText notices_title, notices_details;
    private Button submit_Notice;
    private KProgressHUD hud;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore noticesDB;
    private FirebaseFirestoreSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        noteDate= currentDate.format(calendar.getTime());

        notices_title = findViewById(R.id.add_notice_title);
        notices_details = findViewById(R.id.add_notice_details);
        submit_Notice =findViewById(R.id.add_notice_submit);

        hud = KProgressHUD.create(AddNotices.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait")
                .setDetailsLabel("Posting Notices")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(Color.BLACK)
                .setAutoDismiss(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        noticesDB = FirebaseFirestore.getInstance();
        settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        noticesDB.setFirestoreSettings(settings);


        submit_Notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = notices_title.getText().toString().trim();
                details = notices_details.getText().toString().trim();


                if (!title.isEmpty() && !details.isEmpty()){

                    hud.show();

                    Map<String, Object> data1 = new HashMap<>();
                    data1.put("title", title);
                    data1.put("details", details);
                    data1.put("noticeDate", noteDate);
                    data1.put("time", FieldValue.serverTimestamp());

                    noticesDB.collection("Notices")
                            .add(data1)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    hud.dismiss();

                                    MDToast.makeText(getApplicationContext(), "Notice Successfully Added!",MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                                    Intent toAdmin = new Intent(AddNotices.this, MainActivity.class);
                                    toAdmin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    toAdmin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(toAdmin);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    hud.dismiss();
                                    MDToast.makeText(getApplicationContext(), "Notice Failed to Add!",MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();

                                }
                            });

                }
            }
        });


    }
}
