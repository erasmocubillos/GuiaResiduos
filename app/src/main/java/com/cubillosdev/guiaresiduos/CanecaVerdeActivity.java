package com.cubillosdev.guiaresiduos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.List;

public class CanecaVerdeActivity extends AppCompatActivity {

    private AdView adView;
    RadioButton opcion1, opcion2, opcion3, opcion4;
    TextView pregunta, encabezado, result;
    Button next;

    int numeroPregunta = 0;
    int nota1, nota2, nota3, suma;

    String estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caneca_verde);

        opcion1 = findViewById(R.id.opcion_1_v);
        opcion2 = findViewById(R.id.opcion_2_v);
        opcion3 = findViewById(R.id.opcion_3_v);
        opcion4 = findViewById(R.id.opcion_4_v);

        pregunta = findViewById(R.id.pregunta);
        encabezado = findViewById(R.id.encabezado);
        result = findViewById(R.id.result);

        next = findViewById(R.id.next);
        result.setVisibility(View.INVISIBLE);

        next.setOnClickListener(v -> {
            numeroPregunta++;
            numero();
        });

        ImageView img_btn = findViewById(R.id.btn_back_cv);

        img_btn.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.animateSlideRight(CanecaVerdeActivity.this);
        });

        ImageView img_btn2 = findViewById(R.id.btn_exit_cv);

        img_btn2.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            Animatoo.animateFade(CanecaVerdeActivity.this);
        });

        MobileAds.initialize(this, initializationStatus -> {
        });

        FrameLayout frameLayout = findViewById(R.id.frame_cv);
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.banner_id));
        frameLayout.addView(adView);
        loadBanner();
    }

    public void numero() {

        if (numeroPregunta == 1) {

            if (opcion2.isChecked()) {
                nota1 = 1;
                pregunta2();
            }
            else if(opcion1.isChecked()) {
                nota1 = 0;
                pregunta2();
            }
            else if(opcion3.isChecked()) {
                nota1 = 0;
                pregunta2();
            }
            else if(opcion4.isChecked()) {
                nota1 = 0;
                pregunta2();
            }
        }
        else if (numeroPregunta == 2) {

            if (opcion1.isChecked()) {
                nota2 = 1;
                pregunta3();
            }
            else if(opcion2.isChecked()) {
                nota2 = 0;
                pregunta3();
            }
            else if(opcion3.isChecked()) {
                nota2 = 0;
                pregunta3();
            }
            else if(opcion4.isChecked()) {
                nota2 = 0;
                pregunta3();
            }
        }
        else if (numeroPregunta == 3) {

            if (opcion2.isChecked()) {
                nota3 = 1;
                resultado();
            }
            else if(opcion1.isChecked()) {
                nota3 = 0;
                resultado();
            }
            else if(opcion3.isChecked()) {
                nota3 = 0;
                resultado();
            }
            else if(opcion4.isChecked()) {
                nota3 = 0;
                resultado();
            }
        }
    }

    public void pregunta2() {

        encabezado.setText(R.string.p_2);
        pregunta.setText(R.string.pregunta_2_cv);

        check();

        opcion1.setText(R.string.r_2_1_cv);
        opcion2.setText(R.string.r_2_2_cv);
        opcion3.setText(R.string.r_2_3_cv);
        opcion4.setText(R.string.r_2_4_cv);
    }

    public void pregunta3() {

        encabezado.setText(R.string.p_3);
        pregunta.setText(R.string.pregunta_3_cv);

        check();

        opcion1.setText(R.string.r_3_1_cv);
        opcion2.setText(R.string.r_3_2_cv);
        opcion3.setText(R.string.r_3_3_cv);
        opcion4.setText(R.string.r_3_4_cv);
    }

    @SuppressLint("SetTextI18n")
    public void resultado() {

        suma = nota1 + nota2 + nota3;

        if (suma >= 2) {
            estado = "Aprobado";
        }
        else {
            estado = "Reprobado";
        }

        encabezado.setText(R.string.encabezado);
        pregunta.setText("Nota obtenida: " + suma + "/3");

        opcion1.setVisibility(View.INVISIBLE);
        opcion2.setVisibility(View.INVISIBLE);
        opcion3.setVisibility(View.INVISIBLE);
        opcion4.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        result.setVisibility(View.VISIBLE);

        result.setText(estado);
    }

    public void check() {

        opcion1.setChecked(false);
        opcion2.setChecked(false);
        opcion3.setChecked(false);
        opcion4.setChecked(false);

    }

    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."

        List<String> testDevices = new ArrayList<>();
        testDevices.add(AdRequest.DEVICE_ID_EMULATOR);

        RequestConfiguration adRequest =
                new RequestConfiguration.Builder()
                        .setTestDeviceIds(testDevices)
                        .build();
        MobileAds.setRequestConfiguration(adRequest);

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);

        // Step 5 - Start loading the ad in the background.
        adView.loadAd(new AdRequest.Builder().build());
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }
}