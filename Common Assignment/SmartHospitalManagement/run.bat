@echo off
title Smart Hospital Management System - Java AWT Applet Program
color 0B
echo ================================================================
echo   SMART HOSPITAL MANAGEMENT SYSTEM [AWT Applet and JDBC]
echo ================================================================
echo.

if not exist "out" mkdir out

echo [1/2] Compiling Java source files...
javac -cp ".;mysql-connector-j-9.5.0.jar;lib/*" -d out -encoding UTF-8 src\hospital\model\*.java src\hospital\exception\*.java src\hospital\service\*.java src\hospital\thread\*.java src\hospital\db\*.java src\hospital\applet\*.java src\hospital\main\*.java

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed.
    pause
    exit /b %ERRORLEVEL%
)

echo [2/2] Launching Java AWT Applet Program...
echo.
java -cp "out;mysql-connector-j-9.5.0.jar;lib/*" hospital.applet.HospitalApplet

pause
