title Starting KeyCloak server with new plugins
echo Navigating to Project Directory...
cd "D:\acer\WorkStation\Development\keycloak-extensions"
echo Packaging Keycloak Extensions...
call mvn clean package 

echo Copying Jar file to Keycloak Provider directory... 
cd "D:\acer\WorkStation\Development\keycloak-23.0.7\providers" 
@REM dir /b keycloak-extensions* > nul 
@REM if errorlevel 1 (
@REM     There already exists a jar file... Removing it
@REM     del keycloak-extensions*
@REM ) 

del keycloak-extensions*.jar
copy D:\acer\WorkStation\Development\keycloak-extensions\target\keycloak-extensions*.jar D:\acer\WorkStation\Development\keycloak-23.0.7\providers\

echo Starting KeyCloak Server 
call D:\acer\WorkStation\Development\keycloak-23.0.7\bin\kc.bat start-dev