$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $projectRoot

Get-Process -Name java -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue

if (Test-Path bin) {
    Remove-Item -Recurse -Force bin | Out-Null
}

New-Item -ItemType Directory -Path bin | Out-Null
New-Item -ItemType Directory -Path bin\lib | Out-Null

$javaFiles = Get-ChildItem -Path src -Recurse -Filter *.java | Sort-Object FullName | ForEach-Object FullName

javac --module-path src\lib -d bin $javaFiles
if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed. Fix errors above." -ForegroundColor Red
    exit $LASTEXITCODE
}

if (Test-Path src\lang) {
    Copy-Item -Path src\lang -Destination bin -Recurse -Force
}

if (Test-Path src\lib\gson.jar) {
    Copy-Item -Path src\lib\gson.jar -Destination bin\lib -Force
}

java --module-path "bin;bin\lib" --module StudentPortal/app.App
