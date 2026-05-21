@echo off
setlocal

set "here=%cd%"
set "javaExe="

:: Check whether java_vm exists, if not fall back to runtime.
if exist "%here%\.\java_vm\bin\java.exe" (
    set "javaExe=%here%\.\java_vm\bin\java.exe"
) else if exist "%here%\..\runtime\bin\java.exe" (
    set "javaExe=%here%\..\runtime\bin\java.exe"
) else (
    set "javaExe=java"
)

"%javaExe%" -classpath "%here%\.\datdec.jar;%here%\.\code\projectx-pcode.jar" com.lucasluqui.datdec.DatdecApp

pause