# Development guidelines

This repository requires a proxy to download Maven dependencies.
Always run Maven commands with the provided `settings.xml` file:

```bash
mvn -s settings.xml -DskipTests package
```

Use four spaces for Java indentation and update the README whenever the build or configuration changes.
