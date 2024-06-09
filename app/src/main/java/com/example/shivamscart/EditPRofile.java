package com.example.shivamscart;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.shivamscart.Models.HomeSliderModel;
import com.example.shivamscart.Models.Users;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;


public class EditPRofile extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;
    Uri uri;
    Uri uri1;
    Uri uri2;
    Uri uri3;
    Uri uri4;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(EditPRofile.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Uploading....");
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                binding.NAme.setText(users.getName());
                binding.Email.setText(users.getEmail());
                binding.Phone.setText(users.getNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().child("Users").child(auth.getUid()).child("Address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String Address = snapshot.getValue().toString();
                    binding.Address.setText(Address);
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().child("Users").child(auth.getUid()).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.EnterEmail.setVisibility(View.GONE);

                    binding.cantemail.setVisibility(View.VISIBLE);
                } else {
                    binding.EnterEmail.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().child("Users").child(auth.getUid()).child("gender").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.RadioGroupup.setVisibility(View.GONE);
                } else {
                    binding.RadioGroupup.setVisibility(View.VISIBLE);
                    binding.gender.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.ChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bonceexit);
                binding.ChangeName.startAnimation(animation);
                binding.ChangeName.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.ChangeName.startAnimation(animation1);
                    }
                }, 0);
                binding.NAme.getText().toString();
                database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("name").setValue(binding.NAme.getText().toString());
                Toast.makeText(EditPRofile.this, "Name Updated Successfully", Toast.LENGTH_SHORT).show();

            }
        });
        binding.EnterEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bonceexit);
                binding.EnterEmail.startAnimation(animation);
                binding.EnterEmail.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.EnterEmail.startAnimation(animation1);
                    }
                }, 0);
                database.getReference().child("Users").child(auth.getUid()).child("email").setValue(binding.Email.getText().toString());
                Toast.makeText(EditPRofile.this, "Email Updates Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        binding.gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bonceexit);
                binding.gender.startAnimation(animation);
                binding.gender.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.gender.startAnimation(animation1);
                    }
                }, 0);
                RadioGroup rdbgender = findViewById(R.id.RadioGroupup);
                RadioButton rdbselectedgen = findViewById(rdbgender.getCheckedRadioButtonId());
                String gender = rdbselectedgen.getText().toString();
                database.getReference().child("Users").child(auth.getUid()).child("gender").setValue(gender);
                Toast.makeText(EditPRofile.this, "Gender Updated Successfuly", Toast.LENGTH_SHORT).show();
            }
        });
        binding.ChangAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bonceexit);
                String Address = binding.Address.getText().toString();
                binding.ChangAddress.startAnimation(animation);
                binding.ChangAddress.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.ChangAddress.startAnimation(animation1);
                    }
                }, 0);
                if (Address.isEmpty()) {
                    Toast.makeText(EditPRofile.this, "Please fill first ", Toast.LENGTH_SHORT).show();
                } else {
                    database.getReference().child("Users").child(auth.getUid()).child("Address").setValue(binding.Address.getText().toString());
                    Toast.makeText(EditPRofile.this, "Address Updated", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
        binding.Address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.ChangAddress.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.serch_bar_bg));
                binding.ChangAddress.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.ChangAddress.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.dicount_bg));
                binding.ChangAddress.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                binding.ChangAddress.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.addSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bonceexit);
                binding.addSlider.startAnimation(animation);
                binding.addSlider.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.addSlider.startAnimation(animation1);
                    }
                }, 0);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 123);
            }
        });
        binding.addSlider1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bonceexit);
                binding.addSlider1.startAnimation(animation);
                binding.addSlider1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.addSlider1.startAnimation(animation1);
                    }
                }, 0);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 124);
            }
        });
        binding.addSlider2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bonceexit);
                binding.addSlider2.startAnimation(animation);
                binding.addSlider2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.addSlider2.startAnimation(animation1);
                    }
                }, 0);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 125);
            }
        });
        binding.addSlider3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bonceexit);
                binding.addSlider3.startAnimation(animation);
                binding.addSlider3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.addSlider3.startAnimation(animation1);
                    }
                }, 0);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 126);
            }
        });
        binding.addSlider4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bonceexit);
                binding.addSlider4.startAnimation(animation);
                binding.addSlider4.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.addSlider4.startAnimation(animation1);
                    }
                }, 0);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 127);
            }
        });
        binding.UploadSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(EditPRofile.this, R.anim.bonceexit);
                binding.UploadSlider.startAnimation(animation);
                binding.UploadSlider.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.UploadSlider.startAnimation(animation1);
                    }
                }, 0);
                Toast.makeText(EditPRofile.this, "Uploading Image", Toast.LENGTH_SHORT).show();
                HomeSliderModel model = new HomeSliderModel();
                final StorageReference reference = storage.getReference().child("Upcoming Products")
                        .child(FirebaseAuth.getInstance().getUid()).child("Poter").child(new Date().getTime() + "");
                final StorageReference reference1 = storage.getReference().child("Upcoming Products")
                        .child(FirebaseAuth.getInstance().getUid()).child("image1").child(new Date().getTime() + "");
                final StorageReference reference2 = storage.getReference().child("Upcoming Products")
                        .child(FirebaseAuth.getInstance().getUid()).child("image2").child(new Date().getTime() + "");
                final StorageReference reference3 = storage.getReference().child("Upcoming Products")
                        .child(FirebaseAuth.getInstance().getUid()).child("image3").child(new Date().getTime() + "");
                final StorageReference reference4 = storage.getReference().child("Upcoming Products")
                        .child(FirebaseAuth.getInstance().getUid()).child("image4").child(new Date().getTime() + "");
                reference1.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                           model.setImage1(uri.toString());
                            }
                        });
                    }
                });reference2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                           model.setImage2(uri.toString());
                            }
                        });
                    }
                });reference3.putFile(uri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                           model.setImage3(uri.toString());
                            }
                        });
                    }
                });reference4.putFile(uri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                           model.setImage4(uri.toString());
                            }
                        });
                    }
                });
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                model.setSliderPoster(uri.toString());
                                database.getReference().child("Upcoming Product").push().setValue(model);
                                Toast.makeText(EditPRofile.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });
            }
        });

        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("Slider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.SliderLayout.setVisibility(View.VISIBLE);
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                uri = data.getData();
                binding.SliderImage.setImageURI(uri);
                binding.SliderImage.setVisibility(View.VISIBLE);
                binding.UploadSlider.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.dicount_bg));
                binding.UploadSlider.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                binding.UploadSlider.setEnabled(true);
            }
        }
        if (requestCode == 124 && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                uri1 = data.getData();
                binding.SliderImage1.setImageURI(uri1);
                binding.SliderImage1.setVisibility(View.VISIBLE);
            }
        }
        if (requestCode == 125 && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                uri2 = data.getData();
                binding.SliderImage2.setImageURI(uri2);
                binding.SliderImage2.setVisibility(View.VISIBLE);
            }
        }
        if (requestCode == 126 && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                uri3 = data.getData();
                binding.SliderImage3.setImageURI(uri3);
                binding.SliderImage3.setVisibility(View.VISIBLE);
            }
        }
        if (requestCode == 127 && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                uri4 = data.getData();
                binding.SliderImage4.setImageURI(uri4);
                binding.SliderImage4.setVisibility(View.VISIBLE);

            }
        }
    }
}