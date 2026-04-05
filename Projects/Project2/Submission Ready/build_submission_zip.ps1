param(
    [string]$ProjectRoot = ".",
    [string]$OutputFolder = ".\Submission Ready"
)

$ErrorActionPreference = "Stop"

Set-Location $ProjectRoot

if (!(Test-Path $OutputFolder)) {
    New-Item -ItemType Directory -Path $OutputFolder | Out-Null
}

$javaFiles = Get-ChildItem -File -Filter "*.java" | Select-Object -ExpandProperty FullName
if ($javaFiles.Count -eq 0) {
    throw "No Java source files found in $ProjectRoot"
}

$zipPath = Join-Path $OutputFolder "JavaSourceCode.zip"
if (Test-Path $zipPath) {
    Remove-Item $zipPath -Force
}
Compress-Archive -Path $javaFiles -DestinationPath $zipPath

$docFiles = @(
    "README.md",
    "INDEX.md",
    "SUBMISSION_CHECKLIST.md",
    "DEVELOPERS_GUIDE.md",
    "TEST_PLAN.md",
    "UML_CLASS_DIAGRAM.md",
    "UML_CLASS_DIAGRAM.mmd",
    "UML_EVENT_FLOW.md",
    "UML_EVENT_FLOW.mmd"
)

foreach ($file in $docFiles) {
    if (Test-Path $file) {
        Copy-Item $file -Destination $OutputFolder -Force
    }
}

if (Test-Path "TestScreenshots") {
    $destShots = Join-Path $OutputFolder "TestScreenshots"
    if (Test-Path $destShots) {
        Remove-Item $destShots -Recurse -Force
    }
    Copy-Item "TestScreenshots" -Destination $OutputFolder -Recurse
}

Write-Host "Submission package prepared in: $OutputFolder"
Write-Host "Created: $zipPath"
