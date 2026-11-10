package com.example.douaneapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// النشاط 3: الحكم النهائي (الشخص C)
public class ResultActivity extends AppCompatActivity {

    // 1. تعريف عناصر الواجهة
    private LinearLayout root;
    private TextView tvMessage;
    private Button btnNouveau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 2. ربط العناصر بملف الواجهة
        root = findViewById(R.id.root_result_lz);
        tvMessage = findViewById(R.id.tv_message_lz);
        btnNouveau = findViewById(R.id.btn_nouveau_lz);

        // 3. قراءة البيانات القادمة من النشاط 2 بمفاتيح مبنية بالدالة الثابتة
        String matricule = Keys.MATRICULE;
        String nom = getIntent().getStringExtra(Keys.build(matricule, Keys.SUFFIX_NOM));
        boolean approuve = getIntent().getBooleanExtra(
                Keys.build(matricule, Keys.SUFFIX_VERDICT), false);

        // 4. تغيير لون الخلفية والرسالة حسب الحكم
        if (approuve) {
            // 4.1 الموافقة: خلفية خضراء
            root.setBackgroundColor(Color.parseColor("#2E7D32"));
            tvMessage.setText(getString(R.string.msg_approuve, nom));
        } else {
            // 4.2 الرفض: خلفية حمراء
            root.setBackgroundColor(Color.parseColor("#C62828"));
            tvMessage.setText(getString(R.string.msg_refuse, nom));
        }
    }
}