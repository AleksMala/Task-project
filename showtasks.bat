call runcrud
if "%ERRORLEVEL%" == "0" goto getsite
echo.
echo RUNCRUD has errors
goto fail

:getsite
start chrome "http://localhost:8080/crud/v1/task/getTasks"
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.
