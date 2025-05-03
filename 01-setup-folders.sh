#!/bin/bash

# Top-level Maven modules
modules=(
   "ingest-app-main",
    "ingest-common"
    "ingest-teacher"
    "ingest-student"
    "ingest-spring-batch-teacher"
    "ingest-spring-batch-student"
    "ingest-spring-batch-app"
)

# Create Maven module folders and pom.xml
echo "Creating Maven modules with pom.xml files..."
for module in "${modules[@]}"; do
  mkdir -p "$module/src/main/java"
  mkdir -p "$module/src/main/resources"
  mkdir -p "$module/src/test/java"
  touch "$module/pom.xml"
  echo "✔ Created $module with pom.xml"
done

# DevOps folders
echo "Creating DevOps and tooling folders..."
mkdir -p devops/local-dev
mkdir -p local-dev/__files
mkdir -p local-dev/mappings
mkdir -p docs/observability

# Package.json placeholder
touch package.json

echo "Folder structure and pom.xml files generated."
