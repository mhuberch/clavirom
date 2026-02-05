// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.settings

import java.util.Locale

const val APP_NAME = "ClaviRom"

const val CLAVIROM_DESC = "Ina claviatura romontscha. Clickar per ir sin github."
const val CLAVIROM_GITHUB_URL = "https://github.com/mhuberch/clavirom"

const val CLAVIROM_DISCUSSIONS_TITLE = "Damondas, propostas ni commentaris?"
const val CLAVIROM_DISCUSSIONS_DESC = "Seregistrescha e participescha al svilup da ClaviRom"
const val CLAVIROM_DISCUSSIONS_URL = "https://github.com/mhuberch/clavirom/discussions"

val CLAVIROM_WIZARD_DISPLAY_NAMES = mapOf(
    "rm-SR" to "Romontsch Sursilvan",
    "rm-ST" to "Rumàntsch Sutsilvan",
    "rm-SM" to "Rumantsch Surmiran",
    "rm-PU" to "Rumauntsch Puter",
    "rm-VA" to "Rumantsch Vallader",
    "de-CH" to "Deutsch (Schweiz)",
    "it-CH" to "Italiano (Svizzera)"
)

fun Locale.isClaviromPrivileged(): Boolean = CLAVIROM_WIZARD_DISPLAY_NAMES.containsKey(toLanguageTag())

fun Locale.getSpecialDisplayName(default: String): String {
    return CLAVIROM_WIZARD_DISPLAY_NAMES[toLanguageTag()] ?: default
}

/** Returns the list of languages for the wizard, with the first 5 (Romansh dialects) shuffled. */
fun getShuffledWizardLanguages(): List<Pair<String, String>> {
    val romanshDialects = listOf("rm-SR", "rm-ST", "rm-SM", "rm-PU", "rm-VA")
    val swissOthers = listOf("de-CH", "it-CH")

    val base = romanshDialects.map { it to CLAVIROM_WIZARD_DISPLAY_NAMES[it]!! }.shuffled()
    val others = swissOthers.map { it to CLAVIROM_WIZARD_DISPLAY_NAMES[it]!! }
    return base + others
}

data class WizardStrings(
    val welcomeTitle: String,
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
    val step4Instruction: String,
    val step4Action: String,
    val finishAction: String
)

