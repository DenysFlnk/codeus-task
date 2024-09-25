package com.practice.assignment;

import com.practice.assignment.model.CustomMapperUtils;
import com.practice.assignment.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompletableFutureTests {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockHttpResponse;

    @InjectMocks
    private CompletableFutureTasks completableFutureTasks;

    private final URI USERS_URI = URI.create("https://reqres.in/api/users/1");

    @Test
    public void testExecutesSyncGetRequest() throws Exception {
        String mockResponseBody = "{\"data\":{\"id\":2,\"email\":\"janet.weaver@reqres.in\",\"first_name\":\"Janet\"," +
                "\"last_name\":\"Weaver\",\"avatar\":\"https://reqres.in/img/faces/2-image.jpg\"}}";

        when(mockHttpResponse.body()).thenReturn(mockResponseBody);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        try (MockedStatic<HttpClient> httpClientMockedStatic = Mockito.mockStatic(HttpClient.class)) {
            httpClientMockedStatic.when(HttpClient::newHttpClient).thenReturn(mockHttpClient);

            User user = completableFutureTasks.executesSyncGetRequest(USERS_URI);

            verify(mockHttpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));

            assertNotNull(user);
            assertEquals(2, user.id());
            assertEquals("Janet", user.firstName());
            assertEquals("Weaver", user.lastname());
            assertEquals("janet.weaver@reqres.in", user.email());
            assertEquals("https://reqres.in/img/faces/2-image.jpg", user.avatar());
        }
    }

    @Test
    public void testExecuteAsyncGetRequest() throws Exception {
        String mockResponseBody = "{\"data\":{\"id\":2,\"email\":\"janet.weaver@reqres.in\",\"first_name\":\"Janet\"," +
                "\"last_name\":\"Weaver\",\"avatar\":\"https://reqres.in/img/faces/2-image.jpg\"}}";

        when(mockHttpResponse.body()).thenReturn(mockResponseBody);
        CompletableFuture<HttpResponse<String>> mockFuture = CompletableFuture.completedFuture(mockHttpResponse);
        when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockFuture);

        User mockUser = new User(2, "janet.weaver@reqres.in", "Janet", "Weaver", "https://reqres.in/img/faces/2-image.jpg");
        try (MockedStatic<CustomMapperUtils> mockedStatic = Mockito.mockStatic(CustomMapperUtils.class)) {
            mockedStatic.when(() -> CustomMapperUtils.mapToUser(mockResponseBody))
                    .thenReturn(mockUser);

            User user = completableFutureTasks.executeAsyncGetRequest(USERS_URI);
            verify(mockHttpClient).sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
            mockedStatic.verify(() -> CustomMapperUtils.mapToUser(mockResponseBody));

            assertNotNull(user);
            assertEquals(2, user.id());
            assertEquals("Janet", user.firstName());
            assertEquals("Weaver", user.lastname());
            assertEquals("janet.weaver@reqres.in", user.email());
            assertEquals("https://reqres.in/img/faces/2-image.jpg", user.avatar());
        }
    }

    @Test
    public void testExecuteMultipleAsyncGetRequests() throws Exception {
        URI uri1 = new URI("https://reqres.in/api/users/2");
        URI uri2 = new URI("https://reqres.in/api/users/3");
        List<URI> uriList = List.of(uri1, uri2);

        HttpResponse<String> mockResponse1 = mock(HttpResponse.class);
        HttpResponse<String> mockResponse2 = mock(HttpResponse.class);
        when(mockResponse1.body()).thenReturn("{\"id\": 2, \"first_name\": \"Janet\", \"last_name\": \"Weaver\", \"email\": \"janet.weaver@reqres.in\"}");
        when(mockResponse2.body()).thenReturn("{\"id\": 3, \"first_name\": \"Emma\", \"last_name\": \"Wong\", \"email\": \"emma.wong@reqres.in\"}");

        CompletableFuture<HttpResponse<String>> future1 = CompletableFuture.supplyAsync(() -> {
            // Simulate delay for async operation
            System.out.println("2001");
            sleep(200);
            return mockResponse1;
        });
        //future1.join();
        CompletableFuture<HttpResponse<String>> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("2002");
            sleep(200);
            return mockResponse2;
        });
        //future2.join();

        when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(future1)
                .thenReturn(future2);

        User mockUser1 = new User(2, "janet.weaver@reqres.in", "Janet", "Weaver", "https://reqres.in/img/faces/2-image.jpg");
        User mockUser2 = new User(3, "emma.wong@reqres.in", "Emma", "Wong", "https://reqres.in/img/faces/3-image.jpg");

        try (MockedStatic<CustomMapperUtils> mockedStatic = Mockito.mockStatic(CustomMapperUtils.class)) {
            mockedStatic.when(() -> CustomMapperUtils.mapToUser(mockResponse1.body()))
                    .thenReturn(mockUser1);
            mockedStatic.when(() -> CustomMapperUtils.mapToUser(mockResponse2.body()))
                    .thenReturn(mockUser2);

            // Measure time to verify asynchronous execution
            long startTime = System.currentTimeMillis();
            List<User> users = completableFutureTasks.executeMultipleAsyncGetRequests(uriList);
            long endTime = System.currentTimeMillis();

            // Verify that the execution time is less than the sum of both delays, meaning they ran concurrently
            long executionTime = endTime - startTime;
            System.out.println(executionTime);
            //assertTrue("Execution should be asynchronous", executionTime < 400);

            // Verify that the results are correct
            assertNotNull(users);
            assertEquals(2, users.size());

            assertEquals(2, users.get(0).id());
            assertEquals("Janet", users.get(0).firstName());

            assertEquals(3, users.get(1).id());
            assertEquals("Emma", users.get(1).firstName());

            // Verify the static method calls
            mockedStatic.verify(() -> CustomMapperUtils.mapToUser(mockResponse1.body()), times(1));
            mockedStatic.verify(() -> CustomMapperUtils.mapToUser(mockResponse2.body()), times(1));
        }
    }

    // Helper method to simulate delay
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

//    @Test
//    public void test() throws ExecutionException, InterruptedException {
//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
//            throw new RuntimeException();
//        });
//        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//        when(client.executeAsyncGetRequest(captor.capture())).thenReturn(completableFuture);
//        var result = completableFutureTasks.executeAsyncGetRequest2();
//        User user = result.get();
//        Assert.assertEquals(0, user.id());
//        Assert.assertEquals("", user.email());
//        System.out.println("URL: " + captor.getValue());
//        Assert.assertEquals("url", captor.getValue());
//    }
}
