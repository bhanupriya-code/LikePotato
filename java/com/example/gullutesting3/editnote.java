package com.example.gullutesting3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gullutesting3.fragments.NoteFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class editnote extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;

    Intent data;
    EditText medittitleofnote,meditcontentofnote;
    FloatingActionButton msaveeditnote, mgobacktoallnotes;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);

        medittitleofnote=findViewById(R.id.edittitleofnote);
        meditcontentofnote=findViewById(R.id.editcontentofnote);
        msaveeditnote=findViewById(R.id.saveeditnote);

        mgobacktoallnotes = findViewById(R.id.editgobacktoallnote);
        data=getIntent();

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar=findViewById(R.id.toolbarofeditnote);
        setSupportActionBar(toolbar);

        mgobacktoallnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(editnote.this, notesactivity.class));
            }
        });

        msaveeditnote.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(),"savebuton click",Toast.LENGTH_SHORT).show();

            String newtitle=medittitleofnote.getText().toString();
            String newcontent=meditcontentofnote.getText().toString();

            if(newtitle.isEmpty()||newcontent.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Something is empty",Toast.LENGTH_SHORT).show();
            }
            else
            {
                DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(data.getStringExtra("noteId"));
                Map<String,Object> note=new HashMap<>();
                note.put("title",newtitle);
                note.put("content",newcontent);
                documentReference.set(note).addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(),"Note is updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(editnote.this, notesactivity.class));
                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Failed To update",Toast.LENGTH_SHORT).show());
            }

        });

        String notetitle=data.getStringExtra("title");
        String notecontent=data.getStringExtra("content");
        meditcontentofnote.setText(notecontent);
        medittitleofnote.setText(notetitle);
    }
    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            backToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}