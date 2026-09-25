# syntax=docker/dockerfile:1
# ============================================================
#  discovery-server
#  BUILD CONTEXT = repository root (see docker-compose.yml),
#  so the parent pom.xml (../pom.xml) is available to Maven.
# ============================================================

# ── Build stage ──────────────────────────────────────────────
FROM eclipse-temurin:25-jdk-alpine AS build

WORKDIR /workspace

# Parent POM, resolved through <relativePath>../pom.xml</relativePath>
COPY pom.xml ./pom.xml

# Module wrapper + POM first → dependency layer is cached until the POMs change
COPY discovery-server/.mvn/ discovery-server/.mvn/
COPY discovery-server/mvnw discovery-server/pom.xml discovery-server/

WORKDIR /workspace/discovery-server

# Strip Windows CRLF line endings, otherwise alpine reports "mvnw: not found"
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B -q dependency:go-offline

COPY discovery-server/src/ src/

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B -q clean package -Dmaven.test.skip=true

# ── Runtime stage ────────────────────────────────────────────
FROM eclipse-temurin:25-jre-alpine AS runtime

RUN addgroup -S sms && adduser -S sms -G sms

WORKDIR /app

COPY --from=build /workspace/discovery-server/target/discovery-server-*.jar app.jar

USER sms

EXPOSE 8761

# Container support is on by default in modern JDKs; only the heap ratio is needed
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]