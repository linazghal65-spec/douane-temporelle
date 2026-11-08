package com.example.douaneapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

        // 3. عند الضغط على الزر نفحص الملف الشخصي
        btnInspecter.setOnClickListener(v -> inspecterProfil());
    }

    // 4. التحقق من صحة الحقول قبل الانتقال
    private boolean formulaireValide() {
        // 4.1 الاسم لا يجب أن يكون فارغا
        if (etNom.getText().toString().trim().isEmpty()) {
            etNom.setError(getString(R.string.erreur_nom));
            return false;
        }
        // 4.2 سنة الميلاد
        if (etNaissance.getText().toString().trim().isEmpty()) {
            etNaissance.setError(getString(R.string.erreur_naissance));
            return false;
        }
        // 4.3 سنة الوجهة
        if (etDestination.getText().toString().trim().isEmpty()) {
            etDestination.setError(getString(R.string.erreur_destination));
            return false;
        }
        // 4.4 يجب اختيار دافع السفر
        if (rgMotif.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.erreur_motif, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 5. جمع البيانات وإطلاق النشاط 2 بواسطة Intent صريح
    private void inspecterProfil() {
        // 5.1 إذا كانت الاستمارة غير صحيحة نتوقف
        if (!formulaireValide()) {
            return;
        }

        // 5.2 قراءة القيم (الحقول محدودة بـ 4 أرقام فلا خطر من تجاوز حجم int)
        String nom = etNom.getText().toString().trim();
        int naissance = Integer.parseInt(etNaissance.getText().toString().trim());
        int destination = Integer.parseInt(etDestination.getText().toString().trim());

        // 5.3 قراءة نص الدافع من زر الراديو المحدد
        RadioButton rbChoisi = findViewById(rgMotif.getCheckedRadioButtonId());
        String motif = rbChoisi.getText().toString();

        // 5.4 إنشاء Intent صريح نحو VerificationActivity
        Intent intent = new Intent(MainActivity.this, VerificationActivity.class);

        // 5.5 إضافة البيانات بمفاتيح تُبنى بالدالة الثابتة (لا مفاتيح مكتوبة يدويا)
        String matricule = Keys.MATRICULE;
        intent.putExtra(Keys.build(matricule, Keys.SUFFIX_NOM), nom);
        intent.putExtra(Keys.build(matricule, Keys.SUFFIX_NAISSANCE), naissance);
        intent.putExtra(Keys.build(matricule, Keys.SUFFIX_DESTINATION), destination);
        intent.putExtra(Keys.build(matricule, Keys.SUFFIX_MOTIF), motif);

        // 5.6 الانتقال إلى النشاط 2
        startActivity(intent);
    }
}