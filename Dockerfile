FROM mcr.microsoft.com/playwright/java:v1.57.0-jammy

WORKDIR /app

COPY . .

RUN ./gradlew dependencies --no-daemon

ENTRYPOINT ["./gradlew"]
CMD ["test", "--no-daemon", "-PHEADLESS=true"]