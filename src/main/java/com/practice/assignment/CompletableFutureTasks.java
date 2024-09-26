package com.practice.assignment;

import com.practice.assignment.model.CustomMapperUtils;
import com.practice.assignment.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.practice.assignment.model.CustomMapperUtils.mapToUser;

public class CompletableFutureTasks {

    private final HttpClient httpClient;

    public CompletableFutureTasks(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Implement a method that sends a synchronous HTTP GET request using Java's HttpClient API to a specified URI and
     * returns a User object based on the response received from the server.
     * Map the response body (assumed to be JSON) to a User object using the CustomMapperUtils.mapToUser(String json) method.
     *
     * @param uri input uri of resource. For example, you can use this resource for manual testing: https://reqres.in/api/users/1.
     * @return User object based on the server's response.
     */
    public User executesSyncGetRequest(URI uri) throws IOException, InterruptedException {
        throw new RuntimeException("Not Implemented");
    }

    /**
     * Implement a method that sends an asynchronous HTTP GET request using Java's HttpClient API and CompletableFuture API.
     * After sending the request, the method should process the response asynchronously in 4 steps:
     * - 1: it should extract the response body (think which method of CompletableFuture can help you with this),
     * - 2: map this body to a User object using a utility function CustomMapperUtils::mapToUser.
     * - 3: if an exception occurs during the asynchronous operation, the method should handle it
     *      by printing the exception message and returning null. (Also think which method of CompletableFuture can help you with this)
     * - 4: the final result of the asynchronous operation should be obtained using CompletableFuture method,
     *      which blocks until the operation completes and returns the User object.
     *
     * @param uri input uri of resource. For example, you can use this for manual testing: https://reqres.in/api/users/2.
     * @return User object based on the server's response.
     */
    public User executeAsyncGetRequest(URI uri) {
        throw new RuntimeException("Not Implemented");
    }

    /**
     * Implement a method that performs multiple asynchronous HTTP GET requests using Java's HttpClient API and CompletableFuture API.
     * The method should accept a list of URIs, build corresponding HttpRequest objects for each,
     * and execute the following steps asynchronously for each request:
     * - 1: Send the HTTP request using HttpClient's sendAsync method and handle the response body.
     * - 2: Extract the response body from each HttpResponse.
     * - 3: Map the extracted response body to a User object using a utility function CustomMapperUtils::mapToUser.
     * - 4: If an exception occurs during the asynchronous execution of any request, handle it by printing the exception message and returning null.
     * - 5: Collect all CompletableFutures into List and call CompletableFuture::join for each until all tasks complete, return a list of User objects.
     *
     * @param uriList a list of URIs from which to fetch user data. Each URI will be used for an individual asynchronous request.
     * @return a list of User objects based on the server responses for each URI. If an error occurs for any request, null will be returned for that specific request.
     */
    public List<User> executeMultipleAsyncGetRequests(List<URI> uriList) {
        throw new RuntimeException("Not Implemented");
    }

    /**
     * Implement a method that performs multiple asynchronous HTTP GET requests using Java's HttpClient API and CompletableFuture API,
     * similar to the previous method, but with a key difference: this method should use `CompletableFuture.allOf()` to combine multiple asynchronous tasks.
     *
     * Differences from the previous method:
     * - Instead of handling each CompletableFuture independently and joining them individually,
     *   this method combines all the CompletableFutures into one using `CompletableFuture.allOf()`.
     * - The method uses `thenAccept(users::add)` to accumulate each User object into a shared `List<User>`, which is updated as each request completes.
     *
     * @param uriList a list of URIs from which to fetch user data. Each URI will be used for an individual asynchronous request.
     * @return a list of User objects based on the server responses for each URI. The method ensures all requests complete before returning the results.
     */
    public List<User> executeMultipleAsyncGetRequestsWithAllOf(List<URI> uriList) {
        throw new RuntimeException("Not Implemented");
    }
}
