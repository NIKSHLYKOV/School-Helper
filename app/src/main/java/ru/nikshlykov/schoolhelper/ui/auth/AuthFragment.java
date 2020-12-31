package ru.nikshlykov.schoolhelper.ui.auth;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import ru.nikshlykov.schoolhelper.R;
import ru.nikshlykov.schoolhelper.ui.OnFragmentInteractionListener;

public class AuthFragment extends Fragment {

    private TextInputEditText loginEditText;
    private TextInputEditText passwordEditText;
    private MaterialButton signInButton;

    private AuthViewModel authViewModel;

    private OnFragmentInteractionListener onFragmentInteractionListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener =
                    (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        authViewModel =
                new ViewModelProvider(this).get(AuthViewModel.class);

        View root = inflater.inflate(R.layout.fragment_auth, container, false);
        loginEditText = root.findViewById(R.id.fragment_auth___edit_text___login);
        passwordEditText = root.findViewById(R.id.fragment_auth___edit_text___password);
        signInButton = root.findViewById(R.id.fragment_auth___button___sign_in);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signInButton.setOnClickListener(view1 -> {
            signInButton.setEnabled(false);
            authViewModel.signIn(loginEditText.getText().toString(), passwordEditText.getText().toString());
        });

        authViewModel.getSignInStatus().observe(getViewLifecycleOwner(), status -> {
            Log.i(AuthFragment.class.getCanonicalName(), "status: " + status);
            switch (status){
                case -1:
                    Toast.makeText(getContext(), "Неправильный логин или пароль!", Toast.LENGTH_SHORT).show();
                    signInButton.setEnabled(true);
                    break;
                case 1:
                    NavDirections navDirections = AuthFragmentDirections.actionAuthFragmentToMainActivity();
                    onFragmentInteractionListener.onFragmentInteraction(navDirections);
                    break;
            }
        });
    }
}
