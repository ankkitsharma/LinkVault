import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { useState, useEffect } from "react";
import { bookmarkApi } from "../services/api";
import { Bookmark } from "../types/bookmark";

export const Route = createFileRoute("/")({
  component: Index,
});

function Index() {
  const [bookmarks, setBookmarks] = useState<Bookmark[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    loadBookmarks();
  }, []);

  const loadBookmarks = async () => {
    try {
      setLoading(true);
      const data = await bookmarkApi.getAll();
      setBookmarks(data);
    } catch (error) {
      console.error("Error loading bookmarks:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!searchQuery.trim()) {
      loadBookmarks();
      return;
    }
    try {
      setLoading(true);
      const results = await bookmarkApi.search(searchQuery);
      setBookmarks(results);
    } catch (error) {
      console.error("Error searching bookmarks:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm("Are you sure you want to delete this bookmark?")) {
      return;
    }
    try {
      await bookmarkApi.delete(id);
      setBookmarks(bookmarks.filter((b) => b.id !== id));
    } catch (error) {
      console.error("Error deleting bookmark:", error);
      alert("Failed to delete bookmark");
    }
  };

  return (
    <div>
      <div className="header">
        <div className="header-content">
          <h1>Bookmark Manager</h1>
          <button
            className="btn btn-primary"
            onClick={() => navigate({ to: "/add" })}
          >
            Add Bookmark
          </button>
        </div>
      </div>
      <div className="container">
        <div className="card">
          <form onSubmit={handleSearch}>
            <input
              type="text"
              className="search-box"
              placeholder="Search bookmarks..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
          </form>
        </div>

        {loading ? (
          <div className="card">Loading...</div>
        ) : bookmarks.length === 0 ? (
          <div className="card">
            No bookmarks found. Add your first bookmark!
          </div>
        ) : (
          <div className="card">
            {bookmarks.map((bookmark) => (
              <div key={bookmark.id} className="bookmark-item">
                <div className="bookmark-content">
                  <div className="bookmark-title">
                    <a
                      href={bookmark.url}
                      target="_blank"
                      rel="noopener noreferrer"
                    >
                      {bookmark.title}
                    </a>
                  </div>
                  <div className="bookmark-url">{bookmark.url}</div>
                  {bookmark.description && (
                    <div className="bookmark-description">
                      {bookmark.description}
                    </div>
                  )}
                </div>
                <div className="bookmark-actions">
                  <button
                    className="btn btn-secondary"
                    onClick={() => navigate({ to: `/edit/${bookmark.id}` })}
                  >
                    Edit
                  </button>
                  <button
                    className="btn btn-danger"
                    onClick={() => handleDelete(bookmark.id!)}
                  >
                    Delete
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
