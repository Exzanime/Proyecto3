# Etapa de construccion
FROM eclipse-temurin:21-jdk AS builder
LABEL authors="nacho"
WORKDIR /app

# Copiamos los archivos necesarios para construir el proyecto
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Descargar dependencias
RUN ./mvnw dependency:resolve

# Copiamos el código fuente
COPY src src

# Compilamos el proyecto
RUN ./mvnw clean package -DskipTests

# Etapa de ejecucion
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiamos el JAR generado en la etapa anterior
COPY --from=builder /app/target/usuarioService-1.0.0.jar usuarioService.jar

# Configuramos el comando de inicio del contenedor
ENTRYPOINT ["java", "-jar", "usuarioService.jar"]
