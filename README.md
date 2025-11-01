# 🥗 Nutrinxt — Smart Health & Diet Tracking App

Nutrinxt is a modern JavaFX-based health and nutrition tracking application that helps users monitor their daily calorie intake, macronutrients, and dietary progress through an interactive and visually engaging dashboard.

---

## 🧠 Overview

Nutrinxt allows users to:
- 🍽️ Log daily meals and automatically calculate calories
- 💪 Track macronutrients — protein, carbs, and fats
- 📊 Visualize daily progress with interactive charts and progress rings
- 🕒 Manage meal history and personalized nutrition goals
- 🔐 Securely store user data with Firebase integration

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
- ☕ Java JDK 17 or higher
- 🧩 Maven installed and configured
- 🔥 Firebase project (for authentication & data storage)

### Steps

1. Clone the repository
   ```bash
   git clone https://github.com/dhanalabhe/Nutrinxt.git
   ```

2. Navigate to the project directory
   ```bash
   cd Nutrinxt
   ```

3. Build the project
   ```bash
   mvn clean install
   ```

4. Run the application
   ```bash
   mvn javafx:run
   ```

---

## 🔐 Firebase Configuration

1. Create a Firebase project from the Firebase Console
2. Generate a Service Account Key and download it as `serviceAccountKey.json`
3. Place it in: `src/main/resources/`
4. ⚠️ Do not commit your Firebase key to GitHub — ensure it’s listed in your `.gitignore`

---

## ⚠️ Important

Your `.gitignore` file should exclude all sensitive and build files such as:

```
target/
*.json   # Firebase keys
.idea/
*.log
```

---

## 🧩 Project Structure

```
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
```

---

## 🎨 UI Highlights

- 📊 Dashboard-based layout for intuitive tracking
- 🍱 Scrollable cards for meals and nutrients
- 🔄 Animated progress indicators
- 🧼 Clean, minimalist, and responsive design

---

## 👩‍💻 Author

**Dhanashree Labhe**  
🎓 Engineering Student | 💻 Software Developer  
📧 Open to collaborations and internship opportunities.

---

## 🌟 Contributing

Contributions are always welcome!  
To contribute:
1. Fork this repository
2. Create a new branch (`feature/your-feature-name`)
3. Commit your changes
4. Submit a pull request

---

## 📜 License

This project is licensed under the MIT License —  
Feel free to use, modify, and share with proper attribution.
