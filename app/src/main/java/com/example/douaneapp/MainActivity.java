package com.example.douaneapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// النشاط 1: استمارة تسجيل المسافر (الشخص A)
public class MainActivity extends AppCompatActivity {

    // 1. تعريف عناصر الواجهة
    private EditText etNom;
    private EditText etNaissance;
    private EditText etDestination;
    private RadioGroup rgMotif;
    private Button btnInspecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. ربط العناصر بملف الواجهة عبر المعرّفات
        etNom = findViewById(R.id.et_nom_ha);
        etNaissance = findViewById(R.id.et_naissance_ha);
        etDestination = findViewById(R.id.et_destination_ha);
        rgMotif = findViewById(R.id.rg_motif_ha);
        btnInspecter = findViewById(R.id.btn_inspecter_ha);
    }

    // 3. التحقق من صحة الحقول قبل الانتقال
    private boolean formulaireValide() {
        // 3.1 الاسم لا يجب أن يكون فارغا
        if (etNom.getText().toString().trim().isEmpty()) {
            etNom.setError(getString(R.string.erreur_nom));
            return false;
        }
        // 3.2 سنة الميلاد
        if (etNaissance.getText().toString().trim().isEmpty()) {
            etNaissance.setError(getString(R.string.erreur_naissance));
            return false;
        }
        // 3.3 سنة الوجهة
        if (etDestination.getText().toString().trim().isEmpty()) {
            etDestination.setError(getString(R.string.erreur_destination));
            return false;
        }
        // 3.4 يجب اختيار دافع السفر
        if (rgMotif.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.erreur_motif, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}