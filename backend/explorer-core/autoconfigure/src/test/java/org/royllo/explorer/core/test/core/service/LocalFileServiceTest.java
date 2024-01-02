package org.royllo.explorer.core.test.core.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.royllo.explorer.core.provider.storage.LocalFileServiceImplementation;
import org.royllo.explorer.core.test.util.TestWithMockServers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.royllo.explorer.core.provider.storage.LocalFileServiceImplementation.WEB_SERVER_HOST;
import static org.royllo.explorer.core.provider.storage.LocalFileServiceImplementation.WEB_SERVER_PORT;

@SpringBootTest
@DirtiesContext
@DisplayName("LocalFileService tests")
public class LocalFileServiceTest extends TestWithMockServers {

    @Autowired
    private LocalFileServiceImplementation localFileServiceImplementation;

    @Test
    @DisplayName("Store and get file")
    public void storeAndGetFile() {
        // We store a file.
        localFileServiceImplementation.storeFile("Hello World!".getBytes(), "hello.txt");

        // We try retrieve hello.txt from the webserver.
        var client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://" + WEB_SERVER_HOST + ":" + WEB_SERVER_PORT + "/hello.txt")
                .build();
        try (Response response = client.newCall(request).execute()) {
            assertTrue(response.body().string().contains("Hello World!"));
        } catch (IOException e) {
            fail("Error while retrieving the file" + e.getMessage());
        }

        // We try to retrieve another file that doesn't exist, so we should get a 404 error.
        request = new Request.Builder()
                .url("http://" + WEB_SERVER_HOST + ":" + WEB_SERVER_PORT + "/non_existing_file.txt")
                .build();
        try (Response response = client.newCall(request).execute()) {
            assertEquals(404, response.code());
        } catch (IOException e) {
            fail("Error while retrieving the file" + e.getMessage());
        }

        // We try to store again the same file.
        localFileServiceImplementation.storeFile("Hello World!".getBytes(), "hello.txt");

        // We test that the 24a27ab522c9c33e64f4462f2acee01571e014ccbbac075786d1deae033a128d.txt file for Royllo coin
        // exists and the content is correct.
        request = new Request.Builder()
                .url("http://" + WEB_SERVER_HOST + ":" + WEB_SERVER_PORT + "/24a27ab522c9c33e64f4462f2acee01571e014ccbbac075786d1deae033a128d.txt")
                .build();
        try (Response response = client.newCall(request).execute()) {
            assertTrue(response.body().string().contains("roylloCoin on mainnet by Royllo"));
        } catch (IOException e) {
            fail("Error while retrieving the file" + e.getMessage());
        }

    }

}
