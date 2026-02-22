# 🎯 Job Hunter - Android App

**Job Hunter** is a modern, high-performance Android application designed to streamline the job searching process. Built with **Kotlin** and following the latest Android development best practices, it demonstrates a robust implementation of **Clean Architecture** and **Modern UI** principles.

---

## 🛠 Tech Stack & Tools

- **Language:** [Kotlin](https://kotlinlang.org/) - 100%
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) - Declarative UI for modern Android development.
- **Architecture:** Clean Architecture with **MVVM** (Model-View-ViewModel) pattern.
- **Asynchronous Programming:** [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html) for reactive data streams.
- **Dependency Injection:** [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - For scalable and testable code.
- **Networking:** [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) for REST API communication.
- **Local Storage:** [Room Database](https://developer.android.com/training/data-storage/room) - Offline-first support and caching.
- **Image Loading:** [Coil](https://coil-kt.github.io/coil/) - Kotlin-first image loading library.

---

## ✨ Key Features

- **Real-time Job Search:** Search and filter jobs from multiple sources.
- **Offline Mode:** Browse previously viewed jobs without an internet connection using Room caching.
- **Modern Navigation:** Implemented using **Jetpack Navigation Compose**.
- **Responsive Design:** Supports various screen sizes with a polished Material 3 UI.
- **Dark Mode Support:** Fully adaptive UI for light and dark themes.

---

## 🏗 Architecture Overview

The project follows **Clean Architecture** principles to ensure separation of concerns, testability, and maintainability:

1. **Domain Layer:** Contains Business Logic, Use Cases, and Entities (Pure Kotlin).
2. **Data Layer:** Handles Data Sources (API & Local DB) and Repository implementations.
3. **Presentation Layer:** State management using ViewModels and UI rendering with Jetpack Compose.



[Image of Clean Architecture in Android]


---

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone [https://github.com/maged1986/JobHunter.git](https://github.com/maged1986/JobHunter.git)

This program is originally based on recipesapi which allows recpies from various sources to be presented
![Screenshot_1623697679](https://user-images.githubusercontent.com/64534412/122489822-cdf5e400-cfe0-11eb-8090-f46f70eed708.png)
![Screenshot_1623697656](https://user-images.githubusercontent.com/64534412/122489910-fe3d8280-cfe0-11eb-9a72-08ed4219f0f8.png)
![Screenshot_1623697675](https://user-images.githubusercontent.com/64534412/122489913-ff6eaf80-cfe0-11eb-8956-5b696af6ba7c.png)
![Screenshot_1623697679](https://user-images.githubusercontent.com/64534412/122489916-01387300-cfe1-11eb-971b-6076682d9d65.png)
![Screenshot_1623697647](https://user-images.githubusercontent.com/64534412/122489917-01387300-cfe1-11eb-97e0-359a323b7baa.png)



## Built With
* RestfulAPI(Retrofit,Http)
* Coroutines
* Room DB
* MVVM
* Search View
* Android jetpack
* Material Design
* DrakView

## Authors
Maged Nan .

## License
This project is licensed under the apache license - see the LICENSE.txt file for details



