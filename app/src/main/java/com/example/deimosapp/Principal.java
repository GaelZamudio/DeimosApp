package com.example.deimosapp;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.example.deimosapp.Base;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;

public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Perfil.OnPerfilUpdatedListener {
    private static final int NAV_INICIO = R.id.nav_inicio;
    private static final int NAV_PERFIL = R.id.nav_perfil;
    private static final int NAV_OBRAS = R.id.nav_obras;
    private static final int NAV_SALIR = R.id.nav_salir;
    TextView tvNavNombreUsuario;
    TextView tvNavEmail;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_Layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Accede a la cabecera del NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Encuentra los TextView en la cabecera del NavigationView
        tvNavNombreUsuario = headerView.findViewById(R.id.tvNavNombreUsuario);
        tvNavEmail = headerView.findViewById(R.id.tvNavEmail);

        // Recupera los datos guardados en SharedPreferences
        SharedPreferences prefs = getSharedPreferences("DeimosPrefs", MODE_PRIVATE);
        String nombre = prefs.getString("nombre", "Usuario");
        String correo = prefs.getString("correo", "correo@gmail.com");

        // Establece los datos recuperados en los TextView
        tvNavNombreUsuario.setText(nombre);
        tvNavEmail.setText(correo);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Galeria()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int itemId = item.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);
        if (itemId == NAV_INICIO) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Galeria()).commit();
        } else if (itemId == NAV_PERFIL) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Perfil()).commit();
        } else if (itemId == NAV_OBRAS) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Obras()).commit();
        } else if (itemId == NAV_SALIR) {
            Intent intent = new Intent(this, LogIn.class);
            startActivity(intent);
        }
        return true;
    }

    public void actualizarNavText(String nombre, String correo){
        tvNavNombreUsuario.setText(nombre);
        tvNavEmail.setText(correo);
    }

    @Override
    public void onPerfilUpdated(String nuevoNombre, String nuevoCorreo) {
        // Actualiza los TextViews en la barra de navegación con los nuevos valores
        tvNavNombreUsuario.setText(nuevoNombre);
        tvNavEmail.setText(nuevoCorreo);
    }
}