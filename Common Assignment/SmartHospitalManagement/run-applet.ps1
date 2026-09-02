# PowerShell Launcher for Smart Hospital AWT Applet & Standalone GUI
Write-Host "================================================================" -ForegroundColor Cyan
Write-Host "  SMART HOSPITAL MANAGEMENT SYSTEM [AWT Applet and JDBC]" -ForegroundColor Cyan
Write-Host "================================================================" -ForegroundColor Cyan

if (!(Test-Path -Path "out")) {
    New-Item -ItemType Directory -Path "out" | Out-Null
}

Write-Host "[1/2] Compiling all Java source files..." -ForegroundColor Yellow
$sources = Get-ChildItem -Recurse -Filter *.java src | Select-Object -ExpandProperty FullName
javac -cp ".;mysql-connector-j-9.5.0.jar;lib/*" -d out -encoding UTF-8 $sources

if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Compilation failed." -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "[2/2] Launching Java AWT Applet Standalone GUI..." -ForegroundColor Green
java -cp "out;mysql-connector-j-9.5.0.jar;lib/*" hospital.applet.HospitalApplet
