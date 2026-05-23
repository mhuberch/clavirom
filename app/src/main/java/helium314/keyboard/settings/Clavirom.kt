// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import helium314.keyboard.latin.utils.SubtypeSettings
import helium314.keyboard.latin.utils.locale
import helium314.keyboard.latin.utils.prefs
import java.util.Locale

const val APP_NAME = "ClaviRom"

const val CLAVIROM_DESC = "Ina claviatura romontscha. Clicca cheu per ir silla pagina dil project."
const val CLAVIROM_GITHUB_URL = "https://github.com/mhuberch/clavirom"

const val CLAVIROM_DISCUSSIONS_URL = "https://github.com/mhuberch/clavirom/discussions"

const val HELIBOARD_WIKI_URL = "https://github.com/Helium314/HeliBoard/wiki"

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

val CLAVIROM_BOLD_WORDS = listOf("HeliBoard", "Lia Rumantscha", "far.ch")

val CLAVIROM_WIZARD_LANGUAGES_ROMANSH = listOf("rm-SR", "rm-ST", "rm-SM", "rm-PU", "rm-VA")
val CLAVIROM_WIZARD_LANGUAGES_OTHER = listOf("de-CH", "it-CH")

fun Locale.isClaviromPrivileged(): Boolean = CLAVIROM_WIZARD_DISPLAY_NAMES.containsKey(toLanguageTag())

fun Locale.getSpecialDisplayName(default: String): String {
    return CLAVIROM_WIZARD_DISPLAY_NAMES[toLanguageTag()] ?: default
}

/**
 * Checks whether the [candidate] locale is a valid dictionary match for the [requested] locale
 * according to ClaviRom's exact-match rules for Rumantsch.
 */
fun isDictionaryMatch(requested: Locale, candidate: Locale): Boolean {
    if (requested.language == "rm") {
        return requested == candidate
    }
    return true // Default behavior for other languages
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
    val step5SupportTitle: String,
    val step5SupportItem1: String,
    val step5SupportItem2: String,
    val step5SupportItem3: String,
    val step5Action: String,
    val finishAction: String,
    val discussionsTitle: String,
    val discussionsDesc: String
)

private fun translations(de: String, sr: String, st: String, sm: String, pu: String, va: String, it: String) = mapOf(
    "de-CH" to de, "rm-SR" to sr, "rm-ST" to st, "rm-SM" to sm, "rm-PU" to pu, "rm-VA" to va, "it-CH" to it
)

val welcomeTitles = translations(
    de = "Willkommen bei $APP_NAME",
    sr = "Beinvegni tier $APP_NAME",
    st = "Bagnvagnieu  tier $APP_NAME",
    sm = "Bavegna tar $APP_NAME",
    pu = "Bagnvignieu tar $APP_NAME",
    va = "Bagnvignü tar $APP_NAME",
    it = "Benvenuto in $APP_NAME"
)

val welcomeSubtitles = translations(
    de = "Die App HeliBoard für Rätoromanisch",
    sr = "L'app HeliBoard per il romontsch",
    st = "L'app HeliBoard per il rumàntsch",
    sm = "L'app HeliBoard per il rumantsch",
    pu = "L'app HeliBoard per il rumauntsch",
    va = "L'app HeliBoard per il rumantsch",
    it = "HeliBoard adattato per il Grigioni"
)

val additionalDescriptions = translations(
    de = "Eine Tastatur für Bündner Sprachen",
    sr = "Ina tastatura pils lungatgs Grischuns",
    st = "Egna tastatura par lungatgs grischùns",
    sm = "Ena tastatura per lungatgs grischuns",
    pu = "Üna tastatura per linguas grischunas",
    va = "Üna tastatura per linguas grischunas",
    it = "Una tastiera per le lingue grigionesi"
)

val startActions = translations(
    de = "Starten wir...",
    sr = "Lein entscheiver...",
    st = "Lagn antscheiver...",
    sm = "Lainsa antschever...",
    pu = "Laschains cumanzer...",
    va = "Lain cumanzar...",
    it = "Inizia..."
)

