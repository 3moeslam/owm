# Weather App
## _Another simple Android client for OWM API_



https://user-images.githubusercontent.com/21203596/131329143-2cf5c03b-7576-475e-a39e-95752f164654.mov


**Design author** [Digit X](https://www.behance.net/digitx)
**Design file** [Weath.io - Clean Weather v2](https://www.behance.net/gallery/89709811/Weathio-Clean-Weather-v2-Free-Adobe-Xd-File)
**Loading lottie file** [Cloudysun](https://lottiefiles.com/42890-cloudysun)  _Some colors editing done on lottiefiles editor_

## Used libraries
- [retrofit](https://square.github.io/retrofit/)
- [retrofit2-kotlinx-serialization-converter](https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter)
- [lottie](https://github.com/airbnb/lottie-android)
- [hilt DI](https://developer.android.com/training/dependency-injection/hilt-android#groovy)
- [Kotlin Json serializer](https://github.com/Kotlin/kotlinx.serialization)
- [timber](https://github.com/JakeWharton/timber)

## UX
**Location entry field**
- At start user should read hint, so text is black
- When user enter location he should foucs on what he type, so the hint will be white and text will be black


## architecture
**This app archtected as hugh business complexty application, so all design desisions will base on this assumtion.**

*__App architecture based on The Clean Architecture by robert c martin [Article](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)__*

- At the bottom level layer (inner circle) Entities layer, which hold POJOs hold data
- Above this layer Domain layer take it's place, which contain all business logic, such as data mapping, and validate strings and repositories
- On top of domain layer presentation layer take it's place, which contain ViewModels needed to hold data, and Fragments/Activities needed to represent data
- Finally Frameworks take it's place to inject third-party frameworks like retrofit.


## Future enhancement
- Replace Retrofit#Response in domain layer by built-in type (not done for time saving)
- increase UI tests

## STORY-6
Settings screen to change application language and support mutli language


