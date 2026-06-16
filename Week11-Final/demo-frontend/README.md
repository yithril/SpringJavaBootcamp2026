## Quick Start

### Prerequisites
- The Spring Boot backend must be running on http://localhost:8080
- A modern web browser

### Running the Frontend

**Option 1: Simple File Open**
```bash
# Just open the index.html file in your browser
open index.html  # macOS
start index.html  # Windows
xdg-open index.html  # Linux
```

**Option 2: Local Web Server (Recommended)**

Using Python:
```bash
# Python 3
python -m http.server 8000

# Then open: http://localhost:8000
```

Using Node.js:
```bash
# Install live-server globally (one time)
npm install -g live-server

# Run
live-server
```

Using PHP:
```bash
php -S localhost:8000
```

## 🔗 Backend Connection

The frontend connects to the Spring Boot backend at:
```javascript
const API_BASE_URL = 'http://localhost:8080/api';
```

### Endpoints Used

**Internships:**
- `GET /api/internships` - Load all internships
- `POST /api/internships` - Create new internship
- `DELETE /api/internships/{id}` - Delete internship

**Candidates:**
- `GET /api/candidates` - Load all candidates
- `POST /api/candidates` - Create new candidate
- `DELETE /api/candidates/{id}` - Delete candidate
