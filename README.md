# instant-system-test
Native Android application for displaying news in a basic way.

## Build setup

This project expects a `secrets.json` file at the root of the repository. It
contains the `NEWS_API_KEY` and `NEWS_API_HOST` values for the `dev`, `beta` and
`prod` build flavors and is read during the Gradle configuration phase. Without
this file the build will fail.

## Key libraries

The project relies on the following main libraries, defined in
`gradle/libs.versions.toml`:

- **Coil** – Kotlin-first image loader for Jetpack Compose, chosen for its
  lightweight API and seamless Compose integration.
- **Robolectric** – JVM-based framework that lets Android tests run on the
  local JVM, providing fast and reliable unit tests without an emulator.
- **Retrofit** – Type-safe HTTP client used for networking, making API calls
  straightforward through declarative interfaces.
- **OkHttp3** – Underlying HTTP client powering Retrofit; offers connection
  pooling, caching and an interceptor system.
- **Koin** – Lightweight dependency injection framework used for structuring
  modules and simplifying testing.
- **Turbine** – Utility for testing Kotlin `Flow` emissions, enabling concise
  coroutine-based tests.
- **Shimmer** – Provides shimmering placeholder effects while content is
  loading, improving perceived performance.
- **Browser (Custom Tabs)** – `androidx.browser` library to open web content via
  Chrome Custom Tabs for a more integrated user experience.
- **DataStore** – Jetpack DataStore for storing key-value pairs, offering a
  modern alternative to `SharedPreferences`.
- **Coroutines** – Kotlin coroutines for structured concurrency and
  asynchronous programming throughout the app.