val stepsTitles = translations(
    de = "$APP_NAME einrichten",
    sr = "Drizzar en $APP_NAME",
    st = "Drizar aint $APP_NAME",
    sm = "Andrizzar $APP_NAME",
    pu = "Drizzer aint $APP_NAME",
    va = "Drizzar aint $APP_NAME",
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
    sr = "Activescha \"$APP_NAME\" ella configuraziun dils lungatgs per lubir l'utilisaziun sin tiu telefonin.",
    st = "Activescha igl \"$APP_NAME\" an la configuraziùn da lungatgs par lubir igl diever segl tieus telefonet",
    sm = "Activescha \"$APP_NAME\" ainten la configuraziun digls lungatgs per lubeir l'utilisaziun sen igl ties telefonign.",
    pu = "Activescha \"$APP_NAME\" illas configüraziuns da linguas per permetter l'adöver sün tieu telefonin.",
    va = "Activescha \"$APP_NAME\" illa configüraziun da las linguas per permetter l'adöver sün teis telefonin.",
    it = "Scegli \"$APP_NAME\" nelle impostazioni 'Lingua e immissione' per autorizzare l'app."
)

val step1Actions = translations(
    de = "In den Einstellungen aktivieren",
    sr = "Activar ella configuraziun",
    st = "Activar an la configuraziùn",
    sm = "Activar ainten las preferenzas",
    pu = "Activer illa configüraziun",
    va = "Activar illa configüraziun",
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
    sr = "Tscharna sco proxim \"$APP_NAME\" sco metoda d'endataziun activa.",
    st = " Tschearna sco proxim \"$APP_NAME\" sco metoda d'andataziùn activa.",
    sm = "Tscherna scu proxim \"$APP_NAME\" scu metoda d'endataziun activa.",
    pu = "Tscherna scu prossem \"$APP_NAME\" scu tia metoda d'endataziun activa.",
    va = "Tscherna sco prossem \"$APP_NAME\" sco tia metoda d'endataziun activa.",
    it = "Attiva \"$APP_NAME\" come metodo di immissione di testo."
)

val step2Actions = translations(
    de = "Eingabemethoden wechseln",
    sr = "Midar opziun per scriver",
    st = "Midar las metodas d'endataziun",
    sm = "Midar las metodas d'endataziùn",
    pu = "Müder las metodas d'endataziun",
    va = "Müdar las metodas d'endataziun",
    it = "Cambia metodo di immissione"
)

val step3Titles = translations(
    de = "Hauptsprachen für $APP_NAME wählen",
    sr = "Eleger lungatgs principals per $APP_NAME",
    st = "Tschearner lungatgs prinzipals per $APP_NAME",
    sm = "Tscherner las linguas principalas per $APP_NAME",
    pu = "Tscherner linguas principelas per $APP_NAME",
    va = "Tscherner linguas principalas per $APP_NAME",
    it = "Scegli le lingue principali per $APP_NAME"
)

val step3Instructions = translations(
    de = "Aktiviere deine Hauptsprachen für die Tastatur (kann später verändert werden).",
    sr = "Activescha tes lungatgs principals per la tastatura (sa vegni midau pli tard).",
    st = "Activescha igls teas lungatgs prinzipals par la tastatura (san vagnir midos ple tard).",
    sm = "Activescha igls ties lungatgs principals per la tastatura (pon neir midos pi tard).",
    pu = "Activescha tias linguas principelas per la tastatura (po gnir müda pü tard).",
    va = "Activescha tias linguas principalas per la tastatura (po gnir müdà plü tard).",
    it = "Attiva le lingue principali della tastiera (puoi modificarle in seguito)."
)

val step3Actions = translations(
    de = "Meine Sprachen aktivieren",
    sr = "Activar mes lungatgs",
    st = "Activar mieus lungatgs",
    sm = "Activar mes lungatgs",
    pu = "Activer mias linguas",
    va = "Activar mias linguas",
    it = "Attiva le mie lingue"
)

val step4Titles = translations(
    de = "À propos...",
    sr = "Bien da saver...",
    st = "Bien da saver...",
    sm = "Bung da saveir...",
    pu = "Bun da savair...",
    va = "Bun da savair...",
    it = "Progetto open source"
)

