package com.example.shivamscart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shivamscart.Adapters.AboutAppAdapter;
import com.example.shivamscart.Models.AboutAppModel;
import com.example.shivamscart.databinding.ActivityAboutAppBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;

public class AboutApp extends AppCompatActivity {
    ActivityAboutAppBinding binding;
    Uri uri;
    FirebaseDatabase database;
    FirebaseStorage storage;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        ArrayList<AboutAppModel>list=new ArrayList<>();
        AboutAppAdapter adapter=new AboutAppAdapter(list,this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.AboutRecycle.setAdapter(adapter);
        binding.AboutRecycle.setLayoutManager(linearLayoutManager);


        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("Slider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.AddDescriptionAboutApp.setVisibility(View.VISIBLE);
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.AddDescriptionAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.AboutLAyout.setVisibility(View.VISIBLE);
            }
        });

        binding.UploadDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Description = binding.ETProductDetails.getText().toString();
                if (Description.isEmpty()) {
                    Toast.makeText(AboutApp.this, "Fill Description", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AboutApp.this, "Uploading", Toast.LENGTH_SHORT).show();
                    final StorageReference reference = storage.getReference()
                            .child("AboutApp")
                            .child(FirebaseAuth.getInstance().getUid())
                            .child(new Date().getTime() + "");
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    AboutAppModel aboutAppModel=new AboutAppModel();
                                    aboutAppModel.setAboutDiscription(binding.ETProductDetails.getText().toString());
                                    aboutAppModel.setAboutImage(uri.toString());
                                    database.getReference().child("About App").push().setValue(aboutAppModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(AboutApp.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                            binding.AboutLAyout.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });

        database.getReference().child("About App").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    AboutAppModel model=dataSnapshot.getValue(AboutAppModel.class);
                    list.add(model);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.AddAboutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 12);
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            uri = data.getData();
            binding.AbouAppImageView.setVisibility(View.VISIBLE);
            binding.AbouAppImageView.setImageURI(uri);
            binding.UploadDescription.setEnabled(true);
            binding.UploadDescription.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.dicount_bg));
            binding.UploadDescription.setTextColor(getBaseContext().getResources().getColor(R.color.white));
        }
    }
}