// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.settings

import java.util.Locale

const val APP_NAME = "ClaviRom"

const val CLAVIROM_DESC = "Ina claviatura romontscha. Clicca cheu per ir silla pagina dil project."
const val CLAVIROM_GITHUB_URL = "https://github.com/mhuberch/clavirom"

const val CLAVIROM_DISCUSSIONS_TITLE = "Damondas, propostas ni commentaris?"
const val CLAVIROM_DISCUSSIONS_DESC = "Participescha al svilup da ClaviRom"
const val CLAVIROM_DISCUSSIONS_URL = "https://github.com/mhuberch/clavirom/discussions"

val CLAVIROM_WIZARD_DISPLAY_NAMES = mapOf(
    "rm-SR" to "Romontsch Sursilvan",
    "rm-ST" to "Rumàntsch Sutsilvan",
    "rm-SM" to "Rumantsch Surmiran",
    "rm-PU" to "Rumauntsch Puter",
    "rm-VA" to "Rumantsch Vallader",
    "rm" to "Rumantsch Grischun",
    "de-CH" to "Deutsch (Schweiz)",
    "it-CH" to "Italiano (Svizzera)"
)

val CLAVIROM_WIZARD_LANGUAGES_ROMANSH = listOf("rm-SR", "rm-ST", "rm-SM", "rm-PU", "rm-VA")
val CLAVIROM_WIZARD_LANGUAGES_OTHER = listOf("de-CH", "it-CH")

fun Locale.isClaviromPrivileged(): Boolean = CLAVIROM_WIZARD_DISPLAY_NAMES.containsKey(toLanguageTag())

fun Locale.getSpecialDisplayName(default: String): String {
    return CLAVIROM_WIZARD_DISPLAY_NAMES[toLanguageTag()] ?: default
}

/** Returns the list of languages for the wizard, with the first 5 (Romansh dialects) shuffled. */
fun getShuffledWizardLanguages(): List<Pair<String, String>> {
    val base = CLAVIROM_WIZARD_LANGUAGES_ROMANSH.map { it to CLAVIROM_WIZARD_DISPLAY_NAMES[it]!! }.shuffled()
    val others = CLAVIROM_WIZARD_LANGUAGES_OTHER.map { it to CLAVIROM_WIZARD_DISPLAY_NAMES[it]!! }
    return base + others
}

data class WizardStrings(
    val welcomeTitle: String,
    val welcomeSubtitle: String,
    val additionalDescription: String,
    val startAction: String,
    val stepsTitle: String,
    val step1Title: String,
    val step1Instruction: String,
    val step1Action: String,
    val step2Title: String,
    val step2Instruction: String,
    val step2Action: String,
    val step3Title: String,
    val step3Instruction: String,
    val step3Action: String,
    val step4Title: String,
    val step4Tip: String,
    val step4Thanks: String,
    val step4Text1: String,
    val step4Text2: String,
    val step4Continue: String,
    val step5Title: String,
    val step5Instruction: String,
    val step5Action: String,
    val finishAction: String
)

private fun translations(de: String, sr: String, st: String, sm: String, pu: String, va: String, it: String) = mapOf(
    "de-CH" to de, "rm-SR" to sr, "rm-ST" to st, "rm-SM" to sm, "rm-PU" to pu, "rm-VA" to va, "it-CH" to it
)

val welcomeTitles = translations(
    de = "Willkommen bei $APP_NAME",
    sr = "Beinvegni tier $APP_NAME",
    st = "Bagnvigni tier $APP_NAME",
    sm = "Bagnvigni tar $APP_NAME",
    pu = "Bagnvignieu tar $APP_NAME",
    va = "Bagnvignü tar $APP_NAME",
    it = "Benvenuto in $APP_NAME"
)

val welcomeSubtitles = translations(
    de = "Die App HeliBoard für Graubünden",
    sr = "L'app HeliBoard per la Romontschia",
    st = "HeliBoard adattà par igl Grischun",
    sm = "HeliBoard adattà per igl Grischun",
    pu = "HeliBoard adattà per il Grischun",
    va = "HeliBoard adattà per il Grischun",
    it = "HeliBoard adattato per il Grigioni"
)

