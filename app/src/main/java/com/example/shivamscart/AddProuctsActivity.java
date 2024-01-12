package com.example.shivamscart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.shivamscart.Models.MobileModel;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityAddProductsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class AddProuctsActivity extends AppCompatActivity {
    ActivityAddProductsBinding binding;
    private BroadcastReceiver broadcastReceiver;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Dialog dialog;
    Uri uri;
    String[] items={"Mobiles"};
    String[] company={"Oppo","Vivo","Apple","Samsung","Mi","Realme","OnePlus","iQOO","Techno","Infinix","Google"};
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_bar);
        dialog.setCanceledOnTouchOutside(false);

        final Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        final  Animation animation1=AnimationUtils.loadAnimation(this,R.anim.bonceexit);
        adapter = new ArrayAdapter<String>(this, R.layout.product_type, items);
        binding.EtProductType.setAdapter(adapter);

        binding.EtProductType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddProuctsActivity.this,item+" Selected", Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new ArrayAdapter<String>(this, R.layout.product_type, company);
        binding.EtProductCompany.setAdapter(adapter);
        binding.EtProductCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddProuctsActivity.this,item+ " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        binding.EtActualPRice.addTextChangedListener(textWatcher);
        binding.EtSellingPrice.addTextChangedListener(textWatcher);

        binding.AddMobileImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 123);
                binding.AddMobileImages.startAnimation(animation);
                binding.AddMobileImages.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.AddMobileImages.startAnimation(animation1);
                    }
                },0);
            }
        });
        //Uploading all details
        binding.UploadToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.UploadToDatabase.startAnimation(animation);
                binding.UploadToDatabase.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.UploadToDatabase.startAnimation(animation1);
                    }
                },0);
                if (binding.EtProductType.getText().toString().isEmpty()&& binding.EtProductCompany.getText().toString().isEmpty()
                        && binding.ETProductDetails.getText().toString().isEmpty()&& binding.etProductRam.getText().toString().isEmpty()
                        && binding.EtActualPRice.getText().toString().isEmpty()&&binding.EtSellingPrice.getText().toString().isEmpty()
                &&binding.mbileColor.getText().toString().isEmpty()&&binding.EtProductCompany.getText().toString().isEmpty()&&
                        binding.mobileStorage.getText().toString().isEmpty())
                {
                    Toast.makeText(AddProuctsActivity.this, "fill all the above details", Toast.LENGTH_SHORT).show();
                }
                if (binding.MobilePhoto.equals(null)){
                    Toast.makeText(AddProuctsActivity.this, "select Image", Toast.LENGTH_SHORT).show();
                }
                else {
                    String productType=binding.EtProductType.getText().toString();
                    String mobiles="Mobiles";
                    if (productType.equals(mobiles)) {
                        dialog.show();
                        final StorageReference reference = storage.getReference()
                                .child("Mobiles")
                                .child(FirebaseAuth.getInstance().getUid())
                                .child(new Date().getTime() + "");
                        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        MobileModel mobileModel = new MobileModel();
                                        mobileModel.setMobileDescription(binding.ETProductDetails.getText().toString());
                                        mobileModel.setMobileRam(binding.etProductRam.getText().toString());
                                        mobileModel.setMobilePrice(binding.EtSellingPrice.getText().toString());
                                        mobileModel.setMobileActualPrice(binding.EtActualPRice.getText().toString());
                                        mobileModel.setMobileImage(uri.toString());
                                        mobileModel.setProductAddedAt(new Date().getTime());
                                        mobileModel.setMobileDicount(binding.EtDicount.getText().toString());
                                        mobileModel.setMobileColor(binding.mbileColor.getText().toString());
                                        mobileModel.setMobileStorage(binding.mobileStorage.getText().toString());
                                        mobileModel.setMobileCompany(binding.EtProductCompany.getText().toString());
                                        mobileModel.setAddedBy(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        database.getReference().child("Products").child("Mobiles")
                                                .push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        dialog.dismiss();
                                                        Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                    }
                                });
                            }
                        });
