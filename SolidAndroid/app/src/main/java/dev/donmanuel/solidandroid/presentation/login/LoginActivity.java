package dev.donmanuel.solidandroid.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import dev.donmanuel.solidandroid.MainActivity;
import dev.donmanuel.solidandroid.R;
import dev.donmanuel.solidandroid.data.local.SharedPreferencesManager;
import dev.donmanuel.solidandroid.data.local.UserPreferences;
import dev.donmanuel.solidandroid.data.repository.AuthRepositoryImpl;
import dev.donmanuel.solidandroid.domain.repository.AuthRepository;
import dev.donmanuel.solidandroid.domain.usecase.GetCurrentUserUseCase;
import dev.donmanuel.solidandroid.domain.usecase.LoginUseCase;
import dev.donmanuel.solidandroid.domain.usecase.RegisterUseCase;
import dev.donmanuel.solidandroid.util.EmailValidator;

/**
 * Actividad principal para la autenticación
 * (Principio de Responsabilidad Única - solo maneja la UI de autenticación)
 */
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar componentes de UI
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        progressBar = findViewById(R.id.progressBar);

        // Configurar el adaptador del ViewPager2
        LoginPagerAdapter pagerAdapter = new LoginPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Conectar TabLayout con ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Iniciar sesión");
            } else {
                tab.setText("Registrarse");
            }
        }).attach();

        // Inicializar el ViewModel con sus dependencias
        setupViewModel();

        // Observar el estado de autenticación
        observeAuthState();
    }

    private void setupViewModel() {
        // Crear las dependencias siguiendo el principio de Inversión de Dependencias
        UserPreferences userPreferences = new SharedPreferencesManager(this);
        AuthRepository authRepository = new AuthRepositoryImpl(userPreferences);
        
        LoginUseCase loginUseCase = new LoginUseCase(authRepository);
        RegisterUseCase registerUseCase = new RegisterUseCase(authRepository);
        GetCurrentUserUseCase getCurrentUserUseCase = new GetCurrentUserUseCase(authRepository);
        EmailValidator emailValidator = new EmailValidator();

        // Crear una Factory personalizada para el ViewModel
        LoginViewModelFactory factory = new LoginViewModelFactory(
                loginUseCase, registerUseCase, getCurrentUserUseCase, emailValidator);
        
        // Obtener el ViewModel
        viewModel = new androidx.lifecycle.ViewModelProvider(this, factory).get(LoginViewModel.class);
    }

    private void observeAuthState() {
        // Observar el estado de autenticación
        viewModel.getLoginState().observe(this, state -> {
            switch (state) {
                case LOADING:
                    showLoading(true);
                    break;
                case SUCCESS:
                    showLoading(false);
                    navigateToMainActivity();
                    break;
                case ERROR:
                    showLoading(false);
                    break;
                case IDLE:
                    showLoading(false);
                    break;
            }
        });

        // Observar el estado de carga
        viewModel.getIsLoading().observe(this, this::showLoading);

        // Verificar si hay un usuario con sesión activa
        viewModel.checkCurrentUser();
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
