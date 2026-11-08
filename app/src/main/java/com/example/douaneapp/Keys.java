package com.example.douaneapp;

// 1. فئة المفاتيح: تجمع كل مفاتيح putExtra في مكان واحد (الشخص A)
public final class Keys {

    // 2. آخر 4 أرقام من بطاقة الطالب لأحد أعضاء المجموعة (غيّروها برقمكم)
    public static final String MATRICULE = "1234";

    // 3. لواحق المفاتيح (ليست المفاتيح الكاملة، المفتاح الكامل يُبنى بالدالة الثابتة)
    public static final String SUFFIX_NOM = "NOM_VOYAGEUR";
    public static final String SUFFIX_NAISSANCE = "ANNEE_NAISSANCE";
    public static final String SUFFIX_DESTINATION = "ANNEE_DESTINATION";
    public static final String SUFFIX_MOTIF = "MOTIF_VOYAGE";
    public static final String SUFFIX_VERDICT = "VERDICT_FINAL";

    // 4. منع إنشاء كائنات من هذه الفئة لأنها فئة أدوات فقط
    private Keys() {
    }

    // 5. الدالة الثابتة: تأخذ رقم الطالب كمعامل وتبني المفتاح، مثال: "1234_NOM_VOYAGEUR"
    public static String build(String matricule, String suffix) {
        return matricule + "_" + suffix;
    }
}
