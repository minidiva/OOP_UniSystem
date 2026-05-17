@echo off
cd /d "%~dp0"
if exist bin (
    rd /s /q bin
)
mkdir bin
mkdir bin\lib

setlocal enabledelayedexpansion
set "files="
for /r %%f in (*.java) do (
    set "files=!files! "%%f""
)

javac --module-path src\lib -d bin !files!
if errorlevel 1 (
    echo Compilation failed. Fix errors above.
    exit /b 1
)

if exist src\lang (
    if not exist bin\lang mkdir bin\lang
    xcopy /e /i /y "src\lang\*" "bin\lang\" >nul
)
if exist src\lib\gson.jar (
    copy /y "src\lib\gson.jar" "bin\lib\" >nul
)

java --module-path "bin;bin\lib" --module StudentPortal/app.App