val step4Tips = translations(
    de = "Tipp: ",
    sr = "Cussegl: ",
    st = "Cunzegl: ",
    sm = "Cunsegl: ",
    pu = "Cussagl: ",
    va = "cussagl: ",
    it = "Consiglio: "
)

val step4Thanks = translations(
    de = "Danke: ",
    sr = "Grazia fetg: ",
    st = "Graztga fetg: ",
    sm = "Graztga fitg: ",
    pu = "Grazcha fich: ",
    va = "Grazcha fich: ",
    it = "Grazie: "
)

val step4Texts1 = translations(
    de = "Während des Eintippens kannst Du mittels der Sprachwahltaste (s. Bild) zwischen den Sprachen wechseln. So erhältst Du stets Wort- und Korrekturvorschläge in der gewünschten Sprache. Möchtest Du mehr als drei Vorschläge, dann drücke lange auf den mittleren Vorschlag.",
    sr = "Duront tippar en sas ti midar denter ils lungatgs cun agid dalla tasta d'elecziun dil lungatg (mirar maletg). Aschia survegns ti adina propostas da plaids e da correctura el lungatg giavischau. Vul ti dapli che treis propostas, lu smacca ditg silla proposta enamiez.",
    st = "Durànt tipar an sas Tei midar trànter igls lungatgs cun agid da la tasta da tschearna da lungatgs (varda maletg). Ascheia surveans tei adigna propostas da pleds a da corectura agl lungatg giavischo. Vol tei daple ca tres propostas, alura schmatga gî sen la proposta aintamiez.",
    sm = "Durant tippar en pos te midar tranter igls lungatgs cun ageid da la tasta d'elecziun da lungatg (vurdar maletg). Uscheia survignst te adegna propostas da pleds e correcturas aint igl lungatg giavischia. Vot daple tgi treis propostas, alloura strocla dei sen la proposta damez.",
    pu = "Düraunt il tippar aint poust Tü müder traunter las linguas cun agüd da la tasta da tscherna da la lingua (guarda purtret). Uschè survainst Tü adüna propostas da pleds e da correcturas illa lingua giavüscheda. Vessast gugent dapü cu trais propostas, schi schmacha lönch sülla proposta immez.",
    va = "Dürant il tippar aint poust tü müdar tanter las linguas cun agüd da la tasta da tscherna da la lingua (guarda purtret). Uschè survainst Tü adüna propostas da pleds e da correctura illa lingua giavüschada. voust daplü co trais propostas lura schmacha lönch sülla proposta immez.",
    it = "Durante la digitazione, puoi passare da una lingua all'altra utilizzando il tasto di selezione della lingua (vedi immagine). In questo modo riceverai sempre suggerimenti di parole e correzioni nella lingua desiderata. Se desideri più di tre suggerimenti, premi a lungo sul suggerimento centrale."
)

