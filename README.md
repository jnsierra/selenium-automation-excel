# selenium-automation-excel

Proyecto con el cual se busca generar por medio de un excel automatizar la generación de casos de pruebas. Por medio de selenium

## Comenzando 🚀

La configuracion de java es:

```
$ java -version

java version "1.8.0_202"
Java(TM) SE Runtime Environment (build 1.8.0_202-b08)
Java HotSpot(TM) 64-Bit Server VM (build 25.202-b08, mixed mode)
```

Configuración maven:
```
$ mvn -version

Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /opt/apache/apache-maven-3.6.3
Java version: 1.8.0_202, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre
Default locale: en_CO, platform encoding: UTF-8
OS name: "mac os x", version: "10.16", arch: "x86_64", family: "mac"
```

## Instalación

1. Descarga del repositorio:
```
git clone https://github.com/jnsierra/selenium-automation-excel.git
```
2. Limpiar y compilar el proyecto.
```
mvn clean install
```
3. Vamos a la carpeta en donde se genera el jar selenium-automation-excel/target/ y ejecutamos el siguente comando:
```
java -jar selenium-automation-excel_1.29-SNAPSHOT.jar
```
Nota: Se debe tener en cuenta que con este comando se pueden agregar el numero de parametros que sea necesario los cuales se describiran a continuación:

## Parametros de arranque
Se listaran los parametros que se podran añadir a la ejecución del jar
1. Browser (Indica sobre que navegador se realizara la prueba)
```
--selenium.browser="chrome"
--selenium.browser="firefox"
```
2. Ubicación Driver browser (Indica el path donde se encuentra el driver correspondiende al browser)
```
--selenium.path.chrome=/opt/repository/driver/chromedriver_win32/chromedriver
--selenium.path.firefox=/opt/repository/driver/firefox/geckodriver
```
3. Ubicacion excel; el sistema identifica que tipo de sistema operativo se esta ejecutando y debe cambiar por la ruta correcta.
```
--excel.file.mac=/Users/sierraj/fisa/repositories/excel-automation/Book1_1.xlsx
--excel.file.windows=/opt/repository/excel/Book1_1.xlsx
```
4. Ubicación de los screenshot como evidencias; el sistema identifica que tipo de sistema operativo se esta ejecutando y toma la variable correspondiente.
```
--picture.evidence.mac=/opt/repository/screenshot/
--picture.evidence.windows=/opt/repository/screenshot/
```