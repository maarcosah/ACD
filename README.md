# ACD
Tareas de ACD de 2n DAM

## Actualización a Java 21 (instrucciones y cambios aplicados)

He preparado el proyecto para compilar contra Java 21. Cambios aplicados:

- Añadido maven-compiler-plugin con `<release>21</release>` en los poms de los proyectos Maven:
	- `demo/pom.xml`
	- `handling-form-submission/handling-form-submission/pom.xml`
	- `Practica02/pom.xml`

- El proyecto Gradle (`practica1/build.gradle`) ya usa toolchain con Java 21.

Pasos recomendados para verificar localmente (Windows PowerShell):

```powershell
# Instalar JDK 21 y establecer JAVA_HOME (ajusta la ruta según tu instalación)
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path

# Verificar versión de Java
java -version

# Para proyectos Maven (ejemplo desde la carpeta raíz del repo)
mvn -v
mvn -T 1C clean verify

# Para el proyecto Gradle con wrapper (practica1)
cd practica1
./gradlew.bat clean build
```