val additionalDescriptions = translations(
    de = "Eine Tastatur für Bündner Sprachen",
    sr = "Ina tastatura pils lungatgs Grischuns",
    st = "cun scriver cul det",
    sm = "cun scriver cul det",
    pu = "cun scriver cul daint",
    va = "cun scriver cul daint",
    it = "Una tastiera per le lingue grigionesi"
)

val startActions = translations(
    de = "Starten wir...",
    sr = "Adatg, pinau, los...",
    st = "Lagn nus antscheiver...",
    sm = "Lagn antscheiver...",
    pu = "Lains accumensar...",
    va = "Lains cumanzar...",
    it = "Inizia..."
)

val stepsTitles = translations(
    de = "$APP_NAME einrichten",
    sr = "Drizzar en $APP_NAME",
    st = "Installar $APP_NAME",
    sm = "Installar $APP_NAME",
    pu = "Installar $APP_NAME",
    va = "Installar $APP_NAME",
    it = "Configura $APP_NAME"
)

val step1Titles = translations(
    de = "$APP_NAME aktivieren",
    sr = "Activar $APP_NAME",
    st = "Activar $APP_NAME",
    sm = "Activar $APP_NAME",
    pu = "Activar $APP_NAME",
    va = "Activar $APP_NAME",
    it = "Attiva $APP_NAME"
)

val step1Instructions = translations(
    de = "Bitte aktiviere \"$APP_NAME\" in deinen Sprachen- & Eingabeeinstellungen, um die Nutzung auf Deinem Gerät zu erlauben.",
    sr = "Activescha \"$APP_NAME\" ella configuraziun dils lungatgs per permetter l'utilisaziun sin Tiu telefonin.",
    st = "Activescha par plischer \"$APP_NAME\" aint igls tes ragluments da lungatg e d'introducziun. Quegl permetta ad el d'esser activs sen igl tes apparat.",
    sm = "Activai per plaschair \"$APP_NAME\" ainten vossas preferenzas da lungatg e d'introducziun. Chegl permetta ad el d'esser activs sen igl voss apparat.",
    pu = "Activà per plaschair \"$APP_NAME\" aint in vossas preferenzas da lingua e d'introducziun. Quai permetta ad el d'esser activ sün voss apparat.",
    va = "Activai per plaschair \"$APP_NAME\" aint in vossas preferenzas da lingua e d'introducziun. Quai permetta ad el d'esser activ sün voss apparat.",
    it = "Scegli \"$APP_NAME\" nelle impostazioni 'Lingua e immissione' per autorizzare l'app."
)

val step1Actions = translations(
    de = "In den Einstellungen aktivieren",
    sr = "Activar ella configuraziun",
    st = "Activar aint igls ragluments",
    sm = "Activar ainten las preferenzas",
    pu = "Activar aint in las preferenzas",
    va = "Activar aint in las preferenzas",
    it = "Attiva nelle impostazioni"
)

val step2Titles = translations(
    de = "Auf $APP_NAME wechseln",
    sr = "Midar sin $APP_NAME",
    st = "Midar sen $APP_NAME",
    sm = "Midar sen $APP_NAME",
    pu = "Mudar sün $APP_NAME",
    va = "Mudar sün $APP_NAME",
    it = "Passa a $APP_NAME"
)

val step2Instructions = translations(
    de = "Wähle als nächste \"$APP_NAME\" als deine aktive Eingabemethode aus.",
    sr = "Selecziunescha \"$APP_NAME\" sco opziun per scriver.",
    st = "Suenter seligias Te \"$APP_NAME\" sco metoda d'introducziun activa.",
    sm = "Suenter seligias Te \"$APP_NAME\" sco metoda d'introducziun activa.",
    pu = "Zieva schelgliais Vus \"$APP_NAME\" sco metoda d'introducziun activa.",
    va = "Davo selecziunais Vus \"$APP_NAME\" sco metoda d'introducziun activa.",
    it = "Attiva \"$APP_NAME\" come metodo di immissione di testo."
)

