// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.settings

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import helium314.keyboard.latin.R
import helium314.keyboard.latin.utils.JniUtils
import helium314.keyboard.latin.utils.UncachedInputMethodManagerUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val finishAction: String
)

val wizardTranslations = mapOf(
    "rm-sursilvan" to WizardStrings(
        welcomeTitle = "Beinvegni tier ClaviRom",
        additionalDescription = "cun scriver cul det",
        startAction = "Lein nus entscheiver...",
        stepsTitle = "Installar %s",
        step1Title = "Activar %s",
        step1Instruction = "Activescha plevon %s en tias preferenzas da lungatg e d'introducziun. Quei dretg permetta ad el d'exequir sin tiu apparat.",
        step1Action = "Activar en las preferenzas",
        step2Title = "Midar sin %s",
        step2Instruction = "Suenter seligias Ti %s sco metoda d'introducziun activa.",
        step2Action = "Midar las metodas d'introducziun",
        step3Title = "Seligir ils lungatgs principals per %s",
        step3Instruction = "Activescha Tiu lungatg principal per la tastatura. La configuraziun sa vegnir midadada regularmein.",
        step3Action = "Activar lungatgs",
        step4Title = "Gratulaziun, Ti eis parats!",
        step4Instruction = "Ussa sas Ti scriver en tut tias applicaziuns preferidas cun %s.",
        finishAction = "Finit"
    ),
    "rm-sutsilvan" to WizardStrings(
        welcomeTitle = "Bagnvigni tier ClaviRom",
        additionalDescription = "cun scriver cul det",
        startAction = "Lagn nus antscheiver...",
        stepsTitle = "Installar %s",
        step1Title = "Activar %s",
        step1Instruction = "Activescha par plischer %s aint igls tes ragluments da lungatg e d'introducziun. Quegl permetta ad el d'esser activs sen igl tes apparat.",
        step1Action = "Activar aint igls ragluments",
        step2Title = "Midar sen %s",
        step2Instruction = "Suenter seligias Te %s sco metoda d'introducziun activa.",
        step2Action = "Midar las metodas d'introducziun",
        step3Title = "Selaier or ils lungatgs principals par %s",
        step3Instruction = "Activescha tes lungatg principal par la tastatira. La configuraziun sa vignir midadada regularmeing.",
        step3Action = "Activar lungatgs",
        step4Title = "Gratulaziun, Te es parats!",
        step4Instruction = "Ussa sas Te scriver aint par tut las teas applicaziuns preferidas cun %s.",
        finishAction = "Finit"
    ),
    "de-CH" to WizardStrings(
        welcomeTitle = "Willkommen bei ClaviRom",
        additionalDescription = "mit Gesten-Tippen",
        startAction = "Lass uns anfangen...",
        stepsTitle = "%s einrichten",
        step1Title = "%s aktivieren",
        step1Instruction = "Bitte aktiviere %s in deinen Sprachen- & Eingabeeinstellungen. Dies erlaubt die Ausführung auf deinem Gerät.",
        step1Action = "In den Einstellungen aktivieren",
        step2Title = "Zu %s wechseln",
        step2Instruction = "Wähle als Nächstes %s als deine aktive Eingabemethode aus.",
        step2Action = "Eingabemethoden wechseln",
        step3Title = "Hauptsprachen für %s wählen",
        step3Instruction = "Aktiviere deine Hauptsprachen für die Tastatur. Die Konfiguration kann jederzeit geändert werden.",
        step3Action = "Sprachen aktivieren",
        step4Title = "Glückwunsch, du bist fertig!",
        step4Instruction = "Jetzt kannst du in all deinen Lieblings-Apps mit %s tippen.",
        finishAction = "Fertig"
    )
)

