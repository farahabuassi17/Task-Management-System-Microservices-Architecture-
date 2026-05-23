@echo off
setlocal

set "MAVEN_WRAPPER_DIST=%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.9.14"
set "MAVEN_CMD="

for /D %%D in ("%MAVEN_WRAPPER_DIST%\*") do (
  if exist "%%D\bin\mvn.cmd" set "MAVEN_CMD=%%D\bin\mvn.cmd"
)

if not "%MAVEN_CMD%"=="" (
  call "%MAVEN_CMD%" %*
  exit /b %ERRORLEVEL%
)

where mvn.cmd >nul 2>nul
if %ERRORLEVEL%==0 (
  call mvn.cmd %*
  exit /b %ERRORLEVEL%
)

where mvn >nul 2>nul
if %ERRORLEVEL%==0 (
  call mvn %*
  exit /b %ERRORLEVEL%
)

echo Maven was not found. Please run Maven once from another service wrapper, or install Maven.
exit /b 1
