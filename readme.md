RMANPO QA Regression autotests README


Требования к компонентам

Необходимо установить следующие компоненты:
1. Git версия 2.24 или выше
2. IntellijIdea версия 2018.3.6 или выше
3. Java OpenSDK версия 1.8
4. Apache Maven версия 3.6 или выше
5. Google Chrome версия 83 или выше


Настройка компонентов

Java:
- Установите Java OpenSDK
- Проверьте, что переменная среды JAVA_HOME указывает на папку с установленной версией

Maven:
- Установите версию Maven
- Создайте пустую папку с полными правами на запись для хранения библиотек java
- Пропишите эту папку в файле /conf/settings.xml в папке с Apache Maven внизу файла в тэге <localRepository>имя папки</localRepository>

Chrome:
- Запустите Chrome браузер, зайдите в главном меню в пункт Справка - О браузере Google Chrome - запомните текущую версию
- Введите строку поиска "Chromedriver download" в браузер и скачайте версию chromedriver.exe под вашу версию браузера
- Создайте подпапку в папке с библиотеками java /Chromedriver и распакуйте содержимое скачанного архива с chromedriver.exe

Git:
- Создайте пустую папку с полными правами на запись, в которую будет выгружен код автотестов из GitHub репозитария
- Откройте папку верхнего уровня в Windows Explorer, правой кнопкой мыши нажать на созданной подпапке, в контекстном меню выберите Git Bash
- В GitBash введите следующую команду:
git clone https://github.com/kraeser1978/RmanpoQA.git
команда закачивает содержимое репозитария в созданную подпапку
- После окончания выполнения команды зайдите в папку и убедитесь, что файлы были закачаны в нее

IntellijIdea:
- Запустите IntellijIdea, выберите Open Project, выберите созданную папку с закачанными файлами кода
- В главном меню IntellijIdea слева выберите вертикальную вкладку Maven Projects, в ней нажмите кнопку +, выберите папку с файлами, укажите файл pom.xml
- В главном меню IntellijIdea зайдите File - Settings - Build Execution Deployment - Build Tools - Maven - в поле User Settings File нажмите Override и выберите /conf/settings.xml в папке с Apache Maven
- Убедитесь, что в поле Local repository подгрузилось имя папки библиотек java, прописанное в settings.xml
- В подокне Maven Projects выберите RMANPO - Install, нажмите кнопку Run Maven Build в верхнем меню
- Проверьте, что файлы библиотек java закачиваются в папку библиотек


Дополнительные настройки проекта:
- в Windows создайте новые переменные среды для вашего Windows аккаунта: Rmanpo_autotest_settings, RmanpoQA_personal_case_inputDataFile, RmanpoQA_personal_case_locators
- скопируйте файл rmanpo.properties из выгруженных файлов проекта в какую-либо папку с полным доступом, находящуюся не в папке проекта
- пропишите полный путь к файлу в переменную среды Rmanpo_autotest_settings
- пропишите полный путь к файлу с инпут значениями полей personal_case_input_data.txt в переменную среды RmanpoQA_personal_case_inputDataFile
- пропишите полный путь к файлу с локаторами personal_case_locators.properties в переменную среды RmanpoQA_personal_case_locators
- создайте отдельную папку с правами на запись для хранения лог файлов проекта
- создайте отдельную папку с правами на запись для хранения скриншотов ошибок

Правка файла rmanpo.properties:
- Задайте значения для следующих параметров проекта в файле
- ВАЖНО: разделители в именах папок в Windows указываются с двойными слэшами - см образец в скачанном файле rmanpo.properties

- driver_path = полный путь к файлу chromedriver.exe
- log_file_path = полное имя лог файла
- screenshots_folder = полный путь к папке для хранения скриншотов ошибок
- passport_scan_file_path = полный путь к файлу со сканом паспорта
- snils_scan_file_path = полный путь к файлу со сканом СНИЛС
- rmanpo_url = ссылка на приложение РМАНПО
- user_name = имя пользователя РМАНПО
- password = пароль пользователя



Запуск автотестов

- Откройте проект в IntellijIdea
- В дереве компонентов слева выберите src - test - java - Tests - откройте класс PersonalCaseTest 
- Нажмите стрелку напротив строки public void test01_
- дождитесь окончания выполнения автотеста
- проанализируйте результаты теста в консоли IntellijIdea и содержимое в папке скриншотов

