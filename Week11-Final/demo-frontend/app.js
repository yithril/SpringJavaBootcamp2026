// Configuration
const API_BASE_URL = 'http://localhost:8080/api';

// State
let internships = [];
let candidates = [];

// Initialize app when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    loadInternships();
    loadCandidates();
    checkBackendStatus();
    
    // Check backend status every 30 seconds
    setInterval(checkBackendStatus, 30000);
});

// ============================================
// API Functions
// ============================================

async function checkBackendStatus() {
    const statusDot = document.getElementById('backend-status');
    try {
        const response = await fetch(`${API_BASE_URL}/internships`);
        if (response.ok) {
            statusDot.classList.remove('offline');
            statusDot.title = 'Backend connected';
        } else {
            statusDot.classList.add('offline');
            statusDot.title = 'Backend error';
        }
    } catch (error) {
        statusDot.classList.add('offline');
        statusDot.title = 'Backend offline';
    }
}

async function loadInternships() {
    const loading = document.getElementById('loading');
    const error = document.getElementById('error');
    const emptyState = document.getElementById('empty-state');
    const grid = document.getElementById('internships-grid');

    // Show loading
    loading.style.display = 'block';
    error.style.display = 'none';
    emptyState.style.display = 'none';
    grid.innerHTML = '';

    try {
        const response = await fetch(`${API_BASE_URL}/internships`);
        
        if (!response.ok) {
            throw new Error('Failed to fetch internships');
        }

        internships = await response.json();
        
        // Hide loading
        loading.style.display = 'none';

        if (internships.length === 0) {
            emptyState.style.display = 'block';
        } else {
            renderInternships();
        }
    } catch (err) {
        console.error('Error loading internships:', err);
        loading.style.display = 'none';
        error.style.display = 'block';
    }
}

async function loadCandidates() {
    const list = document.getElementById('candidates-list');
    
    try {
        const response = await fetch(`${API_BASE_URL}/candidates`);
        
        if (!response.ok) {
            throw new Error('Failed to fetch candidates');
        }

        candidates = await response.json();
        renderCandidates();
    } catch (err) {
        console.error('Error loading candidates:', err);
        list.innerHTML = '<p style="color: var(--text-light); text-align: center;">Could not load candidates</p>';
    }
}

async function createInternship(event) {
    event.preventDefault();
    
    const form = event.target;
    const formData = new FormData(form);
    
    const internship = {
        title: formData.get('title'),
        company: formData.get('company'),
        location: formData.get('location'),
        description: formData.get('description'),
        published: formData.get('published') === 'on'
    };

    try {
        const response = await fetch(`${API_BASE_URL}/internships`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(internship)
        });

        if (!response.ok) {
            throw new Error('Failed to create internship');
        }

        // Success!
        form.reset();
        closeCreateModal();
        loadInternships();
        showNotification('Internship created successfully! 🎉', 'success');
    } catch (err) {
        console.error('Error creating internship:', err);
        showNotification('Failed to create internship. Check console for details.', 'error');
    }
}

async function deleteInternship(id) {
    if (!confirm('Are you sure you want to delete this internship?')) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/internships/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error('Failed to delete internship');
        }

        loadInternships();
        showNotification('Internship deleted successfully', 'success');
    } catch (err) {
        console.error('Error deleting internship:', err);
        showNotification('Failed to delete internship', 'error');
    }
}

async function createCandidate(event) {
    event.preventDefault();
    
    const form = event.target;
    const formData = new FormData(form);
    
    const candidate = {
        name: formData.get('name'),
        email: formData.get('email'),
        fieldOfStudy: formData.get('fieldOfStudy')
    };

    try {
        const response = await fetch(`${API_BASE_URL}/candidates`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(candidate)
        });

        if (!response.ok) {
            throw new Error('Failed to create candidate');
        }

        form.reset();
        closeCandidateModal();
        loadCandidates();
        showNotification('Candidate added successfully! 👥', 'success');
    } catch (err) {
        console.error('Error creating candidate:', err);
        showNotification('Failed to add candidate', 'error');
    }
}

async function deleteCandidate(id) {
    if (!confirm('Are you sure you want to delete this candidate?')) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/candidates/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error('Failed to delete candidate');
        }

        loadCandidates();
        showNotification('Candidate deleted successfully', 'success');
    } catch (err) {
        console.error('Error deleting candidate:', err);
        showNotification('Failed to delete candidate', 'error');
    }
}

// ============================================
// Render Functions
// ============================================

function renderInternships() {
    const grid = document.getElementById('internships-grid');
    
    grid.innerHTML = internships.map(internship => `
        <div class="internship-card">
            <h3>${escapeHtml(internship.title)}</h3>
            <div class="company">🏢 ${escapeHtml(internship.company)}</div>
            <div class="location">📍 ${escapeHtml(internship.location)}</div>
            <p class="description">${escapeHtml(internship.description)}</p>
            <div class="card-footer">
                <span class="badge ${internship.published ? 'badge-published' : 'badge-draft'}">
                    ${internship.published ? '✓ Published' : '📝 Draft'}
                </span>
                <button class="btn btn-delete" onclick="deleteInternship(${internship.id})">
                    🗑️ Delete
                </button>
            </div>
        </div>
    `).join('');
}

function renderCandidates() {
    const list = document.getElementById('candidates-list');
    
    if (candidates.length === 0) {
        list.innerHTML = '<p style="color: var(--text-light); text-align: center; padding: 20px;">No candidates yet</p>';
        return;
    }
    
    list.innerHTML = candidates.map(candidate => `
        <div class="candidate-card">
            <div class="candidate-info">
                <h4>${escapeHtml(candidate.name)}</h4>
                <div class="candidate-email">📧 ${escapeHtml(candidate.email)}</div>
                <div class="candidate-field">🎓 ${escapeHtml(candidate.fieldOfStudy)}</div>
            </div>
            <button class="btn btn-delete" onclick="deleteCandidate(${candidate.id})">
                🗑️
            </button>
        </div>
    `).join('');
}

// ============================================
// Modal Functions
// ============================================

function openCreateModal() {
    document.getElementById('create-modal').classList.add('active');
}

function closeCreateModal() {
    document.getElementById('create-modal').classList.remove('active');
    document.getElementById('create-form').reset();
}

function openCandidateModal() {
    document.getElementById('candidate-modal').classList.add('active');
}

function closeCandidateModal() {
    document.getElementById('candidate-modal').classList.remove('active');
    document.getElementById('candidate-form').reset();
}

// Close modal when clicking outside
window.onclick = function(event) {
    const createModal = document.getElementById('create-modal');
    const candidateModal = document.getElementById('candidate-modal');
    
    if (event.target === createModal) {
        closeCreateModal();
    }
    if (event.target === candidateModal) {
        closeCandidateModal();
    }
}

// ============================================
// Utility Functions
// ============================================

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function showNotification(message, type = 'success') {
    // Create notification element
    const notification = document.createElement('div');
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 16px 24px;
        background: ${type === 'success' ? '#4caf50' : '#f44336'};
        color: white;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.3);
        z-index: 10000;
        animation: slideInRight 0.3s ease;
        font-weight: 600;
    `;
    notification.textContent = message;
    
    document.body.appendChild(notification);
    
    // Remove after 3 seconds
    setTimeout(() => {
        notification.style.animation = 'slideOutRight 0.3s ease';
        setTimeout(() => notification.remove(), 300);
    }, 3000);
}

// Add notification animations
const style = document.createElement('style');
style.textContent = `
    @keyframes slideInRight {
        from {
            transform: translateX(400px);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    @keyframes slideOutRight {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(400px);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);