//                        String productCompany=binding.EtProductCompany.getText().toString();
//                        String oppo="Oppo";
//                        String vivo="Vivo";
//                        String samsung="Samsung";
//                        String apple="Apple";
//                        String realme="Realme";
//                        String mi="Mi";
//                        String OnePlus="OnePlus";
//                        String iQ="iQOO";
//                        String Techno="TEchno";
//                        String infinix="Infinix";
//                        String Google="Google";
//                        if (productCompany.equals(oppo)) {
//                            final StorageReference reference1 = storage.getReference()
//                                    .child("Mobiles")
//                                    .child(FirebaseAuth.getInstance().getUid())
//                                    .child(new Date().getTime() + "");
//                            reference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            MobileModel mobileModel = new MobileModel();
//                                            mobileModel.setMobileDescription(binding.ETProductDetails.getText().toString());
//                                            mobileModel.setMobileRam(binding.etProductRam.getText().toString());
//                                            mobileModel.setMobilePrice(binding.EtSellingPrice.getText().toString());
//                                            mobileModel.setMobileActualPrice(binding.EtActualPRice.getText().toString());
//                                            mobileModel.setMobileImage(uri.toString());
//                                            mobileModel.setProductAddedAt(new Date().getTime());
//                                            mobileModel.setMobileDicount(binding.EtDicount.getText().toString());
//                                            mobileModel.setMobileColor(binding.mbileColor.getText().toString());
//                                            mobileModel.setMobileStorage(binding.mobileStorage.getText().toString());
//                                            mobileModel.setMobileCompany(binding.EtProductCompany.getText().toString());
//                                            database.getReference().child("Mobiles Company")
//                                                    .child("Oppo")
//                                                    .push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    });
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                        else if (productCompany.equals(vivo)) {
//                            final StorageReference reference1 = storage.getReference()
//                                    .child("Mobiles")
//                                    .child(FirebaseAuth.getInstance().getUid())
//                                    .child(new Date().getTime() + "");
//                            reference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            MobileModel mobileModel = new MobileModel();
//                                            mobileModel.setMobileDescription(binding.ETProductDetails.getText().toString());
//                                            mobileModel.setMobileRam(binding.etProductRam.getText().toString());
//                                            mobileModel.setMobilePrice(binding.EtSellingPrice.getText().toString());
//                                            mobileModel.setMobileActualPrice(binding.EtActualPRice.getText().toString());
//                                            mobileModel.setMobileImage(uri.toString());
//                                            mobileModel.setProductAddedAt(new Date().getTime());
//                                            mobileModel.setMobileDicount(binding.EtDicount.getText().toString());
//                                            mobileModel.setMobileColor(binding.mbileColor.getText().toString());
//                                            mobileModel.setMobileStorage(binding.mobileStorage.getText().toString());
//                                            mobileModel.setMobileCompany(binding.EtProductCompany.getText().toString());
//                                            database.getReference().child("Mobiles Company")
//                                                    .child("Vivo")
//                                                    .push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    });
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                        else if (productCompany.equals(samsung)) {
//                            final StorageReference reference1 = storage.getReference()
//                                    .child("Mobiles")
//                                    .child(FirebaseAuth.getInstance().getUid())
//                                    .child(new Date().getTime() + "");
//                            reference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            MobileModel mobileModel = new MobileModel();
//                                            mobileModel.setMobileDescription(binding.ETProductDetails.getText().toString());
//                                            mobileModel.setMobileRam(binding.etProductRam.getText().toString());
//                                            mobileModel.setMobilePrice(binding.EtSellingPrice.getText().toString());
//                                            mobileModel.setMobileActualPrice(binding.EtActualPRice.getText().toString());
//                                            mobileModel.setMobileImage(uri.toString());
//                                            mobileModel.setProductAddedAt(new Date().getTime());
//                                            mobileModel.setMobileDicount(binding.EtDicount.getText().toString());
//                                            mobileModel.setMobileColor(binding.mbileColor.getText().toString());
//                                            mobileModel.setMobileStorage(binding.mobileStorage.getText().toString());
//                                            mobileModel.setMobileCompany(binding.EtProductCompany.getText().toString());
//                                            database.getReference().child("Mobiles Company")
//                                                    .child("Samsung")
//                                                    .push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    });
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                        else if (productCompany.equals(apple)) {
//                            final StorageReference reference1 = storage.getReference()
//                                    .child("Mobiles")
//                                    .child(FirebaseAuth.getInstance().getUid())
//                                    .child(new Date().getTime() + "");
//                            reference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            MobileModel mobileModel = new MobileModel();
//                                            mobileModel.setMobileDescription(binding.ETProductDetails.getText().toString());
//                                            mobileModel.setMobileRam(binding.etProductRam.getText().toString());
//                                            mobileModel.setMobilePrice(binding.EtSellingPrice.getText().toString());
//                                            mobileModel.setMobileActualPrice(binding.EtActualPRice.getText().toString());
//                                            mobileModel.setMobileImage(uri.toString());
//                                            mobileModel.setProductAddedAt(new Date().getTime());
//                                            mobileModel.setMobileDicount(binding.EtDicount.getText().toString());
//                                            mobileModel.setMobileColor(binding.mbileColor.getText().toString());
//                                            mobileModel.setMobileStorage(binding.mobileStorage.getText().toString());
//                                            mobileModel.setMobileCompany(binding.EtProductCompany.getText().toString());
//                                            database.getReference().child("Mobiles Company")
//                                                    .child("Apple")
//                                                    .push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    });
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                        else if (productCompany.equals(realme)) {
//                            final StorageReference reference1 = storage.getReference()
//                                    .child("Mobiles")
//                                    .child(FirebaseAuth.getInstance().getUid())
//                                    .child(new Date().getTime() + "");
//                            reference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            MobileModel mobileModel = new MobileModel();
//                                            mobileModel.setMobileDescription(binding.ETProductDetails.getText().toString());
//                                            mobileModel.setMobileRam(binding.etProductRam.getText().toString());
//                                            mobileModel.setMobilePrice(binding.EtSellingPrice.getText().toString());
//                                            mobileModel.setMobileActualPrice(binding.EtActualPRice.getText().toString());
//                                            mobileModel.setMobileImage(uri.toString());
//                                            mobileModel.setProductAddedAt(new Date().getTime());
//                                            mobileModel.setMobileDicount(binding.EtDicount.getText().toString());
//                                            mobileModel.setMobileColor(binding.mbileColor.getText().toString());
//                                            mobileModel.setMobileStorage(binding.mobileStorage.getText().toString());
//                                            mobileModel.setMobileCompany(binding.EtProductCompany.getText().toString());
//                                            database.getReference().child("Mobiles Company")
//                                                    .child("Realme")
//                                                    .push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    });
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                        else if (productCompany.equals(mi)) {
//                            final StorageReference reference1 = storage.getReference()
//                                    .child("Mobiles")
//                                    .child(FirebaseAuth.getInstance().getUid())
//                                    .child(new Date().getTime() + "");
//                            reference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            MobileModel mobileModel = new MobileModel();
//                                            mobileModel.setMobileDescription(binding.ETProductDetails.getText().toString());
//                                            mobileModel.setMobileRam(binding.etProductRam.getText().toString());
//                                            mobileModel.setMobilePrice(binding.EtSellingPrice.getText().toString());
//                                            mobileModel.setMobileActualPrice(binding.EtActualPRice.getText().toString());
//                                            mobileModel.setMobileImage(uri.toString());
//                                            mobileModel.setProductAddedAt(new Date().getTime());
//                                            mobileModel.setMobileDicount(binding.EtDicount.getText().toString());
//                                            mobileModel.setMobileColor(binding.mbileColor.getText().toString());
//                                            mobileModel.setMobileStorage(binding.mobileStorage.getText().toString());
//                                            mobileModel.setMobileCompany(binding.EtProductCompany.getText().toString());
//                                            database.getReference().child("Mobiles Company")
//                                                    .child("Mi")
//                                                    .push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    });
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                        else if (productCompany.equals(OnePlus)) {
//                            final StorageReference reference1 = storage.getReference()
//                                    .child("Mobiles")
//                                    .child(FirebaseAuth.getInstance().getUid())
//                                    .child(new Date().getTime() + "");
//                            reference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            MobileModel mobileModel = new MobileModel();
//                                            mobileModel.setMobileDescription(binding.ETProductDetails.getText().toString());
//                                            mobileModel.setMobileRam(binding.etProductRam.getText().toString());
//                                            mobileModel.setMobilePrice(binding.EtSellingPrice.getText().toString());
//                                            mobileModel.setMobileActualPrice(binding.EtActualPRice.getText().toString());
//                                            mobileModel.setMobileImage(uri.toString());
//                                            mobileModel.setProductAddedAt(new Date().getTime());
//                                            mobileModel.setMobileDicount(binding.EtDicount.getText().toString());
//                                            mobileModel.setMobileColor(binding.mbileColor.getText().toString());
//                                            mobileModel.setMobileStorage(binding.mobileStorage.getText().toString());
//                                            mobileModel.setMobileCompany(binding.EtProductCompany.getText().toString());
//                                            database.getReference().child("Mobiles Company")
//                                                    .child("OnePlus")
//                                                    .push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    });
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                        else if (productCompany.equals(iQ)) {
//                            final StorageReference reference1 = storage.getReference()
//                                    .child("Mobiles")
//                                    .child(FirebaseAuth.getInstance().getUid())
//                                    .child(new Date().getTime() + "");
//                            reference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            MobileModel mobileModel = new MobileModel();
//                                            mobileModel.setMobileDescription(binding.ETProductDetails.getText().toString());
//                                            mobileModel.setMobileRam(binding.etProductRam.getText().toString());
//                                            mobileModel.setMobilePrice(binding.EtSellingPrice.getText().toString());
//                                            mobileModel.setMobileActualPrice(binding.EtActualPRice.getText().toString());
//                                            mobileModel.setMobileImage(uri.toString());
//                                            mobileModel.setProductAddedAt(new Date().getTime());
//                                            mobileModel.setMobileDicount(binding.EtDicount.getText().toString());
//                                            mobileModel.setMobileColor(binding.mbileColor.getText().toString());
//                                            mobileModel.setMobileStorage(binding.mobileStorage.getText().toString());
//                                            mobileModel.setMobileCompany(binding.EtProductCompany.getText().toString());
//                                            database.getReference().child("Mobiles Company")
//                                                    .child("iQOO")
//                                                    .push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    });
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                        else if (productCompany.equals(Techno)) {
//                            final StorageReference reference1 = storage.getReference()
//                                    .child("Mobiles")
//                                    .child(FirebaseAuth.getInstance().getUid())
//                                    .child(new Date().getTime() + "");
//                            reference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            MobileModel mobileModel = new MobileModel();
//                                            mobileModel.setMobileDescription(binding.ETProductDetails.getText().toString());
//                                            mobileModel.setMobileRam(binding.etProductRam.getText().toString());
//                                            mobileModel.setMobilePrice(binding.EtSellingPrice.getText().toString());
//                                            mobileModel.setMobileActualPrice(binding.EtActualPRice.getText().toString());
//                                            mobileModel.setMobileImage(uri.toString());
//                                            mobileModel.setProductAddedAt(new Date().getTime());
//                                            mobileModel.setMobileDicount(binding.EtDicount.getText().toString());
//                                            mobileModel.setMobileColor(binding.mbileColor.getText().toString());
//                                            mobileModel.setMobileStorage(binding.mobileStorage.getText().toString());
//                                            mobileModel.setMobileCompany(binding.EtProductCompany.getText().toString());
//                                            database.getReference().child("Mobiles Company")
//                                                    .child("Techno")
//                                                    .push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    });
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                        else if (productCompany.equals(infinix)) {
//                            final StorageReference reference1 = storage.getReference()
//                                    .child("Mobiles")
//                                    .child(FirebaseAuth.getInstance().getUid())
//                                    .child(new Date().getTime() + "");
//                            reference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            MobileModel mobileModel = new MobileModel();
//                                            mobileModel.setMobileDescription(binding.ETProductDetails.getText().toString());
//                                            mobileModel.setMobileRam(binding.etProductRam.getText().toString());
//                                            mobileModel.setMobilePrice(binding.EtSellingPrice.getText().toString());
//                                            mobileModel.setMobileActualPrice(binding.EtActualPRice.getText().toString());
//                                            mobileModel.setMobileImage(uri.toString());
//                                            mobileModel.setProductAddedAt(new Date().getTime());
//                                            mobileModel.setMobileDicount(binding.EtDicount.getText().toString());
//                                            mobileModel.setMobileColor(binding.mbileColor.getText().toString());
//                                            mobileModel.setMobileStorage(binding.mobileStorage.getText().toString());
//                                            mobileModel.setMobileCompany(binding.EtProductCompany.getText().toString());
//                                            database.getReference().child("Mobiles Company")
//                                                    .child("Infinix")
//                                                    .push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    });
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                        else if (productCompany.equals(Google)) {
//                            final StorageReference reference1 = storage.getReference()
//                                    .child("Mobiles")
//                                    .child(FirebaseAuth.getInstance().getUid())
//                                    .child(new Date().getTime() + "");
//                            reference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            MobileModel mobileModel = new MobileModel();
//                                            mobileModel.setMobileDescription(binding.ETProductDetails.getText().toString());
//                                            mobileModel.setMobileRam(binding.etProductRam.getText().toString());
//                                            mobileModel.setMobilePrice(binding.EtSellingPrice.getText().toString());
//                                            mobileModel.setMobileActualPrice(binding.EtActualPRice.getText().toString());
//                                            mobileModel.setMobileImage(uri.toString());
//                                            mobileModel.setProductAddedAt(new Date().getTime());
//                                            mobileModel.setMobileDicount(binding.EtDicount.getText().toString());
//                                            mobileModel.setMobileColor(binding.mbileColor.getText().toString());
//                                            mobileModel.setMobileStorage(binding.mobileStorage.getText().toString());
//                                            mobileModel.setMobileCompany(binding.EtProductCompany.getText().toString());
//                                            database.getReference().child("Mobiles Company")
//                                                    .child("Google")
//                                                    .push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            dialog.dismiss();
//                                                            Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    });
//                                        }
//                                    });
//                                }
//                            });
//                        }
                    }
                    else {}
                }
            }
        });
    }

    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            calculateAndSetPercentage();

        }

        private void calculateAndSetPercentage() {
            String str1=binding.EtActualPRice.getText().toString();
            String str2=binding.EtSellingPrice.getText().toString();
            // Parse values as numbers
            if (!str1.isEmpty() && !str2.isEmpty()) {
                try {
                    double value1 = Double.parseDouble(str1);
                    double value2 = Double.parseDouble(str2);

                    double minus=(value1-value2);
                    // Calculate percentage
                    double percentage = (minus / value1) * 100;

                    // Set the calculated percentage in the third EditText
                    binding.EtDicount.setText(String.valueOf(percentage));
                } catch (NumberFormatException e) {
                    // Handle invalid input
                    binding.EtDicount.setText("");
                }
            } else {
                // If either field is empty, clear the result field
                binding.EtDicount.setText("");
            }

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null) {
            uri = data.getData();
            binding.MobilePhoto.setImageURI(uri);
            binding.MobilePhoto.setVisibility(View.VISIBLE);
        }
    }

}