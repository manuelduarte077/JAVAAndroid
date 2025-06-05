package dev.donmanuel.solidandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.donmanuel.solidandroid.data.local.SharedPreferencesManager;
import dev.donmanuel.solidandroid.data.local.UserPreferences;
import dev.donmanuel.solidandroid.data.repository.AuthRepositoryImpl;
import dev.donmanuel.solidandroid.domain.model.User;
import dev.donmanuel.solidandroid.domain.repository.AuthRepository;
import dev.donmanuel.solidandroid.domain.usecase.GetCurrentUserUseCase;
import dev.donmanuel.solidandroid.domain.usecase.LogoutUseCase;
import dev.donmanuel.solidandroid.presentation.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvUserInfo;
    private Button btnLogout;
    
    private LogoutUseCase logoutUseCase;
    private GetCurrentUserUseCase getCurrentUserUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        
        // Inicializar vistas
        tvUserInfo = findViewById(R.id.tvUserInfo);
        btnLogout = findViewById(R.id.btnLogout);
        
        // Configurar insets para EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        // Inicializar casos de uso
        setupUseCases();
        
        // Mostrar información del usuario
        displayUserInfo();
        
        // Configurar botón de cierre de sesión
        setupLogoutButton();
    }
    
    private void setupUseCases() {
        // Crear las dependencias siguiendo el principio de Inversión de Dependencias
        UserPreferences userPreferences = new SharedPreferencesManager(this);
        AuthRepository authRepository = new AuthRepositoryImpl(userPreferences);
        
        logoutUseCase = new LogoutUseCase(authRepository);
        getCurrentUserUseCase = new GetCurrentUserUseCase(authRepository);
    }
    
    private void displayUserInfo() {
        // Obtener el usuario actual
        User currentUser = getCurrentUserUseCase.execute();
        
        if (currentUser != null) {
            String userInfo = "Usuario: " + currentUser.getEmail();
            if (currentUser.getName() != null && !currentUser.getName().isEmpty()) {
                userInfo += "\nNombre: " + currentUser.getName();
            }
            tvUserInfo.setText(userInfo);
        } else {
            // Si no hay usuario autenticado, redirigir a la pantalla de login
            navigateToLogin();
        }
    }
    
    private void setupLogoutButton() {
        btnLogout.setOnClickListener(v -> {
            // Cerrar sesión
            logoutUseCase.execute();
            
            // Redirigir a la pantalla de login
            navigateToLogin();
        });
    }
    
    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}