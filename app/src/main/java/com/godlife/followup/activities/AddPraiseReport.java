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

public class AddPraiseReport extends AppCompatActivity {
    private String title, content, author,uid, praiseTime, praiseDate;
    private EditText praise_title, praise_content, praise_written_by;
    private Button submit_praise;
    private KProgressHUD hud;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore praiseDB;
    private FirebaseFirestoreSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_praise_report);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        praiseDate= currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        praiseTime= currentTime.format(calendar.getTime());


        praise_title = findViewById(R.id.add_praise_title);
        praise_content = findViewById(R.id.add_prasie_body);
        praise_written_by = findViewById(R.id.add_praise_author);
        submit_praise=findViewById(R.id.add_praise_submit);

        hud = KProgressHUD.create(AddPraiseReport.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait")
                .setDetailsLabel("Posting Praise Report")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(Color.BLACK)
                .setAutoDismiss(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        praiseDB = FirebaseFirestore.getInstance();
        settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        praiseDB.setFirestoreSettings(settings);

        submit_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = praise_title.getText().toString().trim();
                content= praise_content.getText().toString().trim();
                author = praise_written_by.getText().toString().trim();


                if (!title.isEmpty() && !content.isEmpty() && !author.isEmpty()){

                    hud.show();

                    Map<String, Object> data1 = new HashMap<>();
                    data1.put("title", title);
                    data1.put("content", content);
                    data1.put("author", author);
                    data1.put("praiseDate", praiseDate);
                    data1.put("praiseTime", praiseTime);
                    data1.put("time", FieldValue.serverTimestamp());

                    praiseDB.collection("Praise")
                            .add(data1)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    hud.dismiss();

                                    MDToast.makeText(getApplicationContext(), "Praise Report Successfully Added!",MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                                    Intent toAdmin = new Intent(AddPraiseReport.this, MainActivity.class);
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
                                    MDToast.makeText(getApplicationContext(), "Praise Report Failed to Add!",MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();

                                }
                            });

                }
            }
        });

    }
}
