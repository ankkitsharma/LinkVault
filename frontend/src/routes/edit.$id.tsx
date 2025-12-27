import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { useState, useEffect } from "react";
import { bookmarkApi } from "../services/api";
import { Bookmark } from "../types/bookmark";

export const Route = createFileRoute("/edit/$id")({
  component: EditBookmark,
});

function EditBookmark() {
  const { id } = Route.useParams();
  const navigate = useNavigate();
  const [bookmark, setBookmark] = useState<Bookmark | null>(null);
  const [formData, setFormData] = useState({
    title: "",
    url: "",
    description: "",
  });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadBookmark();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [id]);

  const loadBookmark = async () => {
    try {
      setLoading(true);
      const data = await bookmarkApi.getById(Number(id));
      setBookmark(data);
      setFormData({
        title: data.title,
        url: data.url,
        description: data.description || "",
      });
    } catch (error) {
      console.error("Error loading bookmark:", error);
      alert("Failed to load bookmark");
      navigate({ to: "/" });
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!formData.title || !formData.url) {
      alert("Title and URL are required");
      return;
    }
    try {
      setLoading(true);
      await bookmarkApi.update(Number(id), formData);
      navigate({ to: "/" });
    } catch (error) {
      console.error("Error updating bookmark:", error);
      alert("Failed to update bookmark");
    } finally {
      setLoading(false);
    }
  };

  if (loading && !bookmark) {
    return (
      <div className="container">
        <div className="card">Loading...</div>
      </div>
    );
  }

  return (
    <div>
      <div className="header">
        <div className="header-content">
          <h1>Edit Bookmark</h1>
          <button
            className="btn btn-secondary"
            onClick={() => navigate({ to: "/" })}
          >
            Back
          </button>
        </div>
      </div>
      <div className="container">
        <div className="card">
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Title *</label>
              <input
                type="text"
                value={formData.title}
                onChange={(e) =>
                  setFormData({ ...formData, title: e.target.value })
                }
                required
              />
            </div>
            <div className="form-group">
              <label>URL *</label>
              <input
                type="url"
                value={formData.url}
                onChange={(e) =>
                  setFormData({ ...formData, url: e.target.value })
                }
                required
              />
            </div>
            <div className="form-group">
              <label>Description</label>
              <textarea
                value={formData.description}
                onChange={(e) =>
                  setFormData({ ...formData, description: e.target.value })
                }
              />
            </div>
            <div style={{ display: "flex", gap: "10px" }}>
              <button
                type="submit"
                className="btn btn-primary"
                disabled={loading}
              >
                {loading ? "Updating..." : "Update Bookmark"}
              </button>
              <button
                type="button"
                className="btn btn-secondary"
                onClick={() => navigate({ to: "/" })}
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
