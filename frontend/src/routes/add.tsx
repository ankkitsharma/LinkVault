import { createFileRoute, useNavigate } from '@tanstack/react-router'
import { useState } from 'react'
import { bookmarkApi } from '../services/api'

export const Route = createFileRoute('/add')({
  component: AddBookmark,
})

function AddBookmark() {
  const navigate = useNavigate()
  const [formData, setFormData] = useState({
    title: '',
    url: '',
    description: '',
  })
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    if (!formData.title || !formData.url) {
      alert('Title and URL are required')
      return
    }
    try {
      setLoading(true)
      await bookmarkApi.create(formData)
      navigate({ to: '/' })
    } catch (error) {
      console.error('Error creating bookmark:', error)
      alert('Failed to create bookmark')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div>
      <div className="header">
        <div className="header-content">
          <h1>Add Bookmark</h1>
          <button className="btn btn-secondary" onClick={() => navigate({ to: '/' })}>
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
                onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label>URL *</label>
              <input
                type="url"
                value={formData.url}
                onChange={(e) => setFormData({ ...formData, url: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label>Description</label>
              <textarea
                value={formData.description}
                onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              />
            </div>
            <div style={{ display: 'flex', gap: '10px' }}>
              <button type="submit" className="btn btn-primary" disabled={loading}>
                {loading ? 'Creating...' : 'Create Bookmark'}
              </button>
              <button
                type="button"
                className="btn btn-secondary"
                onClick={() => navigate({ to: '/' })}
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  )
}

