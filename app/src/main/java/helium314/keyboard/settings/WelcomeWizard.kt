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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import helium314.keyboard.latin.R
import helium314.keyboard.latin.utils.JniUtils
import helium314.keyboard.latin.utils.Theme
import helium314.keyboard.latin.utils.SubtypeSettings
import helium314.keyboard.latin.utils.UncachedInputMethodManagerUtils
import helium314.keyboard.latin.utils.previewDark
import helium314.keyboard.latin.utils.locale
import helium314.keyboard.latin.utils.prefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private var persistedStep: Int? = null
private var persistedLanguage: String? = null

@Composable
fun WelcomeWizard(
    onLanguageClick: () -> Unit,
    close: () -> Unit,
    finish: () -> Unit,
    initialStep: Int? = null,
    initialLanguage: String? = null
) {
    val ctx = LocalContext.current
    val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val uriHandler = LocalUriHandler.current
    fun determineStep(): Int = when {
        !UncachedInputMethodManagerUtils.isThisImeEnabled(ctx, imm) -> 0
        !UncachedInputMethodManagerUtils.isThisImeCurrent(ctx, imm) -> 2
        else -> 3
    }

    val languages = remember { getShuffledWizardLanguages() }

    var step by rememberSaveable { mutableIntStateOf(initialStep ?: persistedStep ?: determineStep()) }
    var selectedLanguage by rememberSaveable { mutableStateOf(initialLanguage ?: persistedLanguage ?: languages.first().first) }
    val strings = WIZARD_TRANSLATIONS[selectedLanguage] ?: WIZARD_TRANSLATIONS[languages.first().first]!!

    val scope = rememberCoroutineScope()
    LaunchedEffect(step) {
        persistedStep = step
        if (step == 2)
            scope.launch {
                while (step == 2 && !UncachedInputMethodManagerUtils.isThisImeCurrent(ctx, imm)) {
                    delay(50)
                }
                step = 3
            }
    }
    LaunchedEffect(selectedLanguage) {
        persistedLanguage = selectedLanguage
    }

    val useWideLayout = LocalConfiguration.current.screenWidthDp > 600
    val stepBackgroundColor = Color(ContextCompat.getColor(ctx, R.color.setup_step_background))
    val textColor = Color(ContextCompat.getColor(ctx, R.color.setup_text_action))
    val textColorDim = textColor.copy(alpha = 0.5f)
    val titleColor = Color(ContextCompat.getColor(ctx, R.color.setup_text_title))

    @Composable fun bigText() {
        val title = when (step) {
            0 -> strings.welcomeTitle
            4 -> strings.step4Title
            5 -> strings.step5Title
            else -> strings.stepsTitle
        }
        Column(Modifier.padding(bottom = 36.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                color = titleColor,
                modifier = Modifier.fillMaxWidth()
            )
            if (step == 0) {
                Spacer(Modifier.height(8.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        strings.welcomeSubtitle,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        color = titleColor
                    )
                }
            }
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
            for (i in 1..5) {
                Text(i.toString(), color = if (step == i) titleColor else textColorDim)
            }
        }
        Column(Modifier
            .fillMaxWidth()
            .background(color = stepBackgroundColor)
            .padding(16.dp)
        ) {
            Text(title)
            Text(instruction, style = MaterialTheme.typography.bodyLarge.merge(color = textColor))
        }
        Spacer(Modifier.height(4.dp))
        Row(
            Modifier.fillMaxWidth()
                .clickable { action() }
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
                languages = languages,
                selectedLanguage = selectedLanguage,
                onLanguageSelected = { selectedLanguage = it },
                startText = strings.startAction,
                onClick = { step = 1 }
            )
        else
            Column(Modifier.fillMaxWidth()) {
                val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    step = determineStep()
                }
                if (step == 1) {
                    Step(
                        step,
                        strings.step1Title,
                        strings.step1Instruction,
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
                        strings.step2Title,
                        strings.step2Instruction,
                        strings.step2Action,
                        painterResource(R.drawable.ic_setup_select),
                        imm::showInputMethodPicker
                    )
                } else if (step == 3) {
                    Step(
                        step,
                        strings.step3Title,
                        strings.step3Instruction,
                        strings.step3Action,
                        painterResource(R.drawable.sym_keyboard_language_switch)
                    ) {
                        persistedStep = 4
                        step = 4
                        // Activate the selected language before navigating
                        SubtypeSettings.getAllAvailableSubtypes().firstOrNull {
                            it.locale().toLanguageTag() == selectedLanguage
                        }?.let { subtype ->
                            SubtypeSettings.addEnabledSubtype(ctx.prefs(), subtype)
                        }
                        onLanguageClick()
                    }
                } else if (step == 4) {
                    val boldifiedText2 = boldifySubstrings(strings.step4Text2, CLAVIROM_BOLD_WORDS)
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            for (i in 1..5) {
                                Text(i.toString(), color = if (step == i) titleColor else textColorDim)
                            }
                        }
                        Column(Modifier
                            .background(color = stepBackgroundColor)
                            .padding(32.dp)
                            .fillMaxWidth()
                        ) {
                            Text(
                                buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(strings.step4Tip)
                                    }
                                    append(strings.step4Text1)
                                },
                                style = MaterialTheme.typography.bodyMedium.merge(color = textColor)
                            )
                        }
                        Image(
                            painterResource(R.drawable.tasta_da_lungatgs),
                            null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Column(Modifier
                            .background(color = stepBackgroundColor)
                            .padding(32.dp)
                            .fillMaxWidth()
                        ) {
                            Text(
                                buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(strings.step4Thanks)
                                    }
                                    append(boldifiedText2)
                                },
                                style = MaterialTheme.typography.bodyMedium.merge(color = textColor)
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Row(
                            Modifier.clickable { step = 5 }
                                .background(color = stepBackgroundColor)
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painterResource(R.drawable.ic_setup_check), null, Modifier.padding(end = 6.dp).size(32.dp), tint = textColor)
                            Text(strings.step4Continue, Modifier.weight(1f))
                        }
                    }
                } else { // step 5
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        for (i in 1..5) {
                            Text(i.toString(), color = if (step == i) titleColor else textColorDim)
                        }
                    }
                    Column(Modifier
                        .background(color = stepBackgroundColor)
                        .padding(16.dp)
                        .fillMaxWidth()
                    ) {
                        Text(strings.step5Title)
                        Text(strings.step5Instruction, style = MaterialTheme.typography.bodyLarge.merge(color = textColor))
                    }

                    Spacer(Modifier.height(4.dp))

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(color = stepBackgroundColor)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = strings.step5SupportTitle,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        )
                        Spacer(Modifier.height(8.dp))

                        val bulletPoints = listOf(
                            strings.step5SupportItem1 to HELIBOARD_WIKI_URL,
                            strings.step5SupportItem2 to CLAVIROM_DISCUSSIONS_URL,
                            strings.step5SupportItem3 to null
                        )

                        bulletPoints.forEach { (item, url) ->
                            Row(
                                Modifier
                                    .padding(vertical = 2.dp)
                                    .then(if (url != null) Modifier.clickable { uriHandler.openUri(url) } else Modifier)
                            ) {
                                Text(text = "• ", style = MaterialTheme.typography.bodyMedium.copy(color = textColor))
                                Text(
                                    text = buildAnnotatedString {
                                        if (url != null) {
                                            append("$item: ")
                                            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                                                append(url)
                                            }
                                        } else {
                                            append(item)
                                        }
                                    },
                                    style = MaterialTheme.typography.bodyMedium.copy(color = textColor)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    Row(
                        Modifier.clickable { onLanguageClick() }
                            .background(color = stepBackgroundColor)
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.sym_keyboard_language_switch), null, Modifier.padding(end = 6.dp).size(32.dp), tint = textColor)
                        Text(strings.step5Action, Modifier.weight(1f))
                    }

                    Spacer(Modifier.height(4.dp))

                    Row(
                        Modifier.clickable { finish() }
                            .background(color = stepBackgroundColor)
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_setup_check),
                            null,
                            Modifier.padding(end = 6.dp).size(32.dp),
                            tint = textColor
                        )
                        Text(strings.finishAction, Modifier.weight(1f))
                    }
                }
            }
    }
    Surface {
        CompositionLocalProvider(
            LocalContentColor provides textColor,
            LocalTextStyle provides MaterialTheme.typography.titleLarge.merge(color = textColor),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter // Alignment at top to ensure scrolling starts from the top
            ) {
                val scrollState = rememberScrollState()
                // Reset scroll state when moving to a different step
                LaunchedEffect(step) {
                    scrollState.scrollTo(0)
                }

                if (useWideLayout)
                    Row(Modifier.fillMaxWidth().verticalScroll(scrollState).padding(32.dp)) {
                        Box(Modifier.weight(0.4f)) {
                            bigText()
                        }
                        Box(Modifier.weight(0.6f)) {
                            steps()
                        }
                    }
                else
                    Column(Modifier.fillMaxWidth().verticalScroll(scrollState).padding(16.dp)) {
                        bigText()
                        steps()
                        Spacer(Modifier.height(32.dp)) // Extra bottom clearance
                    }
            }
        }
    }
}

