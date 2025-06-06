package dev.donmanuel.solidpermission;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.donmanuel.solidpermission.permission.PermissionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PermissionManager.PermissionCallback {

    private PermissionManager permissionManager;
    
    // UI Components
    private TextView statusTextView;
    private Button storagePermissionButton;
    private Button locationPermissionButton;
    private Button cameraPermissionButton;
    private Button microphonePermissionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar componentes de la UI
        initializeUI();
        
        // Inicializar el gestor de permisos
        permissionManager = new PermissionManager(this);
        permissionManager.setCallback(this);
    }

    private void initializeUI() {
        statusTextView = findViewById(R.id.statusTextView);
        
        storagePermissionButton = findViewById(R.id.storagePermissionButton);
        locationPermissionButton = findViewById(R.id.locationPermissionButton);
        cameraPermissionButton = findViewById(R.id.cameraPermissionButton);
        microphonePermissionButton = findViewById(R.id.microphonePermissionButton);
        
        storagePermissionButton.setOnClickListener(this);
        locationPermissionButton.setOnClickListener(this);
        cameraPermissionButton.setOnClickListener(this);
        microphonePermissionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        
        if (id == R.id.storagePermissionButton) {
            permissionManager.requestPermission(PermissionManager.STORAGE_PERMISSION_REQUEST_CODE);
        } else if (id == R.id.locationPermissionButton) {
            permissionManager.requestPermission(PermissionManager.LOCATION_PERMISSION_REQUEST_CODE);
        } else if (id == R.id.cameraPermissionButton) {
            permissionManager.requestPermission(PermissionManager.CAMERA_PERMISSION_REQUEST_CODE);
        } else if (id == R.id.microphonePermissionButton) {
            permissionManager.requestPermission(PermissionManager.MICROPHONE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionRequested(int requestCode, String permissionName) {
        updateStatus("Solicitando permiso de " + permissionName + "...");
    }

    @Override
    public void onPermissionResult(int requestCode, boolean isGranted, String message) {
        updateStatus(message);
    }

    private void updateStatus(String status) {
        statusTextView.setText("Estado: " + status);
    }
}