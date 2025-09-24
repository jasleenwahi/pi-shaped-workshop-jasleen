### How gitleaks was configured and run?

- Installed the correct release binary of gitleaks, extracted it and placed it in **/usr/local/bin**, Ran following commands.
> curl -LO https://github.com/gitleaks/gitleaks/releases/download/v8.28.0/gitleaks_8.28.0_linux_x64.tar.gz

> tar -xzvf gitleaks_8.28.0_linux_x64.tar.gz

> chmod +x gitleaks

> sudo mv gitleaks /usr/local/bin/gitleaks

- Verified with:
> gitleaks version

- Commited my changes, and then ran the following command:
> gitleaks detect

It detects for any secret leaks in staged files and commit history.

- Generated the report using the following command.
> gitleaks detect --report-path=gitleaks-report.json --report-format=json

- By default, Gitleaks uses its built-in rules (for AWS keys, GitHub tokens, Azure Storage keys, etc.).
- Created a **gitleaks.toml** file to scan **spring.datasource.password** because gitleaks doesn't scan it by default.
- Re-scanned after, by running this command:
> gitleaks detect --config .gitleaks.toml

### Shift left Security and it's importance

The practice of integrating security measure earlier in the **SDLC** rather than waiting until 
end.

- Fixing security issues early is cheaper and faster than fixing them post-release.
- Prevents serious security incidents by integrating protection from the beginning.
- fixing a vulnerability in production can be more expensive than fixing it during development.
- By embedding security in the process, releases can happen faster without last-minute security emergencies.
- Developers become more security-conscious, leading to better coding practices overall.

### How does detecting secrets early in the CI/CD pipeline prevent production vulnerabilities?

- No sensitive credentials make it to production or public repositories.
- Attackers cannot exploit hard-coded secrets.
- Encourages use of environment variables, secret managers, or vaults.

### What strategies can be used to store secrets securely instead of hardcoding them?

- environment variables, 
- secret managers or vaults 
- encrypted configuration files

### Describe a situation where a secret could still be exposed even after scanning, and how to prevent it.

- Custom secrets can still be exposed, define a .toml file to prevent exposing them.


