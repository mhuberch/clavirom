# Upgrade Plan: Integrating HeliBoard 4.2 into ClaviRom

This document outlines the steps to synchronize the **ClaviRom** fork with the official **HeliBoard 4.2** release while preserving custom Romansh dictionaries and branding.

## Prerequisites
Ensure the upstream remote is configured:
```bash
git remote add upstream https://github.com/Helium314/HeliBoard.git
```

## Step 1: Fix Tag Conflicts
Force fetch the latest tags to avoid conflicts:
```bash
git fetch upstream --tags -f
```

## Step 2: Synchronize `main` with Upstream
Use the `main` branch as a clean mirror of the official HeliBoard releases.
```bash
git checkout main
git reset --hard v4.2
git push origin main --force
```

## Step 3: Merge Updates into ClaviRom Branch
Redoing the merge from a clean state (release 3.8.1 was the last known clean ClaviRom release):
```bash
git checkout -b merge-hb4.2 release_3_8_1
git merge main
```

## Step 4: Restore ClaviRom Branding & Specifics
After the merge, restore the project identity and Romansh-specific logic. 

**Note on Build Configuration:**
We adopt the upstream (HeliBoard) build configuration as the authoritative source for general app functionality, dependencies, and build logic. ClaviRom-specific overrides are kept to a minimum to ease future upgrades.

**Required Branding Changes in `app/build.gradle.kts`:**
1. **Application ID**: Change `applicationId` to `"clavirom.keyboard"`.
2. **Version Name**: Update `versionName` to `"4.2"`.
3. **APK Name**: Ensure `outputFileName` uses `"ClaviRom_..."`.

**Required ClaviRom Specifics:**
1. **Dictionaries**: Verify Romansh dictionaries in `app/src/main/assets/dicts/`.
2. **Romansh Logic**: Ensure `isDictionaryMatch` logic in `DictionaryFactory.kt` and `Clavirom.kt` is preserved.
3. **Welcome Wizard**: Ensure Romansh-specific welcome strings and wizard logic in `WelcomeWizard.kt` are active.
4. **Strings**: Check `res/values/strings.xml` and debug flavors for any "HeliBoard" mentions that should be "ClaviRom".

## Step 5: Verification
1. **Dictionaries**: Confirm regional Romansh variants are available in settings.
2. **Build**: Build the project to ensure ClaviRom branding is applied to the final APK.
3. **Wizard**: Verify the setup wizard displays Romansh options correctly on first launch.
