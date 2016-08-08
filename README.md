Test funcionales de browsers con Selenium sobre Sauce Labs
==========================================================

Requisitos
----------

- Una máquina local con JDK 1.7 o superior y Maven 3.3 o superior
- Una cuenta en [Sauce Labs](https://saucelabs.com/)

Configuración de Sauce Labs
----------------------------

La máquina local se conecta con los servidores remotos de Selenium de Sauce Labs para ejecutar los tests sobre distintos browsers. Los tests se ejecutan usando Maven, vía el plugin de JUnit y las bibliotecas Java de acceso remoto a Selenium.

La dirección de las credenciales de Sauce Labs y los servidores remotos de Selenium están en `src/main/resources/uitest.properties`:

    saucelabs.user = <USERNAME>
    saucelabs.key = <ACCESS_KEY>

    selenium.host = ondemand.saucelabs.com
    selenium.port = 80

Para más información, veáse [Sauce Labs Basics](https://wiki.saucelabs.com/display/DOCS/Sauce+Labs+Basics)

Compilación y ejecución
-----------------------

Para compilar y ejecutar todos los tests desde la máquina local:

    mvn test

Para ejecutar un test particular:

    mvn -Dtest=cl.entel.test.TestLogin test
