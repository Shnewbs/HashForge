@echo off
setlocal
for /f "tokens=2 delims=:." %%x in ('chcp') do set _codepage=%%x
chcp 65001>nul
cd C:\Users\Shnewbs\IdeaProjects\HashForge\HashForge\run
"C:\Program Files\Java\jdk-21\bin\java.exe" @C:\Users\Shnewbs\IdeaProjects\HashForge\HashForge\build\moddev\dataRunClasspath.txt @C:\Users\Shnewbs\IdeaProjects\HashForge\HashForge\build\moddev\dataRunVmArgs.txt -Dfml.modFolders=hashforge%%%%C:\Users\Shnewbs\IdeaProjects\HashForge\HashForge\build\classes\java\main;hashforge%%%%C:\Users\Shnewbs\IdeaProjects\HashForge\HashForge\build\resources\main net.neoforged.devlaunch.Main @C:\Users\Shnewbs\IdeaProjects\HashForge\HashForge\build\moddev\dataRunProgramArgs.txt
if not ERRORLEVEL 0 (  echo Minecraft failed with exit code %ERRORLEVEL%  pause)
chcp %_codepage%>nul
endlocal