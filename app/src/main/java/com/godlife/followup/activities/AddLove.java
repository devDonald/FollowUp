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

public class AddLove extends AppCompatActivity {

    private String title, content, author,uid, loveTime, loveDate;
    private EditText love_title, love_content, love_written_by;
    private Button submit_love;
    private KProgressHUD hud;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore loveDB;
    private FirebaseFirestoreSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_love);



        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        loveDate= currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        loveTime= currentTime.format(calendar.getTime());


        love_title = findViewById(R.id.add_love_title);
        love_content = findViewById(R.id.add_love_body);
        love_written_by = findViewById(R.id.add_love_author);
        submit_love=findViewById(R.id.add_love_submit);

        hud = KProgressHUD.create(AddLove.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait")
                .setDetailsLabel("Posting Love Note")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(Color.BLACK)
                .setAutoDismiss(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        loveDB = FirebaseFirestore.getInstance();
        settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        loveDB.setFirestoreSettings(settings);

        submit_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = love_title.getText().toString().trim();
                content= love_content.getText().toString().trim();
                author = love_written_by.getText().toString().trim();


                if (!title.isEmpty() && !content.isEmpty() && !author.isEmpty()){

                    hud.show();


                    //Map<String, Object> love = new LoveModel(title,content,author,loveDate,loveTime).toMap();


                    Map<String, Object> data1 = new HashMap<>();
                    data1.put("title", title);
                    data1.put("content", content);
                    data1.put("author", author);
                    data1.put("loveDate", loveDate);
                    data1.put("loveTime", loveTime);
                    data1.put("time", FieldValue.serverTimestamp());

                    loveDB.collection("Love")
                            .add(data1)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    hud.dismiss();

                                    MDToast.makeText(getApplicationContext(), "Love Note added!",MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                                    Intent toAdmin = new Intent(AddLove.this, MainActivity.class);
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
                                    MDToast.makeText(getApplicationContext(), "Love Note added!",MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();

                                }
                            });

                }
            }
        });
    }


}
