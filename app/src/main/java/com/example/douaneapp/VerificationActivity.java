import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.douaneapp.ResultActivity;

/**
 * النشاط 2: ماسح المفارقة الزمنية (جواز السفر)
 */
public class VerificationActivity extends AppCompatActivity {

    private TextView tvNom;
    private TextView tvAge;
    private TextView tvMotif;
    private TextView tvTrajet;
    private TextView tvAvertissement;
    private Button btnApprouver;
    private Button btnRefuser;

    private String nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        // 1) ربط العناصر بالـ XML
        tvNom = findViewById(R.id.tv_nom_aj);
        tvAge = findViewById(R.id.tv_age_aj);
        tvMotif = findViewById(R.id.tv_motif_aj);
        tvTrajet = findViewById(R.id.tv_trajet_aj);
        tvAvertissement = findViewById(R.id.tv_avertissement_aj);
        btnApprouver = findViewById(R.id.btn_approuver_aj);
        btnRefuser = findViewById(R.id.btn_refuser_aj);

        // 2) قراءة البيانات المرسلة (المفاتيح تُبنى بالدالة static)
        Intent recu = getIntent();
        nom = recu.getStringExtra(Keys.build(Keys.MATRICULE, Keys.SUFFIX_NOM));
        int naissance = recu.getIntExtra(Keys.build(Keys.MATRICULE, Keys.SUFFIX_NAISSANCE), 0);
        int destination = recu.getIntExtra(Keys.build(Keys.MATRICULE, Keys.SUFFIX_DESTINATION), 0);
        String motif = recu.getStringExtra(Keys.build(Keys.MATRICULE, Keys.SUFFIX_MOTIF));

        // 3) حساب العمر الزمني = سنة الوجهة - سنة الميلاد
        int ageTemporel = destination - naissance;
        // 5.1) حالة خاصة: سنة الوجهة قبل سنة الميلاد
        if (ageTemporel < 0) {
            tvAvertissement.setText("⚠ Année de destination avant la naissance");
        }

        // 4) عرض الملخص على شكل "جواز سفر"
        tvNom.setText("Nom : " + nom);
        tvAge.setText("Âge temporal : " + ageTemporel + " ans");
        tvMotif.setText("Motif : " + motif);
        tvTrajet.setText("Trajet : " + naissance + " → " + destination);

        // 5) القاعدة: إذا كان السبب "Modification du passé" والفرق عدد أولي
        //    يتم تعطيل زر "Approuver" فور تحميل النشاط
        if (getString(R.string.motif_modification).equals(motif) && estPremier(Math.abs(ageTemporel))) {
            btnApprouver.setEnabled(false);
            tvAvertissement.setText("⚠ Paradoxe detected : approbation impossible");
        } else {
            tvAvertissement.setText("Audun paradoxe detected");
        }

        // 6) زر الموافقة -> verdict = true
        btnApprouver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerVerdict(true);
            }
        });

        // 7) زر الرفض -> verdict = false
        btnRefuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lancerVerdict(false);
            }
        });
    }

    /**
     * 8) إطلاق النشاط 3 مع الحكم (boolean) واسم المسافر
     */
    private void lancerVerdict(boolean approuve) {
        Intent intent = new Intent(VerificationActivity.this, ResultActivity.class);
        intent.putExtra(Keys.build(Keys.MATRICULE, Keys.SUFFIX_VERDICT), approuve);
        intent.putExtra(Keys.build(Keys.MATRICULE, Keys.SUFFIX_NOM), nom);
        startActivity(intent);
    }

    /**
     * 9) دالة اختبار العدد الأولي
     * الأعداد الأصغر من 2 (بما فيها السالبة) ليست أولية
     */
    private boolean estPremier(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; (long) i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}