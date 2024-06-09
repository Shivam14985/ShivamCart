package com.example.shivamscart;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityAddProductsBinding;
import com.google.android.gms.tasks.OnFailureListener;
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

public class AddProuctsActivity extends AppCompatActivity {
    ActivityAddProductsBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Dialog dialog;
    Uri uri;
    Uri uri5;
    Uri uri4;
    Uri uri3;
    Uri uri2;
    String[] items = {"Mobiles", "Fashion", "Electronics", "Home", "Appliances"};
    String[] company = {"Oppo", "Vivo", "Apple", "Samsung", "Mi", "Realme", "OnePlus", "iQOO", "Techno", "Infinix", "Google"};
    String[] FAshionProductcompany = {"Puma", "Adidas", "Nike", "Peter England", "Van Huesen", "Allen Solly", "Redtape", "H&m", "Zara", "Gucci", "Spykar"};
    String[] ProductFor = {"Girls", "Boys", "Men", "Women", "Unisex"};
    String[] WomenCategory = {"Saree", "Kurta", "Dress", "Skirt", "Shift Dress", "Crop Top", "Shirt Dress", "Salwar Kameez", "Lehenga", "Ghagra", "Frock"};
    String[] MenCategory = {"Shirt", "T-Shirt", "Polo Shirt", "Jeans", "Pants", "Trouser", "Cargo Pants", "Chinos", "Jackets", "Hooddie", "Sweater"};
    String[] UnisexCategory = {"Shirts", "T-Shirts", "Shoes", "Hooddie", "Caps"};
    String[] ElectronicProductType = {"Laptops", "Led", "Tablets", "Smartwatches", "Cameras", "Headphones", "Earbuds", "Keyboards", "Mouse", "Printers", "Projecters", "Speakers", "Harddisk", "Pendrive", "SD Card", "Smart Speakers", "Smart Bulb"};
    String[] LaptopsCompany = {"HP", "Dell", "Asus", "Apple", "Infinix", "Realme", "Acer", "Lenovo"};
    String[] LedCompany = {"Realme", "Mi", "LG", "Samsung", "OnePlus"};
    String[] TAblets = {"Realme", "Apple", "Xiomi", "Oppo", "Motorola", "Samsung", "Apple iPad"};
    String[] SmartWatch = {"Dizo", "Realme", "Apple", "Fireboult", "OnePlus", "Samsung", "Boat"};
    String[] Cameras = {"Sony", "Nikon", "Cannon", "Panasonic"};
    String[] HeadPhones = {"Boat", "JBL", "Apple", "Realme", "OnePlus", "Samsung", "Mi"};
    String[] ComputerAccesories = {"Hp", "Dell", "Apple", "Asus", "Intel", "Zebronics", "Acer"};
    String[] PRojecter = {"Hp", "Canon", "Epson", "BenQ", "LG", "Zebronics", "Portonics"};
    String[] Spekers = {"JBL", "Zebronics", "Panasonic", "Intex", "Boat", "Sony", "Samsung"};
    String[] Storage = {"Apple", "Realme", "Mi", "Syska", "SandDisk", "Hp", "Philips"};
    String[] SmartHome = {"Mi", "Realme", "Mi", "Syska", "Halonix", "Wipro"};
    String[] HomeApllianceType = {"Cooker"};
    String[] HomeApllianceCompany = {"Hawkins"};
    String[] AppliaceCategory = {"Washing Machine", "Refrigrator", "Air Conditioners", "Juicer", "Electric Kettles", "Microwaves", "Fans", "Coolers"};
    String[] AppliaceCompany = {"Haier", "Samsung", "Whirpool", "LG", "Realme", "Godrej", "Voltas", "Panasonic", "Bajaj", "Pigeon", "V-Guard", "Crompton", "Philips", "Kent", "Livguard", "Livpure"};
    ArrayAdapter<String> adapter;
    private int uploadCount = 0;
    private BroadcastReceiver broadcastReceiver;
    private TextWatcher textWatcher = new TextWatcher() {
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
            String str1 = binding.ETActualPrice.getText().toString();
            String str2 = binding.EtSellingPrice.getText().toString();
            // Parse values as numbers
            if (!str1.isEmpty() && !str2.isEmpty()) {
                try {
                    double value1 = Double.parseDouble(str1);
                    double value2 = Double.parseDouble(str2);

                    double minus = (value1 - value2);
                    // Calculate percentage
                    double percentage = (minus / value1) * 100;

                    // Set the calculated percentage in the third EditText
                    binding.EtDiscount.setText(String.valueOf(percentage));
                } catch (NumberFormatException e) {
                    // Handle invalid input
                    binding.EtDiscount.setText("");
                }
            } else {
                // If either field is empty, clear the result field
                binding.EtDiscount.setText("");
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_bar);
        dialog.setCanceledOnTouchOutside(false);

        adapter = new ArrayAdapter<String>(this, R.layout.product_type, items);
        binding.EtProductType.setAdapter(adapter);
        binding.EtProductType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new ArrayAdapter<String>(this, R.layout.product_type, HomeApllianceType);
        binding.categoryHome.setAdapter(adapter);
        binding.categoryHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ArrayAdapter<String>(this, R.layout.product_type, HomeApllianceCompany);
        binding.companyHomeAppliance.setAdapter(adapter);
        binding.companyHomeAppliance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new ArrayAdapter<String>(this, R.layout.product_type, AppliaceCategory);
        binding.EtappliaceProductType.setAdapter(adapter);
        binding.EtappliaceProductType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new ArrayAdapter<String>(this, R.layout.product_type, AppliaceCompany);
        binding.EtappliaceProductCompany.setAdapter(adapter);
        binding.EtappliaceProductCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ArrayAdapter<String>(this, R.layout.product_type, company);
        binding.ETmobileCompany.setAdapter(adapter);
        binding.ETmobileCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ArrayAdapter<String>(this, R.layout.product_type, ElectronicProductType);
        binding.EtElectronicsCategory.setAdapter(adapter);
        binding.EtElectronicsCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ArrayAdapter<String>(this, R.layout.product_type, ProductFor);
        binding.ETPRoductFor.setAdapter(adapter);
        binding.ETPRoductFor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ArrayAdapter<String>(this, R.layout.product_type, FAshionProductcompany);
        binding.ETFashionProductCompany.setAdapter(adapter);
        binding.ETFashionProductCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

        binding.EtElectronicsCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String type = binding.EtElectronicsCategory.getText().toString();
                binding.EtElectronicsCompany.getText().clear();
                if (type.equals("Laptops")) {
                    binding.EtElectronicRam.setVisibility(View.VISIBLE);
                    binding.EtElctronicStorage.setVisibility(View.VISIBLE);
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, LaptopsCompany);
                    binding.EtElectronicsCompany.setAdapter(adapter);
                    binding.EtElectronicsCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (type.equals("Led")) {
                    binding.EtElectronicRam.setVisibility(View.GONE);
                    binding.EtElctronicStorage.setVisibility(View.GONE);
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, LedCompany);
                    binding.EtElectronicsCompany.setAdapter(adapter);
                    binding.EtElectronicsCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (type.equals("Tablets")) {
                    binding.EtElectronicRam.setVisibility(View.VISIBLE);
                    binding.EtElctronicStorage.setVisibility(View.VISIBLE);
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, TAblets);
                    binding.EtElectronicsCompany.setAdapter(adapter);
                    binding.EtElectronicsCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (type.equals("Smartwatches")) {
                    binding.EtElectronicRam.setVisibility(View.GONE);
                    binding.EtElctronicStorage.setVisibility(View.GONE);
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, SmartWatch);
                    binding.EtElectronicsCompany.setAdapter(adapter);
                    binding.EtElectronicsCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (type.equals("Cameras")) {
                    binding.EtElectronicRam.setVisibility(View.GONE);
                    binding.EtElctronicStorage.setVisibility(View.GONE);
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, Cameras);
                    binding.EtElectronicsCompany.setAdapter(adapter);
                    binding.EtElectronicsCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (type.equals("Headphones") || type.equals("Earbuds")) {
                    binding.EtElectronicRam.setVisibility(View.GONE);
                    binding.EtElctronicStorage.setVisibility(View.GONE);
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, HeadPhones);
                    binding.EtElectronicsCompany.setAdapter(adapter);
                    binding.EtElectronicsCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (type.equals("Keyboards") || type.equals("Mouse")) {
                    binding.EtElectronicRam.setVisibility(View.GONE);
                    binding.EtElctronicStorage.setVisibility(View.GONE);
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, ComputerAccesories);
                    binding.EtElectronicsCompany.setAdapter(adapter);
                    binding.EtElectronicsCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (type.equals("Printers") || type.equals("Projecters")) {
                    binding.EtElectronicRam.setVisibility(View.GONE);
                    binding.EtElctronicStorage.setVisibility(View.GONE);
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, PRojecter);
                    binding.EtElectronicsCompany.setAdapter(adapter);
                    binding.EtElectronicsCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (type.equals("HardDisk") || type.equals("Pendrive") || type.equals("SD Card")) {
                    binding.EtElectronicRam.setVisibility(View.GONE);
                    binding.EtElctronicStorage.setVisibility(View.VISIBLE);
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, Storage);
                    binding.EtElectronicsCompany.setAdapter(adapter);
                    binding.EtElectronicsCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (type.equals("Smart Speakers") || type.equals("Smart Bulb")) {
                    binding.EtElectronicRam.setVisibility(View.GONE);
                    binding.EtElctronicStorage.setVisibility(View.GONE);
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, SmartHome);
                    binding.EtElectronicsCompany.setAdapter(adapter);
                    binding.EtElectronicsCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (type.equals("Speakers")) {
                    binding.EtElectronicRam.setVisibility(View.GONE);
                    binding.EtElctronicStorage.setVisibility(View.GONE);
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, Spekers);
                    binding.EtElectronicsCompany.setAdapter(adapter);
                    binding.EtElectronicsCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.ETPRoductFor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.ETFashionCategory.getText().clear();
                binding.ETFashionProductCompany.getText().clear();
                String ProductFor = binding.ETPRoductFor.getText().toString();
                String Men = "Men";
                String Women = "Women";
                String Unisex = "Unisex";
                String Boys = "Boys";
                String Girls = "Girls";
                if (ProductFor.equals(Men)) {
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, MenCategory);
                    binding.ETFashionCategory.setAdapter(adapter);
                    binding.ETFashionCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (ProductFor.equals(Boys)) {
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, MenCategory);
                    binding.ETFashionCategory.setAdapter(adapter);
                    binding.ETFashionCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (ProductFor.equals(Women)) {
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, WomenCategory);
                    binding.ETFashionCategory.setAdapter(adapter);
                    binding.ETFashionCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (ProductFor.equals(Girls)) {
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, WomenCategory);
                    binding.ETFashionCategory.setAdapter(adapter);
                    binding.ETFashionCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (ProductFor.equals(Unisex)) {
                    adapter = new ArrayAdapter<String>(AddProuctsActivity.this, R.layout.product_type, UnisexCategory);
                    binding.ETFashionCategory.setAdapter(adapter);
                    binding.ETFashionCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Toast.makeText(AddProuctsActivity.this, item + " Selected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.ETActualPrice.addTextChangedListener(textWatcher);
        binding.EtSellingPrice.addTextChangedListener(textWatcher);

        binding.AddMobileImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 123);

                final Animation animation = AnimationUtils.loadAnimation(AddProuctsActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(AddProuctsActivity.this, R.anim.bonceexit);
                binding.AddMobileImages.startAnimation(animation);
                binding.AddMobileImages.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.AddMobileImages.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.AddMobileImages2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 124);

                final Animation animation = AnimationUtils.loadAnimation(AddProuctsActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(AddProuctsActivity.this, R.anim.bonceexit);
                binding.AddMobileImages2.startAnimation(animation);
                binding.AddMobileImages2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.AddMobileImages2.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.AddMobileImages3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 125);

                final Animation animation = AnimationUtils.loadAnimation(AddProuctsActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(AddProuctsActivity.this, R.anim.bonceexit);
                binding.AddMobileImages3.startAnimation(animation);
                binding.AddMobileImages3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.AddMobileImages3.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.AddMobileImages4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 126);

                final Animation animation = AnimationUtils.loadAnimation(AddProuctsActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(AddProuctsActivity.this, R.anim.bonceexit);
                binding.AddMobileImages4.startAnimation(animation);
                binding.AddMobileImages4.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.AddMobileImages4.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.AddMobileImages5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 127);

                final Animation animation = AnimationUtils.loadAnimation(AddProuctsActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(AddProuctsActivity.this, R.anim.bonceexit);
                binding.AddMobileImages5.startAnimation(animation);
                binding.AddMobileImages5.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.AddMobileImages5.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        //Uploading all details
        binding.UploadToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(AddProuctsActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(AddProuctsActivity.this, R.anim.bonceexit);
                binding.UploadToDatabase.startAnimation(animation);
                binding.UploadToDatabase.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.UploadToDatabase.startAnimation(animation1);
                    }
                }, 0);
                String productType = binding.EtProductType.getText().toString();
                String mobiles = "Mobiles";
                String fashion = "Fashion";
                String Electronics = "Electronics";
                if (productType.equals(mobiles)) {
                    if (binding.ETmobileCompany.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Mobile Company", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETmobileModel.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Mobile Model", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETMobileRam.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Mobile Ram", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtMobileStorage.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Mobile Storage", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETActualPrice.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Actual Price", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtSellingPrice.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Mobile Selling Price", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtDiscount.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Mobile Discount", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETMobileColor.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Mobile Color", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.Processor.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Mobile Processor", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.RareCAmera.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Mobile Rarea Camera", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.Size.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Mobile Display Size", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.BAttery.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Mobile Battery", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.NetworkType.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill Mobile Network Type", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.show();
                        final StorageReference reference = storage.getReference().child("Mobiles").child(FirebaseAuth.getInstance().getUid()).child("MainImage").child(new Date().getTime() + "");
                        final StorageReference reference2 = storage.getReference().child("Mobiles").child(FirebaseAuth.getInstance().getUid()).child("2ndImage").child(new Date().getTime() + "");
                        final StorageReference reference3 = storage.getReference().child("Mobiles").child(FirebaseAuth.getInstance().getUid()).child("3rdImage").child(new Date().getTime() + "");
                        final StorageReference reference4 = storage.getReference().child("Mobiles").child(FirebaseAuth.getInstance().getUid()).child("4thImage").child(new Date().getTime() + "");
                        final StorageReference reference5 = storage.getReference().child("Mobiles").child(FirebaseAuth.getInstance().getUid()).child("5thImage").child(new Date().getTime() + "");
                        ProductsModel mobileModel = new ProductsModel();
                        if (uri2 != null) {
                            reference2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            mobileModel.setImage2(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri3 != null) {
                            reference3.putFile(uri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            mobileModel.setImage3(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri4 != null) {
                            reference4.putFile(uri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            mobileModel.setImage4(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri5 != null) {
                            reference5.putFile(uri5).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            mobileModel.setImage5(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        mobileModel.setProductType(binding.EtProductType.getText().toString());
                                        mobileModel.setModel(binding.ETmobileModel.getText().toString());
                                        mobileModel.setMobileRam(binding.ETMobileRam.getText().toString());
                                        mobileModel.setSellingPrice(binding.EtSellingPrice.getText().toString());
                                        mobileModel.setActualPrice(binding.ETActualPrice.getText().toString());
                                        mobileModel.setImage(uri.toString());
                                        mobileModel.setAddedAt(new Date().getTime());
                                        mobileModel.setDicount(binding.EtDiscount.getText().toString());
                                        mobileModel.setColor(binding.ETMobileColor.getText().toString());
                                        mobileModel.setMobileStorage(binding.EtMobileStorage.getText().toString());
                                        mobileModel.setCompany(binding.ETmobileCompany.getText().toString());
                                        mobileModel.setAddedBy(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        mobileModel.setAddedAt(new Date().getTime());
                                        mobileModel.setMobileDisplay(binding.Size.getText().toString());
                                        mobileModel.setMobileRearCamera(binding.RareCAmera.getText().toString());
                                        mobileModel.setMobileProcessor(binding.Processor.getText().toString());
                                        mobileModel.setMobileBAttery(binding.BAttery.getText().toString());
                                        mobileModel.setMobilentworkType(binding.NetworkType.getText().toString());

                                        database.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                    String usersID = dataSnapshot.getKey();
                                                    if (usersID.equals(auth.getUid())) {
                                                    } else {
                                                        mobileModel.setNotificationOpen("false");
                                                        database.getReference().child("Users").child(usersID).child("Notification").push().setValue(mobileModel);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        database.getReference().child("Products").push().setValue(mobileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialog.dismiss();
                                                Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AddProuctsActivity.this, MobilesActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AddProuctsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                }
                if (productType.equals(fashion)) {
                    if (binding.ETPRoductFor.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETFashionCategory.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETFashionProductCompany.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETProductSpecification.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }

                    if (binding.ETActualPrice.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtSellingPrice.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtDiscount.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETMobileColor.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.show();
                        final StorageReference reference = storage.getReference().child("Fashion").child(FirebaseAuth.getInstance().getUid()).child(new Date().getTime() + "");
                        final StorageReference reference2 = storage.getReference().child("Fashion").child(FirebaseAuth.getInstance().getUid()).child("2ndImage").child(new Date().getTime() + "");
                        final StorageReference reference3 = storage.getReference().child("Fashion").child(FirebaseAuth.getInstance().getUid()).child("3rdImage").child(new Date().getTime() + "");
                        final StorageReference reference4 = storage.getReference().child("Fashion").child(FirebaseAuth.getInstance().getUid()).child("4thImage").child(new Date().getTime() + "");
                        final StorageReference reference5 = storage.getReference().child("Fashion").child(FirebaseAuth.getInstance().getUid()).child("5thImage").child(new Date().getTime() + "");
                        ProductsModel fashionModel = new ProductsModel();
                        if (uri2 != null) {
                            reference2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            fashionModel.setImage2(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri3 != null) {
                            reference3.putFile(uri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            fashionModel.setImage3(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri4 != null) {
                            reference4.putFile(uri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            fashionModel.setImage4(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri5 != null) {
                            reference5.putFile(uri5).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            fashionModel.setImage5(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        fashionModel.setFashionFor(binding.ETPRoductFor.getText().toString());
                                        fashionModel.setProductType(binding.EtProductType.getText().toString());
                                        fashionModel.setModel(binding.ETFashionCategory.getText().toString());
                                        fashionModel.setCompany(binding.ETFashionProductCompany.getText().toString());
                                        fashionModel.setActualPrice(binding.ETActualPrice.getText().toString());
                                        fashionModel.setSellingPrice(binding.EtSellingPrice.getText().toString());
                                        fashionModel.setDicount(binding.EtDiscount.getText().toString());
                                        fashionModel.setColor(binding.ETMobileColor.getText().toString());
                                        fashionModel.setImage(uri.toString());
                                        fashionModel.setFashionSpecification(binding.ETProductSpecification.getText().toString());
                                        fashionModel.setAddedBy(FirebaseAuth.getInstance().getUid());
                                        fashionModel.setAddedAt(new Date().getTime());
                                        database.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                    String usersID = dataSnapshot.getKey();
                                                    if (usersID.equals(auth.getUid())) {
                                                    } else {
                                                        fashionModel.setNotificationOpen("false");
                                                        database.getReference().child("Users").child(usersID).child("Notification").push().setValue(fashionModel);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        database.getReference().child("Products").push().setValue(fashionModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialog.dismiss();
                                                Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AddProuctsActivity.this, FashionActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AddProuctsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                }
                if (productType.equals(Electronics)) {
                    if (binding.EtElectronicsCategory.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtElectronicsCompany.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtElectronicModel.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtElectronicsSpecs.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETActualPrice.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtSellingPrice.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtDiscount.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETMobileColor.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.show();
                        final StorageReference reference = storage.getReference().child("Electronics").child(FirebaseAuth.getInstance().getUid()).child(new Date().getTime() + "");
                        final StorageReference reference2 = storage.getReference().child("Electronics").child(FirebaseAuth.getInstance().getUid()).child("2ndImage").child(new Date().getTime() + "");
                        final StorageReference reference3 = storage.getReference().child("Electronics").child(FirebaseAuth.getInstance().getUid()).child("3rdImage").child(new Date().getTime() + "");
                        final StorageReference reference4 = storage.getReference().child("Electronics").child(FirebaseAuth.getInstance().getUid()).child("4thImage").child(new Date().getTime() + "");
                        final StorageReference reference5 = storage.getReference().child("Electronics").child(FirebaseAuth.getInstance().getUid()).child("5thImage").child(new Date().getTime() + "");
                        ProductsModel electronicsModel = new ProductsModel();
                        if (uri2 != null) {
                            reference2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            electronicsModel.setImage2(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri3 != null) {
                            reference3.putFile(uri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            electronicsModel.setImage3(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri4 != null) {
                            reference4.putFile(uri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            electronicsModel.setImage4(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri5 != null) {
                            reference5.putFile(uri5).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            electronicsModel.setImage5(uri.toString());
                                        }
                                    });
                                }
                            });
                        }

                        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        electronicsModel.setImage(uri.toString());
                                        electronicsModel.setProductType(binding.EtProductType.getText().toString());
                                        electronicsModel.setElectronicsCategory(binding.EtElectronicsCategory.getText().toString());
                                        electronicsModel.setCompany(binding.EtElectronicsCompany.getText().toString());
                                        electronicsModel.setElectronicsSpecs(binding.EtElectronicsSpecs.getText().toString());
                                        electronicsModel.setActualPrice(binding.ETActualPrice.getText().toString());
                                        electronicsModel.setSellingPrice(binding.EtSellingPrice.getText().toString());
                                        electronicsModel.setDicount(binding.EtDiscount.getText().toString());
                                        electronicsModel.setColor(binding.ETMobileColor.getText().toString());
                                        electronicsModel.setElectronicsRam(binding.EtElectronicRam.getText().toString());
                                        electronicsModel.setElectronicsStorage(binding.EtElctronicStorage.getText().toString());
                                        electronicsModel.setModel(binding.EtElectronicModel.getText().toString());
                                        electronicsModel.setAddedAt(new Date().getTime());
                                        electronicsModel.setAddedBy(auth.getCurrentUser().getUid());
                                        database.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                    String usersID = dataSnapshot.getKey();
                                                    if (usersID.equals(auth.getUid())) {
                                                    } else {
                                                        electronicsModel.setNotificationOpen("false");
                                                        database.getReference().child("Users").child(usersID).child("Notification").push().setValue(electronicsModel);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        database.getReference().child("Products").push().setValue(electronicsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        dialog.dismiss();
                                                        Toast.makeText(AddProuctsActivity.this, "Product Added Succesfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(AddProuctsActivity.this, ElectronicsActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(AddProuctsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });
                            }
                        });
                    }
                }
                if (productType.equals("Home")) {
                    if (binding.categoryHome.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "fill details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.companyHomeAppliance.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "fill details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtAppliancespecs.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "fill details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETActualPrice.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtSellingPrice.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtDiscount.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETMobileColor.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.show();
                        final StorageReference reference = storage.getReference().child("HomeAppliance").child(auth.getCurrentUser().getUid()).child(new Date().getTime() + "");
                        final StorageReference reference2 = storage.getReference().child("HomeAppliance").child(FirebaseAuth.getInstance().getUid()).child("2ndImage").child(new Date().getTime() + "");
                        final StorageReference reference3 = storage.getReference().child("HomeAppliance").child(FirebaseAuth.getInstance().getUid()).child("3rdImage").child(new Date().getTime() + "");
                        final StorageReference reference4 = storage.getReference().child("HomeAppliance").child(FirebaseAuth.getInstance().getUid()).child("4thImage").child(new Date().getTime() + "");
                        final StorageReference reference5 = storage.getReference().child("HomeAppliance").child(FirebaseAuth.getInstance().getUid()).child("5thImage").child(new Date().getTime() + "");
                        ProductsModel productsModel = new ProductsModel();
                        if (uri2 != null) {
                            reference2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            productsModel.setImage2(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri3 != null) {
                            reference3.putFile(uri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            productsModel.setImage3(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri4 != null) {
                            reference4.putFile(uri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            productsModel.setImage4(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri5 != null) {
                            reference5.putFile(uri5).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            productsModel.setImage5(uri.toString());
                                        }
                                    });
                                }
                            });
                        }

                        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        productsModel.setImage(uri.toString());
                                        productsModel.setProductType(binding.EtProductType.getText().toString());
                                        productsModel.setCompany(binding.companyHomeAppliance.getText().toString());
                                        productsModel.setModel(binding.categoryHome.getText().toString());
                                        productsModel.setHomeApplianceSpecs(binding.EtAppliancespecs.getText().toString());
                                        productsModel.setActualPrice(binding.ETActualPrice.getText().toString());
                                        productsModel.setSellingPrice(binding.EtSellingPrice.getText().toString());
                                        productsModel.setDicount(binding.EtDiscount.getText().toString());
                                        productsModel.setColor(binding.ETMobileColor.getText().toString());
                                        productsModel.setAddedAt(new Date().getTime());
                                        productsModel.setAddedBy(auth.getCurrentUser().getUid());
                                        database.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                    String usersID = dataSnapshot.getKey();
                                                    if (usersID.equals(auth.getUid())) {
                                                    } else {
                                                        productsModel.setNotificationOpen("false");
                                                        database.getReference().child("Users").child(usersID).child("Notification").push().setValue(productsModel);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        database.getReference().child("Products").push().setValue(productsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialog.dismiss();
                                                finish();
                                                Toast.makeText(AddProuctsActivity.this, "Product Added Successfuly", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AddProuctsActivity.this, Home_Appliances_Activity.class);
                                                startActivity(intent);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AddProuctsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                }
                if (productType.equals("Appliances")) {
                    if (binding.EtappliaceProductType.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "fill details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtappliaceProductCompany.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "fill details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETAppliacSpecifications.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "fill details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETActualPrice.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtSellingPrice.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.EtDiscount.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                    if (binding.ETMobileColor.getText().toString().isEmpty()) {
                        Toast.makeText(AddProuctsActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.show();
                        final StorageReference reference = storage.getReference().child("Appliance").child(auth.getCurrentUser().getUid()).child(new Date().getTime() + "");
                        final StorageReference reference2 = storage.getReference().child("Appliance").child(FirebaseAuth.getInstance().getUid()).child("2ndImage").child(new Date().getTime() + "");
                        final StorageReference reference3 = storage.getReference().child("Appliance").child(FirebaseAuth.getInstance().getUid()).child("3rdImage").child(new Date().getTime() + "");
                        final StorageReference reference4 = storage.getReference().child("Appliance").child(FirebaseAuth.getInstance().getUid()).child("4thImage").child(new Date().getTime() + "");
                        final StorageReference reference5 = storage.getReference().child("Appliance").child(FirebaseAuth.getInstance().getUid()).child("5thImage").child(new Date().getTime() + "");
                        ProductsModel productsModel = new ProductsModel();
                        if (uri2 != null) {
                            reference2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            productsModel.setImage2(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri3 != null) {
                            reference3.putFile(uri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            productsModel.setImage3(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri4 != null) {
                            reference4.putFile(uri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            productsModel.setImage4(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        if (uri5 != null) {
                            reference5.putFile(uri5).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            productsModel.setImage5(uri.toString());
                                        }
                                    });
                                }
                            });
                        }
                        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        productsModel.setImage(uri.toString());
                                        productsModel.setProductType(binding.EtProductType.getText().toString());
                                        productsModel.setCompany(binding.EtappliaceProductCompany.getText().toString());
                                        productsModel.setModel(binding.EtappliaceProductType.getText().toString());
                                        productsModel.setAppliaceSpecification(binding.ETAppliacSpecifications.getText().toString());
                                        productsModel.setActualPrice(binding.ETActualPrice.getText().toString());
                                        productsModel.setSellingPrice(binding.EtSellingPrice.getText().toString());
                                        productsModel.setDicount(binding.EtDiscount.getText().toString());
                                        productsModel.setColor(binding.ETMobileColor.getText().toString());
                                        productsModel.setAddedAt(new Date().getTime());
                                        productsModel.setAddedBy(auth.getCurrentUser().getUid());
                                        database.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                    String usersID = dataSnapshot.getKey();
                                                    if (usersID.equals(auth.getUid())) {
                                                    } else {
                                                        productsModel.setNotificationOpen("false");
                                                        database.getReference().child("Users").child(usersID).child("Notification").push().setValue(productsModel);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        database.getReference().child("Products").push().setValue(productsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialog.dismiss();
                                                finish();
                                                Toast.makeText(AddProuctsActivity.this, "Product Added Successfuly", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AddProuctsActivity.this, AppliancesActivity.class);
                                                startActivity(intent);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AddProuctsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                }

            }
        });
        binding.EtElectronicsCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.EtElectronicsCategory.getText().toString().isEmpty()) {
                    Toast.makeText(AddProuctsActivity.this, "First Select Product Category", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.ETFashionCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.ETPRoductFor.getText().toString().isEmpty()) {
                    Toast.makeText(AddProuctsActivity.this, "First Select Above Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.ETFashionProductCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.ETPRoductFor.getText().toString().isEmpty()) {
                    Toast.makeText(AddProuctsActivity.this, "First Select Above Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.EtProductType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ProductType = binding.EtProductType.getText().toString();
                if (ProductType.equals("Mobiles")) {
                    binding.AddMobileLayout.setVisibility(View.VISIBLE);
                    binding.AddMobileImages.setVisibility(View.VISIBLE);
                    binding.AddMobileImages2.setVisibility(View.VISIBLE);
                    binding.AddMobileImages3.setVisibility(View.VISIBLE);
                    binding.CommonForAllPRoducts.setVisibility(View.VISIBLE);
                    binding.FashionLayout.setVisibility(View.GONE);
                    binding.AddMobileImages4.setVisibility(View.VISIBLE);
                    binding.AddMobileImages5.setVisibility(View.VISIBLE);
                    binding.ElectronicsLayout.setVisibility(View.GONE);
                    binding.HomeApplianceLayout.setVisibility(View.GONE);
                    binding.ApplianceLayout.setVisibility(View.GONE);
                }
                if (ProductType.equals("Fashion")) {
                    binding.AddMobileImages2.setVisibility(View.VISIBLE);
                    binding.ElectronicsLayout.setVisibility(View.GONE);
                    binding.AddMobileImages.setVisibility(View.VISIBLE);
                    binding.AddMobileImages3.setVisibility(View.VISIBLE);
                    binding.FashionLayout.setVisibility(View.VISIBLE);
                    binding.CommonForAllPRoducts.setVisibility(View.VISIBLE);
                    binding.AddMobileLayout.setVisibility(View.GONE);
                    binding.AddMobileImages4.setVisibility(View.VISIBLE);
                    binding.AddMobileImages5.setVisibility(View.VISIBLE);
                    binding.HomeApplianceLayout.setVisibility(View.GONE);
                    binding.ApplianceLayout.setVisibility(View.GONE);
                }
                if (ProductType.equals("Electronics")) {
                    binding.AddMobileImages2.setVisibility(View.VISIBLE);
                    binding.AddMobileImages3.setVisibility(View.VISIBLE);
                    binding.ElectronicsLayout.setVisibility(View.VISIBLE);
                    binding.HomeApplianceLayout.setVisibility(View.GONE);
                    binding.AddMobileImages.setVisibility(View.VISIBLE);
                    binding.FashionLayout.setVisibility(View.GONE);
                    binding.CommonForAllPRoducts.setVisibility(View.VISIBLE);
                    binding.AddMobileLayout.setVisibility(View.GONE);
                    binding.AddMobileImages4.setVisibility(View.VISIBLE);
                    binding.AddMobileImages5.setVisibility(View.VISIBLE);
                    binding.ApplianceLayout.setVisibility(View.GONE);
                }
                if (ProductType.equals("Home")) {
                    binding.ElectronicsLayout.setVisibility(View.GONE);
                    binding.HomeApplianceLayout.setVisibility(View.VISIBLE);
                    binding.AddMobileImages2.setVisibility(View.VISIBLE);
                    binding.AddMobileImages.setVisibility(View.VISIBLE);
                    binding.AddMobileImages3.setVisibility(View.VISIBLE);
                    binding.FashionLayout.setVisibility(View.GONE);
                    binding.AddMobileImages4.setVisibility(View.VISIBLE);
                    binding.AddMobileImages5.setVisibility(View.VISIBLE);
                    binding.ApplianceLayout.setVisibility(View.GONE);
                    binding.CommonForAllPRoducts.setVisibility(View.VISIBLE);
                    binding.AddMobileLayout.setVisibility(View.GONE);
                }
                if (ProductType.equals("Appliances")) {
                    binding.ElectronicsLayout.setVisibility(View.GONE);
                    binding.HomeApplianceLayout.setVisibility(View.GONE);
                    binding.AddMobileImages.setVisibility(View.VISIBLE);
                    binding.FashionLayout.setVisibility(View.GONE);
                    binding.AddMobileImages2.setVisibility(View.VISIBLE);
                    binding.AddMobileImages3.setVisibility(View.VISIBLE);
                    binding.AddMobileImages4.setVisibility(View.VISIBLE);
                    binding.AddMobileImages5.setVisibility(View.VISIBLE);
                    binding.ApplianceLayout.setVisibility(View.VISIBLE);
                    binding.CommonForAllPRoducts.setVisibility(View.VISIBLE);
                    binding.AddMobileLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                uri = data.getData();
                binding.MobilePhoto.setImageURI(uri);
                binding.MobilePhoto.setVisibility(View.VISIBLE);
                binding.UploadToDatabase.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.dicount_bg));
                binding.UploadToDatabase.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                binding.UploadToDatabase.setEnabled(true);
            }
        }
        if (requestCode == 124 && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                uri2 = data.getData();
                binding.MobilePhoto2.setImageURI(uri2);
                binding.MobilePhoto2.setVisibility(View.VISIBLE);
            }
        }
        if (requestCode == 125 && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                uri3 = data.getData();
                binding.MobilePhoto3.setImageURI(uri3);
                binding.MobilePhoto3.setVisibility(View.VISIBLE);
            }
        }
        if (requestCode == 126 && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                uri4 = data.getData();
                binding.MobilePhoto4.setImageURI(uri4);
                binding.MobilePhoto4.setVisibility(View.VISIBLE);
            }
        }
        if (requestCode == 127 && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                uri5 = data.getData();
                binding.MobilePhoto5.setImageURI(uri5);
                binding.MobilePhoto5.setVisibility(View.VISIBLE);
            }
        }
    }

}