val step2Actions = translations(
    de = "Eingabemethoden wechseln",
    sr = "Midar opziun per scriver",
    st = "Midar las metodas d'introducziun",
    sm = "Midar las metodas d'introducziun",
    pu = "Mudar las metodas d'introducziun",
    va = "Mudar las metodas d'introducziun",
    it = "Cambia metodo di immissione"
)

val step3Titles = translations(
    de = "Hauptsprachen für $APP_NAME wählen",
    sr = "Lungatgs principals per $APP_NAME",
    st = "Selaier or ils lungatgs principals par $APP_NAME",
    sm = "Seligir ils lungatgs principals per $APP_NAME",
    pu = "Schelglier las linguas principalas per $APP_NAME",
    va = "Selecziunar las linguas principalas per $APP_NAME",
    it = "Scegli le lingue principali per $APP_NAME"
)

val step3Instructions = translations(
    de = "Aktiviere deine Hauptsprachen für die Tastatur (kann später verändert werden).",
    sr = "Activescha Tes lungatgs principals per la tastatura (sa vegni midaus pli tard).",
    st = "Activescha tes lungatg principal par la tastatira. La configuraziun sa vignir midadada regularmeing.",
    sm = "Activai voss lungatg principal per la tastatira. La configuraziun po vignir midadada regularmeing.",
    pu = "Activà vossa lingua principala per la tastatura. La configuraziun po gnir mudada regularmaing.",
    va = "Activai vossa lingua principala per la tastatura. La configuraziun po gnir mudada regularmaing.",
    it = "Attiva le lingue principali della tastiera (puoi modificarle in seguito)."
)

val step3Actions = translations(
    de = "Meine Sprachen aktivieren",
    sr = "Activar mes lungatgs",
    st = "Activar lungatgs",
    sm = "Activar lungatgs",
    pu = "Activar linguas",
    va = "Activar linguas",
    it = "Attiva le mie lingue"
)

val step4Titles = translations(
    de = "Infos zu $APP_NAME",
    sr = "Dapli sur da $APP_NAME",
    st = "Project open source",
    sm = "Project open source",
    pu = "Project open source",
    va = "Project open source",
    it = "Progetto open source"
)

val step4Tips = translations(
    de = "Tipp: ",
    sr = "Cussegl: ",
    st = "Cussegl: ",
    sm = "Cussegl: ",
    pu = "Cussegl: ",
    va = "Cussegl: ",
    it = "Consiglio: "
)

val step4Thanks = translations(
    de = "Danke: ",
    sr = "Engraziament: ",
    st = "Grazia fitg: ",
    sm = "Grazia fitg: ",
    pu = "Grazcha fich: ",
    va = "Grazcha fich: ",
    it = "Grazie: "
)

val step4Texts1 = translations(
    de = "Während des Eintippens kannst Du mittels der Sprachwahltaste (s. Bild) zwischen den Sprachen wechseln. So erhältst Du stets Wort- und Korrekturvorschläge in der gewünschten Sprache.",
    sr = "Quei project ei disponibels sin GitHub.",
    st = "Quegl project es disponibel sen GitHub.",
    sm = "Chegl project es disponibel sen GitHub.",
    pu = "Quai project es disponibel s\u00fcn GitHub.",
    va = "Quai project es disponibel s\u00fcn GitHub.",
    it = "Questo progetto \u00e8 disponibile su GitHub."
)

val step4Texts2 = translations(
    de = "Ein riesiger Dank an alle freiwilligen Helfer des Projekts HeliBoard - der freien Tastatur, die Deine Privatsphäre vollständig garantiert (für ClaviRom wurde weniger als 1% der Codes verändert...). Open-Source Projekte ermöglichen es kleinen Communities den digitalen Wandel selbst bestimmt zu gestalten. Vielen Dank der Lia Rumantscha und far.ch für die Sprachdaten, die Hilfe und vor allem für das Interesse.",
    sr = "Tuts ein beinvegnis d' gidar!",
    st = "Mincha agid es bagnvigni!",
    sm = "Mincha agid es bagnvigni!",
    pu = "Mincha agiut es bagnvignieu!",
    va = "Mincha agid es bagnvign\u00fc!",
    it = "Ogni aiuto \u00e8 il benvenuto!"
)

