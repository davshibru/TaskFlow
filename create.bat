@echo off
rem Переключаем кодировку консоли на UTF-8
chcp 65001 > nul

rem Указываем путь к встроенной Java от Android Studio
set JAVA_HOME=D:\Program Files\Android\Android Studio\jbr

rem Запускаем генератор
call gradlew.bat :template:script:run --quiet -Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 --args="%*"