import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        nom = recu.getStringExtra(CleExtras.cle(CleExtras.MATRICULE, CleExtras.NOM));
        int naissance = recu.getIntExtra(CleExtras.cle(CleExtras.MATRICULE, CleExtras.NAISSANCE), 0);
        int destination = recu.getIntExtra(CleExtras.cle(CleExtras.MATRICULE, CleExtras.DESTINATION), 0);
        String motif = recu.getStringExtra(CleExtras.cle(CleExtras.MATRICULE, CleExtras.MOTIF));

        // 3) حساب العمر الزمني = سنة الوجهة - سنة الميلاد
        int ageTemporel = destination - naissance;
        // 5.1) حالة خاصة: سنة الوجهة قبل سنة الميلاد
        if (ageTemporel < 0) {
            btnApprouver.setEnabled(false);
            tvAvertissement.setText("⚠ Année de destination avant la naissance");
        }

        // 4) عرض الملخص على شكل "جواز سفر"
        tvNom.setText("Nom : " + nom);
        tvAge.setText("Âge temporel : " + ageTemporel + " ans");
        tvMotif.setText("Motif : " + motif);
        tvTrajet.setText("Trajet : " + naissance + " → " + destination);

        // 5) القاعدة: إذا كان السبب "Modification du passé" والفرق عدد أولي
        //    يتم تعطيل زر "Approuver" فور تحميل النشاط
        if (CleExtras.MOTIF_MODIF_PASSE.equals(motif) && estPremier(ageTemporel)) {
            btnApprouver.setEnabled(false);
            tvAvertissement.setText("⚠ Paradoxe détecté : approbation impossible");
        } else {
            tvAvertissement.setText("Aucun paradoxe détecté");
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
        intent.putExtra(CleExtras.cle(CleExtras.MATRICULE, CleExtras.VERDICT), approuve);
        intent.putExtra(CleExtras.cle(CleExtras.MATRICULE, CleExtras.NOM), nom);
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