val step4Continues = translations(
    de = "Weiter",
    sr = "Vinavon",
    st = "Inavant",
    sm = "Inavant",
    pu = "Inavant",
    va = "Inavant",
    it = "Avanti"
)

val step5Titles = translations(
    de = "Glückwunsch, du bist fertig!",
    sr = "Gratulaziun, tut ei pinaus!",
    st = "Gratulaziun, Te es parats!",
    sm = "Gratulaziun, Vus esas parats!",
    pu = "Gratulaziun, Vus essas paros!",
    va = "Gratulaziun, Vus essas parats!",
    it = "Congratulazioni, hai finito!"
)

val step5Instructions = translations(
    de = "Jetzt kannst du in allen Apps mit $APP_NAME tippen. Tippe auf die Sprachwahltaste um zwischen den aktivierten Sprache hin- und her zu wechseln.",
    sr = "Ussa sas Ti scriver en tuts apps cun $APP_NAME.",
    st = "Ussa sas Te scriver aint par tut las teas applicaziuns preferidas cun $APP_NAME.",
    sm = "Ussa sas Te scriver ainten tut las teas applicaziuns preferidas cun $APP_NAME.",
    pu = "Uossa pudais Vus scriver aint in tuot vossas applicaziuns preferidas cun $APP_NAME.",
    va = "Uossa pudais Vus scriver aint in tuot vossas applicaziuns preferidas cun $APP_NAME.",
    it = "Puoi usare $APP_NAME per digitare in qualsiasi app."
)

val step5Actions = translations(
    de = "Mehr Details konfigurieren?",
    sr = "Configurar dapli detagls?",
    st = "Configurar la tastatira",
    sm = "Configurar la tastatira",
    pu = "Configurer la tastatura",
    va = "Configurar la tastatura",
    it = "Configurare più dettagli"
)

val finishActions = translations(
    de = "Oder nun einfach benutzen?",
    sr = "Ni semplamein duvrar ussa?",
    st = "Finit",
    sm = "Finit",
    pu = "Finit",
    va = "Finit",
    it = "Tutto pronto!"
)

val WIZARD_TRANSLATIONS: Map<String, WizardStrings> = listOf("de-CH", "rm-SR", "rm-ST", "rm-SM", "rm-PU", "rm-VA", "it-CH").associateWith { locale ->
    WizardStrings(
        welcomeTitle = welcomeTitles[locale]!!,
        welcomeSubtitle = welcomeSubtitles[locale]!!,
        additionalDescription = additionalDescriptions[locale]!!,
        startAction = startActions[locale]!!,
        stepsTitle = stepsTitles[locale]!!,
        step1Title = step1Titles[locale]!!,
        step1Instruction = step1Instructions[locale]!!,
        step1Action = step1Actions[locale]!!,
        step2Title = step2Titles[locale]!!,
        step2Instruction = step2Instructions[locale]!!,
        step2Action = step2Actions[locale]!!,
        step3Title = step3Titles[locale]!!,
        step3Instruction = step3Instructions[locale]!!,
        step3Action = step3Actions[locale]!!,
        step4Title = step4Titles[locale]!!,
        step4Tip = step4Tips[locale]!!,
        step4Thanks = step4Thanks[locale]!!,
        step4Text1 = step4Texts1[locale]!!,
        step4Text2 = step4Texts2[locale]!!,
        step4Continue = step4Continues[locale]!!,
        step5Title = step5Titles[locale]!!,
        step5Instruction = step5Instructions[locale]!!,
        step5Action = step5Actions[locale]!!,
        finishAction = finishActions[locale]!!
    )
}