val step4Texts2 = translations(
    de = "Ein riesiger Dank an alle freiwilligen Helfer des Projekts HeliBoard - der freien Tastatur, die Deine Privatsphäre vollständig garantiert (für ClaviRom wurde weniger als 1% der Codes verändert...). Open-Source Projekte ermöglichen es kleinen Communities den digitalen Wandel selbst bestimmt zu gestalten. Vielen Dank der Lia Rumantscha und far.ch für die Sprachdaten, die Hilfe und vor allem für das Interesse.",
    sr = "In grond engraziament a tut ils gidonters voluntaris dil project HeliBoard - la tastatura libra che garantescha cumpleinamein tia sfera privata (per ClaviRom eis ei vegniu midau pli pauc che 1% dil code...). Projects open source possibiliteschan a pintgas cuminonzas da concepir sezzas la midada digitala a moda definida. Grazia fetg a la Lia Rumantscha e far.ch per las datas linguisticas, l'agid e surtut per l'interess.",
    st = "Egn grànd angraztgamaint a tut igls gidànters voluntaris digl project HeliBoard - la tastatura libra ca garantescha cumplagnameing la Tia sfera privata (par ClaviRom e vagnieu mido ple poc ca 1% digls codes...). Projects open source pussibiliteschan a pintgas cuminànzas da crear sezs a moda dezidida la midada digitala. Graztga fetg a la Lia Rumantscha a far.ch par las datas linguisticas, igl agid ad oravànttut pigl interess.",
    sm = "En grond angraztg a tot igls gidanters voluntaris digl project HeliBoard - la tastatura libra tgi garantescha cumplettamaintg la tia sfera privata (per ClaviRom è nia mido pi pac tgi 1% digls codes...). Projects open source pussibiliteschan a pitschnas cuminanzas da furmar sezzas la midada digitala a moda defineida. Grazia fitg a la Lia Rumantscha e far.ch per las datas linguisticas, l'agid e surtut per l'interess.",
    pu = "Ün grazcha fich a tuot ils agüdaunts voluntaris dal proget HeliBoard - la tastatura libra chi garantescha cumplettamaing tia sfera privata (per ClaviRom es gnieu müdo damain cu 1% dals codes...). Progets d'open source pussibilteschan a pitschnas cumünaunzas da concepir svess la müdeda digitela da maniera definida. Grazcha fich a la Lia Rumantscha ed a far.ch per las datas linguisticas, l'agüd ed impustüt per l'interess",
    va = "Ün grazcha fich a tuot ils agüdants voluntaris dal proget HeliBoard - la tastatura libra chi garantischa cumplettamaing Tia sfera privata (per ClaviRom es gnü müdà damain co 1% dals codes...). Progets open source pussibilteschan a cumünanzas pitschnas da fuormar svess la müdada digitala in möd decis. Grazcha fich a la Lia Rumantscha ed a far.ch per las datas da lingua, l'agüd ed impustüt per l'interess.",
    it = "Un enorme ringraziamento a tutti i volontari del progetto HeliBoard - la tastiera libera che garantisce completamente la tua privacy (per ClaviRom è stato modificato meno dell'1% del codice...). I progetti open source consentono alle piccole comunità di plasmare la trasformazione digitale in modo autodeterminato. Grazie mille alla Lia Rumantscha e a far.ch per i dati linguistici, l'aiuto e soprattutto per l'interesse."
)

val step4Continues = translations(
    de = "Weiter",
    sr = "Vinavon",
    st = "Anavànt",
    sm = "Anavant",
    pu = "Inavaunt",
    va = "Inavant",
    it = "Avanti"
)

val step5Titles = translations(
    de = "Glückwunsch, du bist fertig!",
    sr = "Gratulaziun, has finiu!",
    st = "Gratulaziùn, âs fito!",
    sm = "Gratulaziun, ast fitto!",
    pu = "Gratulaziun, hest glivro!",
    va = "Gratulaziun, hast fini!",
    it = "Congratulazioni, hai finito!"
)

val step5Instructions = translations(
    de = "Jetzt kannst du in allen Apps mit $APP_NAME tippen.",
    sr = "Ussa sas ti scriver en tut las apps cun $APP_NAME.",
    st = "Ussa sas tei tipar an tut las apps cun $APP_NAME.",
    sm = "Ossa post ti tippar en tut las apps cun $APP_NAME.",
    pu = "Uossa poust tü tipper in tuot las apps cun $APP_NAME.",
    va = "Uossa poust tü tippar in tuot las apps cun $APP_NAME.",
    it = "Puoi usare $APP_NAME per digitare in qualsiasi app."
)

val step5SupportTitles = translations(
    de = "Support findest Du im:",
    sr = "Agid anflas ti cheu:",
    st = "Agid tgatas tei qua.",
    sm = "Ageid cattas te cò:",
    pu = "Agüd chattast tü cò:",
    va = "Agüd chattast tü quia:",
    it = "Supporto:"
)

val step5SupportItems1 = translations(
    de = "Wiki von HeliBoard",
    sr = "Wiki da HeliBoard",
    st = "Wiki da HeliBoard",
    sm = "Wiki da HeliBoard",
    pu = "Wiki da HeliBoard",
    va = "Wiki da HeliBoard",
    it = "Wiki del progetto HeliBoard"
)

val step5SupportItems2 = translations(
    de = "Forum von ClaviRom",
    sr = "Forum da ClaviRom",
    st = "Forum da ClaviRom",
    sm = "Forum da ClaviRom",
    pu = "Forum da ClaviRom",
    va = "Forum da ClaviRom",
    it = "Forum del progetto laviRom"
)

