## Pipeline Steps

The CI/CD workflow includes the following steps:

- **Checkout Repository**
    - Uses `actions/checkout@v4` to pull the latest code.

- **Set Up JDK 17**
    - Ensures compatibility with the Spring Boot application.

- **Build Application**
    - Runs `./gradlew clean build` to compile and package the app.

- **Build Docker Image**
    - Creates a Docker image (`spring-app`) for scanning with OWASP ZAP.

- **Create Docker Network**
    - Isolates the application and OWASP ZAP scanner for network communication.

- **Start Spring Boot Container**
    - Launches the container and waits for `/actuator/health` to confirm readiness.

- **Install Trivy**
    - Installs the Trivy vulnerability scanner.

- **Run Trivy scan on Docker image**
    - Scans the built Docker image (spring-app) for OS and dependency vulnerabilities.

- **Run Trivy filesystem scan**
    - Uploads scan results to the Actions Artifacts tab for review.

- **Upload Trivy Reports**
    - Uploads scan results to the Actions Artifacts tab for review.

- **Create ZAP workspace**
    - Prepares a directory to store ZAP scan results.

- **Run OWASP ZAP baseline scan**
    - Performs a DAST scan on the running application for common web vulnerabilities.

- **Upload ZAP Reports**
    - Stores the ZAP reports as artifacts.

- **Run Semgrep scan**
    - Stores the ZAP reports as artifacts.

- **Upload Semgrep Reports**
    - Runs static code analysis on the source code to detect security vulnerabilities, bad practices, and insecure patterns.
  
- **Display Scan Summary**
    - Uploads the results for review.

    
## Core Concept Questions

### Pipeline Integration:

#### Why is it important to run Trivy scans (for OS packages and dependencies) as part of the CI/CD pipeline instead of only scanning after deployment?
- Running Trivy early in the CI/CD process aligns with the "shift left" security principle — catching vulnerabilities before code is deployed.
- Early detection means vulnerabilities in base images, OS packages, or third-party libraries are identified before they ever reach production.
- Fixing issues at this stage is faster, cheaper, and less disruptive than patching live systems.
- By integrating Trivy in CI/CD, you can fail the build if severe vulnerabilities (e.g., HIGH or CRITICAL) are detected.
- This enforces security gates automatically, ensuring only compliant and secure images are deployed.
- Without this step, insecure images could get deployed and require emergency patches later.

#### Why is it important to run security scans (SAST, dependency scanning, DAST) directly in the CI/CD pipeline instead of only during production?
- Running scans in CI/CD moves security checks earlier in the development lifecycle (“shift-left” approach).
- Vulnerabilities are identified before code is deployed, reducing the risk of exposing them to production.
- Scans integrated into CI/CD provide immediate feedback.
- Developers can fix issues while the context is fresh, leading to faster and more accurate remediation.
- SAST, dependency scanning, and DAST in CI/CD act as gates to block builds with high-risk issues.
- This ensures that only secure and compliant artifacts are deployed.

### Tool Roles:

#### How do Bandit, Semgrep, Trivy, and OWASP ZAP complement each other in the pipeline? Give one example of what each tool detects that the others do not.
- Semgrep – General Static Application Security Testing (SAST)
  - Detects insecure code patterns, logic errors, and secrets across multiple languages.
    - Hardcoded API keys in Java or JavaScript files.

- Trivy – Dependency and Container Scanning
  - Scans Docker images, OS packages, and language dependencies for known CVEs.
    - A vulnerable version of openssl in a Docker base image.

- OWASP ZAP – Dynamic Application Security Testing (DAST)
  - Tests the running application for web vulnerabilities.
    - Cross-Site Scripting (XSS) in a web form.

### Developer Actionability:

#### If Trivy reports a HIGH severity vulnerability in a base image or Bandit flags hardcoded secrets, what should the developer or DevOps engineer do next?
- Trivy – HIGH Severity Vulnerability in a Base Image or Dependency
  - Check the CVE ID and impact on your app and environment.
  - Switch to a patched base image or update the affected package.
  - Rebuild the Docker image.
  - Re-run Trivy to confirm the vulnerability is resolved.
  - Prevent deployment of insecure images by enforcing CI/CD gates on HIGH/CRITICAL vulnerabilities.
- Bandit – Hardcoded Secrets Detected
  - Confirm if the detected secret is sensitive (API keys, credentials, tokens).
  - Replace with environment variables or a secret management tool (e.g., AWS Secrets Manager, GitHub Secrets, Vault).
  - If the secret was committed to Git, rotate it immediately to prevent misuse.
  - Re-run Bandit to ensure the secret is no longer present and test the application with the secured secret management approach.
