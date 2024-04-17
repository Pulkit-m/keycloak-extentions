title Starting KeyCloak server with new plugins
echo Navigating to Project Directory...
cd "D:\acer\WorkStation\Development\keycloak-extensions"
echo Packaging Keycloak Extensions...
call mvn clean package 

cd "D:\acer\WorkStation\Development\keycloak-24.0.2\providers"
echo Copying Jar file to Keycloak Provider directory...
@REM dir /b keycloak-extensions* > nul 
@REM if errorlevel 1 (
@REM     There already exists a jar file... Removing it
@REM     del keycloak-extensions*
@REM ) 

del keycloak-extensions*.jar
echo Removed older extension versions
copy D:\acer\WorkStation\Development\keycloak-extensions\target\keycloak-extensions*.jar  D:\acer\WorkStation\Development\keycloak-24.0.2\keycloak-24.0.2\providers\
echo Copied compiled code to provider directory

echo Starting KeyCloak Server 
call D:\acer\WorkStation\Development\keycloak-24.0.2\keycloak-24.0.2\bin\kc.bat start-dev

keycloak-extensions-1.0-SNAPSHOT