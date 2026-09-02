@echo off
title Smart Hospital Management System - Master Launcher
color 0F

:menu
cls
echo ================================================================
echo         SMART HOSPITAL MANAGEMENT SYSTEM [MASTER LAUNCHER]
echo ================================================================
echo.
echo   [1] Launch Java AWT Applet Program (Standalone Desktop GUI)
echo   [2] Launch Classic Console Menu System (Terminal CLI)
echo   [3] Test MySQL Database Connection and Initialize Schema
echo   [4] Compile All Java Source Files (Build to out/)
echo   [5] Exit
echo.
echo ================================================================
set /p choice="Enter your choice (1-5): "

if "%choice%"=="1" (
    call run-applet.bat
    goto menu
)
if "%choice%"=="2" (
    cls
    echo Starting Console System...
    java -cp "out;mysql-connector-j-9.5.0.jar;lib/*" hospital.main.HospitalManagementSystem
    pause
    goto menu
)
if "%choice%"=="3" (
    cls
    echo Testing MySQL Database Connection via JDBC...
    java -cp "out;mysql-connector-j-9.5.0.jar;lib/*" hospital.db.DBConnection
    pause
    goto menu
)
if "%choice%"=="4" (
    cls
    echo Compiling all Java classes...
    if not exist "out" mkdir out
    javac -cp ".;mysql-connector-j-9.5.0.jar;lib/*" -d out -encoding UTF-8 src\hospital\model\*.java src\hospital\exception\*.java src\hospital\service\*.java src\hospital\thread\*.java src\hospital\db\*.java src\hospital\applet\*.java src\hospital\main\*.java
    echo Build finished with exit code %ERRORLEVEL%.
    pause
    goto menu
)
if "%choice%"=="5" exit /b 0

echo Invalid choice.
pause
goto menu
