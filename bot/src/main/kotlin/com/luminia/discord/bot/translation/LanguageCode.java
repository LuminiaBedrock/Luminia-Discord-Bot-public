package com.luminia.discord.bot.translation;

import java.util.HashMap;
import java.util.Map;

public enum LanguageCode {
    ENG("eng", "en_GB", "en_US"),
    DEU("deu", "de_DE"),
    ESP("esp", "es_ES", "es_MX"),
    FRA("fra", "fr_FR", "fr_CA"),
    ITA("ita", "it_IT"),
    JPN("jpn", "ja_JP"),
    KOR("kor", "ko_KR"),
    POR("por", "pt_BR", "pt_PT"),
    RUS("rus", "ru_RU"),
    ZH_CN("zh_cn", "zh_CN"),
    ZH_TW("zh_tw", "zh_TW"),
    NLD("nld", "nl_NL"),
    BGR("bgr", "bg_BG"),
    CES("ces", "cs_CZ"),
    DAN("dan", "da_DK"),
    ELL("ell", "el_GR"),
    FIN("fin", "fi_FI"),
    HUN("hun", "hu_HU"),
    IND("ind", "id_ID"),
    NOR("nor", "nb_NO"),
    POL("pol", "pl_PL"),
    SLK("slk", "sk_SK"),
    SWE("swe", "sv_SE"),
    TRK("trk", "tr_TR"),
    UKR("ukr", "uk_UA");

    private final String name;
    private final String[] codes;

    private static final Map<String, LanguageCode> BY_NAME = new HashMap<>();
    private static final Map<String, LanguageCode> BY_CODE = new HashMap<>();

    private LanguageCode(String name, String... codes) {
        this.name = name;
        this.codes = codes;
    }

    public String getName() {
        return name;
    }

    public String[] getLangCodes() {
        return codes;
    }

    public static LanguageCode getByName(String name) {
        return BY_NAME.get(name);
    }

    public static LanguageCode getByCode(String code) {
        return BY_CODE.get(code);
    }

    static {
        for (LanguageCode languageCode : values()) {
            BY_NAME.put(languageCode.getName(), languageCode);

            for (String langCode : languageCode.getLangCodes()) {
                BY_CODE.put(langCode, languageCode);
            }
        }
    }
}