@Composable
fun Step0(
    languages: List<Pair<String, String>>,
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    startText: String,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Image(painterResource(R.drawable.setup_welcome_image), null, modifier = Modifier.size(120.dp))

        Spacer(Modifier.height(16.dp))

        Column(Modifier.selectableGroup().fillMaxWidth()) {
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

        Row(Modifier.fillMaxWidth().clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 4.dp)
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

@Composable
private fun boldifySubstrings(
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

private const val PREVIEW_LANGUAGE = "de-CH"
private const val PREVIEW_THEME_WIZARD_DARK = false

@Preview
@Composable
private fun Step0Preview() {
    Theme(PREVIEW_THEME_WIZARD_DARK) {
        Surface {
            WelcomeWizard({}, {}, {}, initialStep = 0, initialLanguage = PREVIEW_LANGUAGE)
        }
    }
}

@Preview
@Composable
private fun Step1Preview() {
    Theme(PREVIEW_THEME_WIZARD_DARK) {
        Surface {
            WelcomeWizard({}, {}, {}, initialStep = 1, initialLanguage = PREVIEW_LANGUAGE)
        }
    }
}

@Preview
@Composable
private fun Step2Preview() {
    Theme(PREVIEW_THEME_WIZARD_DARK) {
        Surface {
            WelcomeWizard({}, {}, {}, initialStep = 2, initialLanguage = PREVIEW_LANGUAGE)
        }
    }
}

@Preview
@Composable
private fun Step3Preview() {
    Theme(PREVIEW_THEME_WIZARD_DARK) {
        Surface {
            WelcomeWizard({}, {}, {}, initialStep = 3, initialLanguage = PREVIEW_LANGUAGE)
        }
    }
}

@Preview
@Composable
private fun Step4Preview() {
    Theme(PREVIEW_THEME_WIZARD_DARK) {
        Surface {
            WelcomeWizard({}, {}, {}, initialStep = 4, initialLanguage = PREVIEW_LANGUAGE)
        }
    }
}

@Preview
@Composable
private fun Step5Preview() {
    Theme(PREVIEW_THEME_WIZARD_DARK) {
        Surface {
            WelcomeWizard({}, {}, {}, initialStep = 5, initialLanguage = PREVIEW_LANGUAGE)
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
            WelcomeWizard({}, {}, {})
        }
    }
}
