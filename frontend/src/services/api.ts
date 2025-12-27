import type { Bookmark, BookmarkRequest } from "@bookmark/shared";

const API_BASE_URL = import.meta.env.VITE_API_URL || "/api";

export const bookmarkApi = {
  getAll: async (): Promise<Bookmark[]> => {
    const response = await fetch(`${API_BASE_URL}/bookmarks`);
    if (!response.ok) {
      throw new Error("Failed to fetch bookmarks");
    }
    return response.json();
  },

  getById: async (id: number): Promise<Bookmark> => {
    const response = await fetch(`${API_BASE_URL}/bookmarks/${id}`);
    if (!response.ok) {
      throw new Error("Failed to fetch bookmark");
    }
    return response.json();
  },

  create: async (bookmark: BookmarkRequest): Promise<Bookmark> => {
    const response = await fetch(`${API_BASE_URL}/bookmarks`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(bookmark),
    });
    if (!response.ok) {
      throw new Error("Failed to create bookmark");
    }
    return response.json();
  },

  update: async (id: number, bookmark: BookmarkRequest): Promise<Bookmark> => {
    const response = await fetch(`${API_BASE_URL}/bookmarks/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(bookmark),
    });
    if (!response.ok) {
      throw new Error("Failed to update bookmark");
    }
    return response.json();
  },

  delete: async (id: number): Promise<void> => {
    const response = await fetch(`${API_BASE_URL}/bookmarks/${id}`, {
      method: "DELETE",
    });
    if (!response.ok) {
      throw new Error("Failed to delete bookmark");
    }
  },

  search: async (query: string): Promise<Bookmark[]> => {
    const response = await fetch(
      `${API_BASE_URL}/bookmarks/search?q=${encodeURIComponent(query)}`
    );
    if (!response.ok) {
      throw new Error("Failed to search bookmarks");
    }
    return response.json();
  },
};
