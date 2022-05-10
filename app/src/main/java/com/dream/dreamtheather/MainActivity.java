package com.dream.dreamtheather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dream.dreamtheather.Fragment.AccountTabFragment;
import com.dream.dreamtheather.Fragment.BookingFragment;
import com.dream.dreamtheather.Fragment.HomeTabFragment;
import com.dream.dreamtheather.Fragment.TheatherFragment;
import com.dream.dreamtheather.Model.UserInfo;
import com.dream.dreamtheather.data.MyPrefs;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final int HOME = R.id.navigation_home,
            THEATHER = R.id.navigation_theather,
            ACCOUNT = R.id.navigation_account ;

    public FirebaseFirestore firebaseFirestore;
    public FirebaseUser user;
    FirebaseAuth mAuth;
    MyPrefs myPrefs;

    private BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

<<<<<<< HEAD
        mDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        myPrefs = new MyPrefs(this);
=======
        firebaseFirestore = FirebaseFirestore.getInstance();
>>>>>>> 0c5140b059449ea301819b178bebe15ed4286866

        getUserType();
    }

    private final NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                Fragment fragment;
                switch (item.getItemId()) {
                    case HOME :
                        fragment = new HomeTabFragment();
                        loadFragment(fragment);
                        return true;
                    case THEATHER :
                        fragment = new TheatherFragment();
                        loadFragment(fragment);
                        return true;
                    case ACCOUNT :
                        fragment = new AccountTabFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            };

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentHomeTab, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void restartHomeScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        finish();
        startActivity(intent);
    }

    private void getUserType(){
        if(myPrefs.getIsSignIn()){
            String id = user.getUid();

            DocumentReference userGet = mDb.collection("user_info").document(id);
            userGet.get()
                    .addOnSuccessListener(documentSnapshot -> {
                        UserInfo info = documentSnapshot.toObject(UserInfo.class);
                        if(!info.getUserType().matches("")){
                            myPrefs.setIsAdmin(true);
                        }
                        else{
                            myPrefs.setIsAdmin(false);
                        }
                    })
                    .addOnFailureListener(f -> {
                        Toast.makeText(MainActivity.this,"Có vấn đề khi gọi Firebase",Toast.LENGTH_LONG).show();
                    });
        }
        else{
            myPrefs.setIsAdmin(false);
        }
    }
}