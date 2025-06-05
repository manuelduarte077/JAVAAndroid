package dev.donmanuel.solidandroid.presentation.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import dev.donmanuel.solidandroid.R;

/**
 * Fragmento para la pantalla de registro
 * (Principio de Responsabilidad Única - solo maneja la UI de registro)
 */
public class RegisterFragment extends Fragment {

    private LoginViewModel viewModel;
    private TextInputEditText etName;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private Button btnRegister;
    private TextView tvError;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener referencias a las vistas
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnRegister = view.findViewById(R.id.btnRegister);
        tvError = view.findViewById(R.id.tvError);

        // Obtener el ViewModel compartido desde la actividad
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        // Configurar listeners
        setupListeners();

        // Observar cambios en el estado del registro
        observeViewModel();
    }

    private void setupListeners() {
        btnRegister.setOnClickListener(v -> {
            String name = etName.getText() != null ? etName.getText().toString() : "";
            String email = etEmail.getText() != null ? etEmail.getText().toString() : "";
            String password = etPassword.getText() != null ? etPassword.getText().toString() : "";
            viewModel.register(name, email, password);
        });
    }

    private void observeViewModel() {
        // Observar el estado del registro
        viewModel.getLoginState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case LOADING:
                    showLoading(true);
                    break;
                case SUCCESS:
                    showLoading(false);
                    // La navegación a la pantalla principal se maneja en la actividad
                    break;
                case ERROR:
                    showLoading(false);
                    break;
                case IDLE:
                    showLoading(false);
                    break;
            }
        });

        // Observar mensajes de error
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                tvError.setText(message);
                tvError.setVisibility(View.VISIBLE);
            } else {
                tvError.setVisibility(View.GONE);
            }
        });

        // Observar el estado de carga
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), this::showLoading);
    }

    private void showLoading(boolean isLoading) {
        btnRegister.setEnabled(!isLoading);
        // El ProgressBar se maneja en la actividad
    }
}