@Composable
fun WelcomeWizard(
    onLanguageClick: () -> Unit,
    finish: () -> Unit
) {
    val ctx = LocalContext.current
    val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    fun determineStep(): Int = when {
        !UncachedInputMethodManagerUtils.isThisImeEnabled(ctx, imm) -> 0
        !UncachedInputMethodManagerUtils.isThisImeCurrent(ctx, imm) -> 2
        else -> 3
    }
    var step by rememberSaveable { mutableIntStateOf(determineStep()) }
    var selectedLanguage by rememberSaveable { mutableStateOf("rm-sursilvan") }
    val strings = wizardTranslations[selectedLanguage] ?: wizardTranslations["rm-sursilvan"]!!

    val scope = rememberCoroutineScope()
    LaunchedEffect(step) {
        if (step == 2)
            scope.launch {
                while (step == 2 && !UncachedInputMethodManagerUtils.isThisImeCurrent(ctx, imm)) {
                    delay(50)
                }
                step = 3
            }
    }
    val useWideLayout = LocalConfiguration.current.screenWidthDp > 600
    val stepBackgroundColor = Color(ContextCompat.getColor(ctx, R.color.setup_step_background))
    val textColor = Color(ContextCompat.getColor(ctx, R.color.setup_text_action))
    val textColorDim = textColor.copy(alpha = 0.5f)
    val titleColor = Color(ContextCompat.getColor(ctx, R.color.setup_text_title))
    val appName = stringResource(ctx.applicationInfo.labelRes)

    @Composable fun bigText() {
        val title = if (step == 0) strings.welcomeTitle else strings.stepsTitle.format(appName)
        Column(Modifier.padding(bottom = 36.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                color = titleColor,
            )
            if (JniUtils.sHaveGestureLib)
                Text(
                    strings.additionalDescription,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.End,
                    color = titleColor,
                    modifier = Modifier.fillMaxWidth()
                )
        }
    }
    @Composable
    fun ColumnScope.Step(step: Int, title: String, instruction: String, actionText: String, icon: Painter, action: () -> Unit) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("1", color = if (step == 1) titleColor else textColorDim)
            Text("2", color = if (step == 2) titleColor else textColorDim)
            Text("3", color = if (step == 3) titleColor else textColorDim)
            Text("4", color = if (step == 4) titleColor else textColorDim)
        }
        Column(Modifier
            .background(color = stepBackgroundColor)
            .padding(16.dp)
        ) {
            Text(title)
            Text(instruction, style = MaterialTheme.typography.bodyLarge.merge(color = textColor))
        }
        Spacer(Modifier.height(4.dp))
        Row(
            Modifier.clickable { action() }
                .background(color = stepBackgroundColor)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, Modifier.padding(end = 6.dp).size(32.dp), tint = textColor)
            Text(actionText, Modifier.weight(1f))
        }
    }
    @Composable fun steps() {
        if (step == 0)
            Step0(
                selectedLanguage = selectedLanguage,
                onLanguageSelected = { selectedLanguage = it },
                startText = strings.startAction,
                onClick = { step = 1 }
            )
        else
            Column {
                val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    step = determineStep()
                }
                if (step == 1) {
                    Step(
                        step,
                        strings.step1Title.format(appName),
                        strings.step1Instruction.format(appName),
                        strings.step1Action,
                        painterResource(R.drawable.ic_setup_key)
                    ) {
                        val intent = Intent()
                        intent.action = Settings.ACTION_INPUT_METHOD_SETTINGS
                        intent.addCategory(Intent.CATEGORY_DEFAULT)
                        launcher.launch(intent)
                    }
                } else if (step == 2) {
                    Step(
                        step,
                        strings.step2Title.format(appName),
                        strings.step2Instruction.format(appName),
                        strings.step2Action,
                        painterResource(R.drawable.ic_setup_select),
                        imm::showInputMethodPicker
                    )
                } else if (step == 3) {
                    Step(
                        step,
                        strings.step3Title.format(appName),
                        strings.step3Instruction,
                        strings.step3Action,
                        painterResource(R.drawable.sym_keyboard_language_switch)
                    ) {
                        onLanguageClick()
                        step = 4
                    }
                } else { // step 4
                    Step(
                        step,
                        strings.step4Title,
                        strings.step4Instruction.format(appName),
                        strings.finishAction,
                        painterResource(R.drawable.ic_setup_check),
                        finish
                    )
                }
            }
    }
    Surface {
        CompositionLocalProvider(
            LocalContentColor provides textColor,
            LocalTextStyle provides MaterialTheme.typography.titleLarge.merge(color = textColor),
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                if (useWideLayout)
                    Row {
                        Box(Modifier.weight(0.4f)) {
                            bigText()
                        }
                        Box(Modifier.weight(0.6f)) {
                            steps()
                        }
                    }
                else
                    Column {
                        bigText()
                        steps()
                    }
            }
        }
    }
}

@Composable
fun Step0(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    startText: String,
    onClick: () -> Unit
) {
    val languages = listOf(
        "rm-sursilvan" to "Romontsch Sursilvan",
        "rm-sutsilvan" to "Rumàntsch Sutsilvan",
        "de-CH" to "Deutsch (Schweiz)"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painterResource(R.drawable.setup_welcome_image), null, modifier = Modifier.size(120.dp))

        Spacer(Modifier.height(16.dp))

        Column(Modifier.selectableGroup()) {
            languages.forEach { (code, label) ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .selectable(
                            selected = (selectedLanguage == code),
                            onClick = { onLanguageSelected(code) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedLanguage == code),
                        onClick = null // null because the row handles the click
                    )
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(Modifier.clickable { onClick() }
            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                startText,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Theme(previewDark) {
        Surface {
            WelcomeWizard({}) {  }
        }
    }
}

@Preview(
    // content cut off on real device, but not here... great?
    device = "spec:orientation=landscape,width=400dp,height=780dp"
)
@Composable
private fun WidePreview() {
    Theme(previewDark) {
        Surface {
            WelcomeWizard({}) {  }
        }
    }
}
