# BiScience UI Test Automation

Playwright + Java + TestNG test automation framework for [stg-ui.adcint.com](https://stg-ui.adcint.com).

---
## Hardcoded values

- Class EnvConfig contains hardcoded mine, but other can be passed via run variables if mine are not active anymore
- Url is hardcoded same for all envs in EnvConfig.class cause I have only 1 valid url. Config can be easily updated for all env parameters

## Before run

- needs gradle installed on env. For container run docker should be up and running and docker compose should be installed
- exec command to make run work on your env
```bash
gradle wrapper
```
## Parameters

All parameters can be passed as environment variables or Gradle properties (`-P`). Environment variables take priority over Gradle properties.

| Parameter | Description | Possible Values | Default |
|---|---|---|---|
| `ENV` | Target environment | `stg`, `dev` | `stg` |
| `BROWSER` | Browser to run tests in | `CHROMIUM`, `FIREFOX`, `WEBKIT` | `CHROMIUM` |
| `HEADLESS` | Run browser in headless mode | `true`, `false` | `false` |
| `THREAD_COUNT` | Number of parallel test threads | Any positive integer | `4` |
| `GROUP` | TestNG group to run. Leave empty to run all tests | `ALL`, or any defined group name | *(all tests)* |
| `USER_EMAIL` | Login email for the test user | Any valid account email | *(none)* |
| `USER_PASSWORD` | Login password for the test user | Any valid account password | *(none)* |

---

## Running Tests

### Using Environment Variables

**Bash:**
```bash
ENV=stg \
BROWSER=CHROMIUM \
HEADLESS=false \
THREAD_COUNT=3 \
GROUP=ALL \
USER_EMAIL=your@email.com \
USER_PASSWORD=yourpassword \
./gradlew test --continue --rerun-tasks
```

**PowerShell:**
```powershell
$env:ENV="stg"
$env:BROWSER="CHROMIUM"
$env:HEADLESS="false"
$env:THREAD_COUNT="3"
$env:GROUP="ALL"
$env:USER_EMAIL="your@email.com"
$env:USER_PASSWORD="yourpassword"

.\gradlew test --continue --rerun-tasks

Remove-Item Env:ENV
Remove-Item Env:BROWSER
Remove-Item Env:HEADLESS
Remove-Item Env:THREAD_COUNT
Remove-Item Env:GROUP
Remove-Item Env:USER_EMAIL
Remove-Item Env:USER_PASSWORD
```

---

### Using Gradle Properties

**Bash:**
```bash
./gradlew test --continue --rerun-tasks \
  -PENV=stg \
  -PBROWSER=CHROMIUM \
  -PHEADLESS=false \
  -PTHREAD_COUNT=3 \
  -PGROUP=ALL \
  -PUSER_EMAIL=your@email.com \
  -PUSER_PASSWORD=yourpassword
```

**PowerShell:**
```powershell
.\gradlew test --continue --rerun-tasks `
  -PENV=stg `
  -PBROWSER=CHROMIUM `
  -PHEADLESS=false `
  -PTHREAD_COUNT=3 `
  -PGROUP=ALL `
  -PUSER_EMAIL=your@email.com `
  -PUSER_PASSWORD=yourpassword
```

---

### Using Docker

Build the image and run tests in a container. The container is removed automatically after the run.

```bash
docker build -t ca-front-tests:latest . && \
docker run --rm \
  -e ENV=stg \
  -e BROWSER=CHROMIUM \
  -e HEADLESS=true \
  -e THREAD_COUNT=3 \
  -e GROUP=ALL \
  -e USER_EMAIL=your@email.com \
  -e USER_PASSWORD=yourpassword \
  -v "$(pwd)/build/allure-results:/app/build/allure-results" \
  ca-front-tests:latest test --no-daemon
```

> **Note:** `HEADLESS` must be `true` when running inside Docker — there is no display available.

---

## Allure Report

Generate and open the Allure report after a test run:

```bash
./gradlew report
```

This generates the report and serves it in your browser automatically.

---

## Install Browsers

To install Playwright browser binaries (required once before running locally):

```bash
./gradlew installBrowsers
```
