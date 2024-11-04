# Practice with CompletableFuture API

> Please set up the timer and start doing the following tasks !!!
> 
> Solution is available in `completed` branch

**Warmup [15 min]**: Find non-unique elements in a list

1. Clone a project https://github.com/TheJKGL/codeus-practice/tree/develop
2. Switch to `develop` branch and navigate to `src/main/java/com/practice/warmup/FindNonUniqueElementsInList.java`
3. Validate your solution using Unit Tests in `src/test/java/com/practice/warmup/FindNonUniqueElementsTest.java`

Optionally:

- Solve the task using Java Stream API
- Solve the task using  any other programming language

**Assignment [40 min]**: Practice with CompletableFuture API and HttpClient API

1. Read the short presentation below to dive into the context of the task
2. Stay in `develop` branch and navigate to `src/main/java/com/practice/assignment` package and implement all methods in `CompletableFutureTasks` class
3. Validate your solution using Unit Tests in `src/test/java/com/practice/assignment/CompletableFutureTests.java`

### Short Presentation for CompletableFuture and HttpClient

### Introduction

- **Objective:** Understand key methods of `CompletableFuture` and `HttpClient` for asynchronous programming in Java.
- **Use Cases:** Making non-blocking HTTP requests, processing results asynchronously.

---

### Overview of CompletableFuture

- **What is CompletableFuture?**
    - A class that represents a future result of an asynchronous computation.
    - Provides a way to execute tasks asynchronously and handle results and exceptions.

---

### Key Methods of CompletableFuture

1. **supplyAsync(Supplier<U> supplier)**
    - Executes a task asynchronously using a `Supplier`.
    - Returns a `CompletableFuture` containing the result.
    
    ```java
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 42);
    ```
    
2. **thenApply(Function<? super T, ? extends U> fn)**
    - Transforms the result of a `CompletableFuture` using a provided function.
    - Returns a new `CompletableFuture` with the transformed result.
    
    ```java
    CompletableFuture<String> completableFuture
      = CompletableFuture.supplyAsync(() -> "Hello");
    
    CompletableFuture<String> future = completableFuture
      .thenApply(s -> s + " World");
    
    assertEquals("Hello World", future.get());
    ```
    
3. **thenAccept(Consumer<? super T> action)**
    - Consumes the result of the `CompletableFuture` without returning a new result.
    - Useful for side effects.
    
    ```java
    CompletableFuture<String> completableFuture
      = CompletableFuture.supplyAsync(() -> "Hello");
    
    CompletableFuture<Void> future = completableFuture
      .thenAccept(s -> System.out.println("Computation returned: " + s));
    
    future.get();//void
    ```
    
4. **exceptionally(Function<Throwable, ? extends T> fn)**
    - Handles exceptions that occur during the asynchronous computation.
    - Returns a new `CompletableFuture` with a fallback result.
    
    ```java
    future.exceptionally(ex -> {
        System.out.println("Error: " + ex.getMessage());
        return null;
    });
    ```
    
5. **allOf(CompletableFuture<?>... cfs)**
    - Combines multiple `CompletableFuture` instances into one.
    - Completes when all futures complete.
    
    ```java
    CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2);
    ```
    

---

### Overview of HttpClient

- **What is HttpClient?**
    - A modern API for making HTTP requests in Java. (since Java 11)
    - Supports synchronous and asynchronous operations.

---

### Key Methods of HttpClient

1. **newBuilder()**
    - Creates a new `HttpClient` builder for customizing client settings (timeouts, redirects).
    
    ```java
    HttpClient client = HttpClient.newBuilder().build();
    ```
    
2. **sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler)**
    - Sends an asynchronous HTTP request and returns a `CompletableFuture<HttpResponse<T>>`.
    
    ```java
    CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, BodyHandlers.ofString());
    ```
    
3. **send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler)**
    - Sends a synchronous HTTP request and returns the response.
    
    ```java
    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    ```
    
4. **HttpRequest.newBuilder()**
    - Creates a builder for constructing HTTP requests with various settings (method, headers).
    
    ```java
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.example.com"))
            .GET()
            .build();
    ```
    
5. **HttpResponse.BodyHandlers**
    - Provides built-in body handlers for various types of responses (e.g., string, byte array).

---

### Combining CompletableFuture and HttpClient

- **Asynchronous HTTP Requests:**
    - Utilize `HttpClient` with `CompletableFuture` to send non-blocking requests.
    - Process responses asynchronously using `thenApply`, `thenAccept`, and handle exceptions with `exceptionally`.

---

### Example Code Snippet

```java
CompletableFuture<User> userFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
    .thenApply(...)
    .thenApply(...)
    .exceptionally(...);
```

---

### Conclusion

- **Benefits:**
    - Efficiently handle multiple asynchronous operations.
    - Build scalable applications without blocking threads.
- **Key Takeaway:** Understanding these methods allows for robust and efficient HTTP interactions in Java applications.

### Useful resources for self education:

https://www.baeldung.com/java-9-http-client

https://www.baeldung.com/java-completablefuture

https://therealsainath.medium.com/resttemplate-vs-webclient-vs-httpclient-a-comprehensive-comparison-69a378c2695b
