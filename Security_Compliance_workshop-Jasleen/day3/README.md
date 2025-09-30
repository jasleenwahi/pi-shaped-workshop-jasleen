## Core Concept Questions

### What is the difference between SAST, DAST, and secrets scanning, and why should all be part of a CI/CD pipeline?

**SAST - Static Application Security Testing**
- Analyzes your source code, bytecode, or binaries without running the application.
- Looks for vulnerabilities like:
  - SQL Injection, XSS
  - Hardcoded credentials
  - Insecure cryptography
  - Unsafe deserialization
- Catches issues early in development (“shift-left”).

**DAST - Dynamic Application Security Testing**
- Tests the running application by sending HTTP requests and analyzing responses.
- Simulates an attacker hitting your app from the outside.
- Detects vulnerabilities like:
  - Missing security headers
  - Cross-Site Scripting (XSS)
  - SQL Injection in live endpoints
  - Authentication flaws
- Finds runtime/configuration issues that SAST can’t see.

**Secrets Scanning**
- Detects hardcoded secrets like:
  - API keys
  - Database passwords
  - Certificates and tokens
- Prevents credential leaks that could compromise entire environments.

### Why is storing secrets in code dangerous? What’s a secure alternative?

- Pushing secrets to GitHub, GitLab, or other version control systems can make them publicly visible.
- Even deleted secrets may remain in Git history, which attackers can retrieve.
- Changing a secret requires updating code everywhere it’s used, which is error-prone.
- Multiple developers or CI/CD agents may get access to the secret unnecessarily.
- Attackers can use leaked credentials to access databases, cloud resources, or third-party APIs.
- Leads to data breaches, financial loss, or service compromise.

**Secure Alternatives**
- environment variables,
- secret managers or vaults
- encrypted configuration files

### How does adding these scans to a pipeline help enforce Shift-Left Security?
- Detects issues early
  - Developers fix issues before they reach production, reducing cost and effort

- Automates Security Checks
  - Integrating scans into CI/CD ensures that every commit and pull request is automatically checked.
  - No reliance on manual code reviews alone, which can miss subtle vulnerabilities.

### If a scan fails in your pipeline, what is the next step for a developer or DevOps engineer?
- Review the Scan Report
  - Identify:
    - Severity (High, Medium, Low)
    - Type of issue (SQL injection, hardcoded secret, insecure configuration)
    - Location in code or endpoint

- Prioritize the Issue
  - Block the merge or deployment immediately.
  - Evaluate if fix is urgent or can be scheduled.
  - Document and optionally suppress if validated.

- Fix the Issue
  - Refactor code to remove unsafe patterns.
  - Remove hardcoded secrets and use environment variables or secret manager.
  - Fix insecure endpoints, headers, TLS settings.

- Re-run the pipeline to ensure the vulnerability is removed.