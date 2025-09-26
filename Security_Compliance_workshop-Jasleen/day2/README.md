### 2 OWASP Top 10 vulnerabilities, their impact and fix

1. **Insufficient Site Isolation Against Spectre Vulnerability**
- Impact
  - Sensitive information like, Passwords stored in memory, Authentication tokens or cookies, Form inputs or personal data
  - Can be exfiltrated by a malicious website running in the same browser session.
  - This is primarily a client-side vulnerability, so it doesn’t directly compromise your server code, but your users’ data could be at risk.

- Fix
  - Enable strict site isolation in browsers
    - For Chrome: Go to chrome://flags/#site-isolation-trial-opt-in or enable “Strict Site Isolation” in settings.
  - Keep browsers and operating systems up-to-date
    - Vendors regularly release patches to mitigate Spectre/Meltdown vulnerabilities.
  

2. **Vulnerability: X-Content-Type-Options Header Missing**
- Impact
  - Malicious content could execute in the user’s browser.
  - Attackers could trick users into executing malicious scripts by serving files with the wrong MIME type.
  - If scripts execute unexpectedly, sensitive data might be exposed.

- Fix
  - Add the X-Content-Type-Options header to all HTTP responses. 
  - Recommended value: nosniff

### What is the purpose of DAST and how does it complement other security testing methods?
DAST, or Dynamic Application Security Testing, is like a security guard that checks your app while it’s running. 
It scans the app from the outside to find vulnerabilities such as missing security settings or weak spots that could be attacked. 
Unlike checking the code directly, DAST looks at how the app behaves in real-time. 

DAST works alongside other tests like Static Application Security Testing (SAST), 
which checks the code before it runs, and Software Composition Analysis (SCA), which looks at third-party libraries. 
Together, they cover all angles—SAST finds issues in the code, SCA spots library risks, 
and DAST catches runtime problems. This teamwork ensures a stronger, more secure app!

### Explain how XSS or SQL injection vulnerabilities can affect an application and its users.
XSS (Cross-Site Scripting): This happens when an attacker sneaks malicious scripts (like JavaScript) into your app, 
often through user input (e.g., a search bar). If not stopped, it can steal users’ cookies (which hold login info), 
redirect them to fake websites to steal data, or mess up the app’s look. For users, this means their personal info (like passwords) could be stolen, 
and they might not even know!

SQL Injection: This occurs when an attacker adds harmful SQL code (e.g., via a login form) to trick the app into revealing or changing data in the database. 
It can expose all user data, delete records, or even let the attacker take over the app. Users might lose their accounts, and the app’s data could be wiped out or sold on the dark web.

### Describe the steps you would take to fix the vulnerabilities detected in your ZAP scan.
**Add Security Headers**
- Use tools like flask_talisman or manually add headers (e.g., Content-Security-Policy, X-Frame-Options) to block XSS and clickjacking. 
This tells browsers how to protect the app.

**Hide Server Info**
- Change or remove the Server header (e.g., using a custom WSGI app) to hide version details like Werkzeug/3.1.3, 
making it harder for attackers to target specific flaws.

**Fix Authentication**
- Add rate limiting (e.g., with flask_limiter) to stop brute force attacks on /login, and add delays for failed attempts to slow attackers down.

**Patch Local Vulnerabilities**
- For XSS, use markupsafe.escape() to clean user input on /search. For SQLi, switch to parameterized queries in database calls to prevent code injection.

**Test and Deploy**
- Re-run the ZAP scan to confirm fixes work, then update the app in production.

### How does integrating ZAP scans into CI/CD pipelines support shift-left security practices?
**What is Shift-Left?**: Shift-left means moving security checks to the beginning of the development process, not just at the end. 
It’s like fixing a leaky pipe as soon as you see a drip, not after the house floods!

**How ZAP Helps**: By adding ZAP scans to the CI/CD pipeline (e.g., on every push to main), we catch security issues early—while coding or testing—rather than after deployment. 
This saves time and money, as fixing bugs later is harder and costlier.
Benefits: Developers get instant feedback (e.g., 8 warnings in our scan), can fix problems before release, and build a habit of secure coding.
It’s like having a safety net that catches issues before they reach users!