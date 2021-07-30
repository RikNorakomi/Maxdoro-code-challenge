
RijksmuseumRepository
- getArtObjectDetails is 'main-safe' by returning a Coroutine through withContext(ioDispatcher). 
making sure that the code isn't executed on the main thread.

- The 'defaultDispatcher' is injected via constructor.
As it is best practice to not hard-code dispatchers, but use this dependency injection pattern.
This makes testing easier and the dispatchers can be replaced with 'TestCoroutineDispatcher' in unit tests to make test more deterministic.

- Only exposing suspend functions (one-shot calls) and Flows (data streams)
This is best practice makes the caller, generally the presentation layer (ViewModel/UseCases), able to control the execution and lifecycle of the work happening in those layers, and cancel when needed.
(From: https://developer.android.com/kotlin/coroutines/coroutines-best-practices)


RijksCollectionViewModel
- _detailInformation: As a best practice mutable types shouldn't be exposed to other classes. That way all changes to mutable types are happening from
within one class. This makes it easier to troubleshoot & debug when something goes wrong. 
- loadDetailedInformation() starts a coroutine using viewModelScope which survives configuration changes

TODO MutableStateFlow?!

RijksMuseumRepositoryTest
- TestCoroutineDispatcher is used making it possible to .runBlockingTests()
This way the tests become deterministic (meaning always return the same results) and won't suffer from race conditions


- TODO: Exception Handling
- TODO: Ui State Handling
