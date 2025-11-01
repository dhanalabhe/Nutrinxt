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
mvn clean install

🔐 Firebase Configuration

Create a Firebase project from the Firebase Console
.

Generate a service account key and download the serviceAccountKey.json.

Place it in the src/main/resources/ directory (but do not commit it to Git).

Update Firebase credentials in your project configuration.

⚠️ Ensure your .gitignore excludes all credential and build files.

🧩 Project Structure
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

🎨 UI Highlights

Dashboard-based layout

Scrollable cards for meals and nutrients

Animated progress indicators

Clean and minimalist design

🧑‍💻 Author

Dhanashree Labhe
🎓 Engineering Student | 💻 Software Developer 

🔗 GitHub

📧 Open to collaborations and internship opportunities.

🌟 Contributing

Contributions are welcome!
If you’d like to improve Nutrinxt, please fork the repo and submit a pull request.

📜 License

This project is licensed under the MIT License — feel free to use and modify with attribution.
# 4. Run the application
mvn javafx:run
