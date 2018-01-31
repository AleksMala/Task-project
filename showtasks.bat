call runcrud
if "%ERRORLEVEL%" == "0" goto getsite
echo.
echo RUNCRUD has errors
goto fail

:getsite
start "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo Cannot open window
goto fail

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.
