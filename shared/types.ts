/**
 * Type aliases for easier usage of generated OpenAPI types
 * These are re-exported from the auto-generated api-types.ts file
 */

import type { components } from "./api-types";

// Re-export the DTOs with simpler names
export type Bookmark = components["schemas"]["BookmarkResponseDTO"];
export type BookmarkRequest = components["schemas"]["BookmarkRequestDTO"];
export type BookmarkResponse = components["schemas"]["BookmarkResponseDTO"];

// Export the full types object for advanced usage
export type { components, paths, operations } from "./api-types";
