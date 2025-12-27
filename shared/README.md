# @bookmark/shared

Shared TypeScript types and utilities for the bookmark application.

## Overview

This package contains TypeScript types that are auto-generated from the backend's OpenAPI specification. It ensures type safety and consistency between the frontend and backend.

## Contents

- `api-types.ts` - Auto-generated TypeScript types from OpenAPI spec
- `types.ts` - Convenient type aliases for easier usage

## Usage

The package is automatically linked as a dependency in the frontend:

```typescript
import type { Bookmark, BookmarkRequest } from "@bookmark/shared";
```

## Generating Types

Types are generated from the backend's OpenAPI specification. See the main README for instructions on running the type generation script.

## Package Structure

```
shared/
├── package.json      # Package configuration
├── tsconfig.json     # TypeScript configuration
├── api-types.ts      # Auto-generated OpenAPI types
└── types.ts          # Type aliases and exports
```

