package ru.nikshlykov.schoolhelper.ui;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import ru.nikshlykov.schoolhelper.R;

public class AuthActivity extends AppCompatActivity implements OnFragmentInteractionListener{
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        navController = Navigation.findNavController(this, R.id.activity_auth___nav_host_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(NavDirections navDirections) {
        navController.navigate(navDirections);
    }
}
