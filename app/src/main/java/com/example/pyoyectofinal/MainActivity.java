package com.example.pyoyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText contraseña;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vinculación de vistas
        usuario = findViewById(R.id.e_txt_usuario);
        contraseña = findViewById(R.id.e_txt_password);
        loginButton = findViewById(R.id.bottom_ingresar);

        loginButton.setOnClickListener(v -> {
            irAUsuarioActivity();
        });
    }

    private void irAUsuarioActivity() {
        Intent intent = new Intent(MainActivity.this, UsuarioActivity.class);
        startActivity(intent);

        finish();
    }
}
