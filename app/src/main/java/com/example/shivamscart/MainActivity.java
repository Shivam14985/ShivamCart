package com.example.shivamscart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.shivamscart.Fragments.CartFragment;
import com.example.shivamscart.Fragments.CategoriesFragment;
import com.example.shivamscart.Fragments.HomeFragment;
import com.example.shivamscart.Fragments.NotificationFragment;
import com.example.shivamscart.Fragments.PRofileFragment;
import com.example.shivamscart.Models.Users;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityMainBinding;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    //Double press back to exit app
    boolean doubleBackToExitPressedOnce = false;

    private BroadcastReceiver broadcastReceiver;

    public static int dpToPx(Context context, int dp) {
        Resources resources = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        FirebaseApp.initializeApp(this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance());
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.Hhome) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, new HomeFragment());
                    fragmentTransaction.commit();
                }
                if (item.getItemId() == R.id.catogories) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, new CategoriesFragment());
                    fragmentTransaction.commit();
                }
                if (item.getItemId() == R.id.notification) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, new NotificationFragment());
                    fragmentTransaction.commit();

                }
                if (item.getItemId() == R.id.profile) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, new PRofileFragment());
                    fragmentTransaction.commit();
                }
                if (item.getItemId() == R.id.Cart) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, new CartFragment());
                    fragmentTransaction.commit();
                }
                return true;
            }
        });
        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Notification").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.child("notificationOpen").getValue().equals("false")) {
                                BadgeDrawable badgeDrawable = binding.bottomNavigation.getOrCreateBadge(R.id.notification);
                                badgeDrawable.setVisible(true);
                                badgeDrawable.setVerticalOffset(dpToPx(MainActivity.this, 5));
                                badgeDrawable.setHorizontalOffset(dpToPx(MainActivity.this, 7));
                                badgeDrawable.setBadgeTextColor(getResources().getColor(R.color.white));
                            } else {

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("cartNumber").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Users users=snapshot.getValue(Users.class);
//                        BadgeDrawable badgeDrawable = binding.bottomNavigation.getOrCreateBadge(R.id.Cart);
//                        badgeDrawable.setVisible(true);
//                        badgeDrawable.setNumber(users.getCartNumber());
//                        badgeDrawable.setVerticalOffset(dpToPx(MainActivity.this, 5));
//                        badgeDrawable.setHorizontalOffset(dpToPx(MainActivity.this, 7));
//                        badgeDrawable.setBadgeTextColor(getResources().getColor(R.color.white));
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
            }
        }, 0);

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}