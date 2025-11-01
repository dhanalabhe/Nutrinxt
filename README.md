# 🥗 Nutrinxt — Smart Health & Diet Tracking App

**Nutrinxt** is a modern **JavaFX-based health and nutrition tracking application** that helps users monitor their daily calorie intake, macronutrients, and dietary progress through an interactive and visually engaging dashboard.

---

## 🧠 Overview

Nutrinxt allows users to:
- Log daily meals and automatically calculate calories.
- Track macronutrients such as protein, carbs, and fats.
- Visualize daily progress with interactive charts and progress rings.
- Manage meal history and personalized nutrition goals.
- Securely store user data with Firebase integration.

---

## 🖥️ Tech Stack

| Category | Technology |
|-----------|-------------|
| **Frontend** | JavaFX, FXML, CSS |
| **Backend** | Java 17+, Firebase |
| **Build Tool** | Maven |
| **Database** | Firebase Realtime Database |
| **Version Control** | Git, GitHub |

---

## ⚙️ Installation & Setup

### Prerequisites
- Java JDK 17 or higher  
- Maven installed and configured  
- Firebase project (for authentication & data storage)

### Steps
```bash
# 1. Clone the repository
git clone https://github.com/dhanalabhe/Nutrinxt.git

# 2. Navigate to the project directory
cd Nutrinxt

# 3. Build the project

<!-- Firebase Configuration -->
<section>
  <h3>🔐 Firebase Configuration</h3>
  <ol>
    <li>Create a Firebase project from the <a href="https://console.firebase.google.com/" target="_blank" rel="noopener">Firebase Console</a>.</li>
    <li>Generate a service account key and download the <code>serviceAccountKey.json</code>.</li>
    <li>
      Place the service account file somewhere safe on your machine (for example: <code>config/serviceAccountKey.json</code>).
      <strong>Do not commit it to the repository.</strong>
    </li>
    <li>Update the Firebase credentials path in your application code or load from an environment variable.</li>
  </ol>

  <blockquote>
    <strong>⚠️ Important:</strong> Ensure your <code>.gitignore</code> excludes credential and build files, for example:
    <ul>
      <li><code>target/</code></li>
      <li><code>*.json</code> (Firebase keys)</li>
      <li><code>.idea/</code></li>
      <li><code>*.log</code></li>
    </ul>
  </blockquote>
</section>

<!-- Project Structure -->
<section>
  <h3>🧩 Project Structure</h3>

  <details>
    <summary>Click to expand project tree</summary>

    <pre><code>
Nutrinxt/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── nutrinxt/
│   │   │       ├── controllers/
│   │   │       ├── models/
│   │   │       └── utils/
│   │   └── resources/
│   │       ├── fxml/
│   │       ├── css/
│   │       └── images/
├── target/
├── pom.xml
└── README.md
    </code></pre>
  </details>
</section>

<!-- UI Highlights -->
<section>
  <h3>🎨 UI Highlights</h3>
  <ul>
    <li>Dashboard-based layout</li>
    <li>Scrollable cards for meals and nutrients</li>
    <li>Animated progress indicators</li>
    <li>Clean and minimalist design</li>
  </ul>
</section>

<!-- Author -->
<section>
  <h3>🧑‍💻 Author</h3>
  <p><strong>Dhanashree Labhe</strong><br>
     🎓 Engineering Student | 💻 Software Developer</p>
  <p>
    🔗 <a href="https://github.com/dhanalabhe" target="_blank" rel="noopener">GitHub Profile</a><br>
    📧 Open to collaborations and internship opportunities.
  </p>
</section>

<!-- Contributing -->
<section>
  <h3>🌟 Contributing</h3>
  <p>Contributions are welcome! If you’d like to improve <strong>Nutrinxt</strong>, please:</p>
  <ol>
    <li>Fork the repository</li>
    <li>Create a feature branch (<code>feature/your-feature</code>)</li>
    <li>Open a pull request with a clear description and tests (if applicable)</li>
  </ol>
</section>

<!-- License -->
<section>
  <h3>📜 License</h3>
  <p>This project is licensed under the <strong>MIT License</strong> — feel free to use and modify with attribution.</p>
</section>

mvn clean install

