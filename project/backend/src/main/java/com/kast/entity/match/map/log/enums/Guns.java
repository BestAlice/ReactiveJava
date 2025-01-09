package com.kast.entity.match.map.log.enums;

import org.jetbrains.annotations.NotNull;

public enum Guns {
    AK47("ak47"),
    AUG("aug"),
    AWP("awp"),
    BAYONET("bayonet"),
    BIZON("bizon"),
    CZ75A("cz75a"),
    DEAGLE("deagle"),
    DECOY("decoy"),
    ELITE("elite"),
    FAMAS("famas"),
    FIVESEVEN("fiveseven"),
    FLASHBANG("flashbang"),
    G3SG1("g3sg1"),
    GALILAR("galilar"),
    GLOCK("glock"),
    HEGRENADE("Hegrenade"),
    HKP2000("hkp2000"),
    INCGRENADE("incgrenade"),
    INFERNO("inferno"),
    KNIFE("knife"),
    KNIFE_BOWIE("knife_bowie"),
    KNIFE_BUTTERFLY("knife_butterfly"),
    KNIFE_CANIS("knife_canis"),
    KNIFE_CORD("knife_cord"),
    KNIFE_CSS("knife_css"),
    KNIFE_FALCHION("knife_falchion"),
    KNIFE_FLIP("knife_flip"),
    KNIFE_GUT("knife_gut"),
    KNIFE_GYPSY_JACKKNIFE("knife_gypsy_jackknife"),
    KNIFE_KARAMBIT("knife_karambit"),
    KNIFE_KUKRI("knife_kukri"),
    KNIFE_M9_BAYONET("knife_m9_bayonet"),
    KNIFE_OUTDOOR("knife_outdoor"),
    KNIFE_PUSH("knife_push"),
    KNIFE_SKELETON("knife_skeleton"),
    KNIFE_STILETTO("knife_stiletto"),
    KNIFE_SURVIVAL_BOWIE("knife_survival_bowie"),
    KNIFE_T("knife_t"),
    KNIFE_TACTICAL("knife_tactical"),
    KNIFE_URSUS("knife_ursus"),
    KNIFE_WIDOWMAKER("knife_widowmaker"),
    M249("m249"),
    M4A1("m4a1"),
    M4A1_SILENCER("m4a1_silencer"),
    M4A1_SILENCER_OFF("m4a1_silencer_off"),
    MAC10("mac10"),
    MAG7("mag7"),
    MOLOTOV("molotov"),
    MP5SD("mp5sd"),
    MP7("mp7"),
    MP9("mp9"),
    NEGEV("negev"),
    NOVA("nova"),
    P250("p250"),
    P90("p90"),
    REVOLVER("revolver"),
    SAWEDOFF("sawedoff"),
    SCAR20("scar20"),
    SG556("sg556"),
    SMOKEGRENADE("smokegrenade"),
    SSG08("ssg08"),
    TASER("taser"),
    TEC9("tec9"),
    UMP45("ump45"),
    USP_SILENCER("usp_silencer"),
    USP_SILENCER_OFF("usp_silencer_off"),
    XM1014("xm1014");

    private final String title;

    Guns(String title) {
        this.title = title;
    }

    @NotNull
    @Override
    public String toString() {
        return "%s{title=%s}".formatted(this.getClass().getSimpleName(), this.title);
    }
}