val step5SupportItems3 = translations(
    de = "Diese Links findest Du später auch dort, wo Du die App runtergeladen hast.",
    sr = "Quels links anflas ti pli tard era leu nua che ti has cargau giu l'app.",
    st = "Quels links tgatas Tei ple tard ear là, noua ca Tei âs cargieu giou l'app.",
    sm = "Chels links cattas te pi tard er lò, noua tgi te ast cargea giu l'app.",
    pu = "Quists links chattast tü pü tard eir lo, inua cha Tü hest telechargio l'app.",
    va = "Quels links chattast tü plü tard eir là, ingio cha Tü hast telechargià l'app.",
    it = "Troverai questi link anche dove hai scaricato l'app."
)

val step5Actions = translations(
    de = "Mehr Details konfigurieren?",
    sr = "Drizzar en dapli detagls?",
    st = "Drizar aint daple detagls?",
    sm = "Andrizzar dapli detagls?",
    pu = "Drizzer aint dapü detagls?",
    va = "Drizzar aint dapü detagls?",
    it = "Configurare più dettagli"
)

val finishActions = translations(
    de = "Oder nun einfach benutzen?",
    sr = "Ni duvrar ussa semplamein?",
    st = "Near duvrar ussa semplameing?",
    sm = "U duvrar ossa simplamaintg?",
    pu = "U druver uossa simplamaing?",
    va = "O dovrar uossa simplamaing?",
    it = "Tutto pronto!"
)

val discussionsTitles = translations(
    de = "Fragen, Vorschläge oder Kommentare?",
    sr = "Damondas, propostas ni commentaris?",
    st = "Amparadas, propostas near comentaris?",
    sm = "Dumondas, propostas u commentars?",
    pu = "Dumandas, propostas u commentars?",
    va = "Dumondas, propostas o commentars?",
    it = "Domande, proposte o commenti?"
)

val discussionsDescs = translations(
    de = "Beteilige dich an der Entwicklung von ClaviRom",
    sr = "Separticipescha al svilup da ClaviRom",
    st = "Sapartizipescha agl svilup da ClaviRom",
    sm = "Ta participescha agl svilup da ClaviRom",
    pu = "Participescha't al svilup da ClaviRom",
    va = "At partecipescha al svilup da ClaviRom",
    it = "Partecipa allo sviluppo di ClaviRom"
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
        step5SupportTitle = step5SupportTitles[locale]!!,
        step5SupportItem1 = step5SupportItems1[locale]!!,
        step5SupportItem2 = step5SupportItems2[locale]!!,
        step5SupportItem3 = step5SupportItems3[locale]!!,
        step5Action = step5Actions[locale]!!,
        finishAction = finishActions[locale]!!,
        discussionsTitle = discussionsTitles[locale]!!,
        discussionsDesc = discussionsDescs[locale]!!
    )
}

@Composable
fun getWizardStrings(): WizardStrings {
    val ctx = LocalContext.current
    val locale = SubtypeSettings.getSelectedSubtype(ctx.prefs()).locale()
    val fallbackTag = "rm-SR"
    val tag = if (locale.isClaviromPrivileged()) {
        val fullTag = locale.toLanguageTag()
        WIZARD_TRANSLATIONS.keys.firstOrNull { it == fullTag }
            ?: WIZARD_TRANSLATIONS.keys.firstOrNull { it.startsWith(locale.language) }
            ?: fallbackTag
    } else fallbackTag
    return WIZARD_TRANSLATIONS[tag] ?: WIZARD_TRANSLATIONS[fallbackTag]!!
}

fun boldifySubstrings(
    fullText: String,
    keywords: List<String>,
    boldStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.Bold)
): AnnotatedString {
    return buildAnnotatedString {
        append(fullText)

        keywords.forEach { keyword ->
            if (keyword.isNotEmpty()) {
                var startIndex = fullText.indexOf(keyword, ignoreCase = true)
                while (startIndex >= 0) {
                    addStyle(
                        style = boldStyle,
                        start = startIndex,
                        end = startIndex + keyword.length
                    )
                    // Weitersuchen nach dem nächsten Vorkommen
                    startIndex = fullText.indexOf(keyword, startIndex + keyword.length, ignoreCase = true)
                }
            }
        }
    }
}