val WIZARD_TRANSLATIONS = mapOf(
    "rm-SR" to WizardStrings(
        welcomeTitle = "Beinvegni tier $APP_NAME",
        additionalDescription = "cun scriver cul det",
        startAction = "Lein nus entscheiver...",
        stepsTitle = "Installar $APP_NAME",
        step1Title = "Activar $APP_NAME",
        step1Instruction = "Activescha plevon \"$APP_NAME\" en tias preferenzas da lungatg e d'introducziun. Quei dretg permetta ad el d'exequir sin tiu apparat.",
        step1Action = "Activar en las preferenzas",
        step2Title = "Midar sin $APP_NAME",
        step2Instruction = "Suenter seligias Ti \"$APP_NAME\" sco metoda d'introducziun activa.",
        step2Action = "Midar las metodas d'introducziun",
        step3Title = "Seligir ils lungatgs principals per $APP_NAME",
        step3Instruction = "Activescha Tiu lungatg principal per la tastatura. La configuraziun sa vegnir midadada regularmein.",
        step3Action = "Activar lungatgs",
        step4Title = "Gratulaziun, Ti eis parats!",
        step4Instruction = "Ussa sas Ti scriver en tut tias applicaziuns preferidas cun $APP_NAME.",
        step4Action = "Configurar la tastatura",
        finishAction = "Finit"
    ),
    "rm-ST" to WizardStrings(
        welcomeTitle = "Bagnvigni tier $APP_NAME",
        additionalDescription = "cun scriver cul det",
        startAction = "Lagn nus antscheiver...",
        stepsTitle = "Installar $APP_NAME",
        step1Title = "Activar $APP_NAME",
        step1Instruction = "Activescha par plischer \"$APP_NAME\" aint igls tes ragluments da lungatg e d'introducziun. Quegl permetta ad el d'esser activs sen igl tes apparat.",
        step1Action = "Activar aint igls ragluments",
        step2Title = "Midar sen $APP_NAME",
        step2Instruction = "Suenter seligias Te \"$APP_NAME\" sco metoda d'introducziun activa.",
        step2Action = "Midar las metodas d'introducziun",
        step3Title = "Selaier or ils lungatgs principals par $APP_NAME",
        step3Instruction = "Activescha tes lungatg principal par la tastatira. La configuraziun sa vignir midadada regularmeing.",
        step3Action = "Activar lungatgs",
        step4Title = "Gratulaziun, Te es parats!",
        step4Instruction = "Ussa sas Te scriver aint par tut las teas applicaziuns preferidas cun $APP_NAME.",
        step4Action = "Configurar la tastatira",
        finishAction = "Finit"
    ),
    "rm-SM" to WizardStrings(
        welcomeTitle = "Bagnvigni tar $APP_NAME",
        additionalDescription = "cun scriver cul det",
        startAction = "Lagn antscheiver...",
        stepsTitle = "Installar $APP_NAME",
        step1Title = "Activar $APP_NAME",
        step1Instruction = "Activai per plaschair \"$APP_NAME\" ainten vossas preferenzas da lungatg e d'introducziun. Chegl permetta ad el d'esser activs sen igl voss apparat.",
        step1Action = "Activar ainten las preferenzas",
        step2Title = "Midar sen $APP_NAME",
        step2Instruction = "Suenter seligias Te \"$APP_NAME\" sco metoda d'introducziun activa.",
        step2Action = "Midar las metodas d'introducziun",
        step3Title = "Seligir ils lungatgs principals per $APP_NAME",
        step3Instruction = "Activai voss lungatg principal per la tastatira. La configuraziun po vignir midadada regularmeing.",
        step3Action = "Activar lungatgs",
        step4Title = "Gratulaziun, Vus esas parats!",
        step4Instruction = "Ussa sas Te scriver ainten tut las teas applicaziuns preferidas cun $APP_NAME.",
        step4Action = "Configurar la tastatira",
        finishAction = "Finit"
    ),
    "rm-PU" to WizardStrings(
        welcomeTitle = "Bagnvignieu tar $APP_NAME",
        additionalDescription = "cun scriver cul daint",
        startAction = "Lains accumensar...",
        stepsTitle = "Installar $APP_NAME",
        step1Title = "Activar $APP_NAME",
        step1Instruction = "Activè per plaschair \"$APP_NAME\" aint in vossas preferenzas da lingua e d'introducziun. Quai permetta ad el d'esser activ sün voss apparat.",
        step1Action = "Activar aint in las preferenzas",
        step2Title = "Mudar sün $APP_NAME",
        step2Instruction = "Zieva schelgliais Vus \"$APP_NAME\" sco metoda d'introducziun activa.",
        step2Action = "Mudar las metodas d'introducziun",
        step3Title = "Schelglier las linguas principalas per $APP_NAME",
        step3Instruction = "Activè vossa lingua principala per la tastatura. La configuraziun po gnir mudada regularmaing.",
        step3Action = "Activar linguas",
        step4Title = "Gratulaziun, Vus essas paros!",
        step4Instruction = "Uossa pudais Vus scriver aint in tuot vossas applicaziuns preferidas cun $APP_NAME.",
        step4Action = "Configurer la tastatura",
        finishAction = "Finit"
    ),
    "rm-VA" to WizardStrings(
        welcomeTitle = "Bagnvignü tar $APP_NAME",
        additionalDescription = "cun scriver cul daint",
        startAction = "Lains cumanzar...",
        stepsTitle = "Installar $APP_NAME",
        step1Title = "Activar $APP_NAME",
        step1Instruction = "Activai per plaschair \"$APP_NAME\" aint in vossas preferenzas da lingua e d'introducziun. Quai permetta ad el d'esser activ sün voss apparat.",
        step1Action = "Activar aint in las preferenzas",
        step2Title = "Mudar sün $APP_NAME",
        step2Instruction = "Davo selecziunais Vus \"$APP_NAME\" sco metoda d'introducziun activa.",
        step2Action = "Mudar las metodas d'introducziun",
        step3Title = "Selecziunar las linguas principalas per $APP_NAME",
        step3Instruction = "Activai vossa lingua principala per la tastatura. La configuraziun po gnir mudada regularmaing.",
        step3Action = "Activar linguas",
        step4Title = "Gratulaziun, Vus essas parats!",
        step4Instruction = "Uossa pudais Vus scriver aint in tuot vossas applicaziuns preferidas cun $APP_NAME.",
        step4Action = "Configurar la tastatura",
        finishAction = "Finit"
    ),
    "de-CH" to WizardStrings(
        welcomeTitle = "Willkommen bei $APP_NAME",
        additionalDescription = "mit Gesten-Tippen",
        startAction = "Lass uns anfangen...",
        stepsTitle = "$APP_NAME einrichten",
        step1Title = "$APP_NAME aktivieren",
        step1Instruction = "Bitte aktiviere \"$APP_NAME\" in deinen Sprachen- & Eingabeeinstellungen. Dies erlaubt die Ausführung auf deinem Gerät.",
        step1Action = "In den Einstellungen aktivieren",
        step2Title = "Zu $APP_NAME wechseln",
        step2Instruction = "Wähle als Nächstes \"$APP_NAME\" als deine aktive Eingabemethode aus.",
        step2Action = "Eingabemethoden wechseln",
        step3Title = "Hauptsprachen für $APP_NAME wählen",
        step3Instruction = "Aktiviere deine Hauptsprachen für die Tastatur. Die Konfiguration kann jederzeit geändert werden.",
        step3Action = "Sprachen aktivieren",
        step4Title = "Glückwunsch, du bist fertig!",
        step4Instruction = "Jetzt kannst du in all deinen Lieblings-Apps mit $APP_NAME tippen.",
        step4Action = "Konfiguriere die Tastatur",
        finishAction = "Fertig"
    ),
    "it-CH" to WizardStrings(
        welcomeTitle = "Benvenuto in $APP_NAME",
        additionalDescription = "con digitazione gestuale",
        startAction = "Inizia...",
        stepsTitle = "Configura $APP_NAME",
        step1Title = "Attiva $APP_NAME",
        step1Instruction = "Scegli \"$APP_NAME\" nelle impostazioni 'Lingua e immissione' per autorizzare l'app sul tuo dispositivo.",
        step1Action = "Attiva nelle impostazioni",
        step2Title = "Passa a $APP_NAME",
        step2Instruction = "Infine, seleziona \"$APP_NAME\" come metodo di immissione di testo attivo.",
        step2Action = "Cambia metodo di immissione",
        step3Title = "Scegli le lingue principali per $APP_NAME",
        step3Instruction = "Attiva la tua lingua principale per la tastiera. La configurazione può essere modificata regolarmente.",
        step3Action = "Attiva lingue",
        step4Title = "Congratulazioni, hai finito!",
        step4Instruction = "Ora puoi usare $APP_NAME per scrivere in tutte le tue app preferite.",
        step4Action = "Configura la tastiera",
        finishAction = "Fine"
    )
)
