package org.royllo.explorer.batch.test.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.royllo.explorer.batch.batch.maintenance.PurgeBatch;
import org.royllo.explorer.core.domain.request.AddProofRequest;
import org.royllo.explorer.core.domain.request.AddUniverseServerRequest;
import org.royllo.explorer.core.repository.request.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.royllo.explorer.core.util.constants.AnonymousUserConstants.ANONYMOUS_USER;
import static org.royllo.explorer.core.util.enums.RequestStatus.FAILURE;
import static org.royllo.explorer.core.util.enums.RequestStatus.OPENED;
import static org.royllo.explorer.core.util.enums.RequestStatus.SUCCESS;

@DisplayName("Purge batch test")
@DirtiesContext
@ActiveProfiles("scheduler-disabled")
@Testcontainers
@SpringBootTest(properties = {"spring.datasource.url=jdbc:tc:postgresql:15:///explorer",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"})
public class PurgeBatchTest {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private PurgeBatch purgeBatch;

    @Test
    @DisplayName("purge()")
    public void purge() {
        // We start by erasing all requests.
        requestRepository.deleteAll();

        // =============================================================================================================
        // Requests with "OPENED" status.
        // We create 10 000 "Add proof request" with "OPENED" status.
        for (int i = 1; i <= 10_000; i++) {
            AddProofRequest request = new AddProofRequest();
            request.setRequestId("ID_" + i);
            request.setCreator(ANONYMOUS_USER);
            request.setStatus(OPENED);
            request.setProof("Request n°" + i);
            requestRepository.save(request);
        }
        // We should have 10 000 requests.
        assertTrue(requestRepository.findByRequestId("ID_1").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_10000").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_10001").isEmpty());
        assertEquals(10_000, requestRepository.count());

        // We create 10 000 "Add universe server request" with "OPENED" status.
        for (int i = 10_001; i <= 20_000; i++) {
            AddUniverseServerRequest request = new AddUniverseServerRequest();
            request.setRequestId("ID_" + i);
            request.setCreator(ANONYMOUS_USER);
            request.setStatus(OPENED);
            request.setServerAddress("Request n°" + i);
            requestRepository.save(request);
        }
        // We should have 20 000 requests.
        assertTrue(requestRepository.findByRequestId("ID_10001").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_20000").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_20001").isEmpty());
        assertEquals(20_000, requestRepository.count());

        // =============================================================================================================
        // Requests with "FAILURE" status.
        // We create 10 000 "Add proof request" with "FAILURE" status.
        for (int i = 20_001; i <= 30_000; i++) {
            AddProofRequest request = new AddProofRequest();
            request.setRequestId("ID_" + i);
            request.setCreator(ANONYMOUS_USER);
            request.setStatus(FAILURE);
            request.setProof("Request n°" + i);
            requestRepository.save(request);
        }
        // We should have 30 000 requests.
        assertTrue(requestRepository.findByRequestId("ID_20001").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_30000").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_30001").isEmpty());
        assertEquals(30_000, requestRepository.count());

        // We create 10 000 "Add universe server request" with "FAILURE" status.
        for (int i = 30_001; i <= 40_000; i++) {
            AddUniverseServerRequest request = new AddUniverseServerRequest();
            request.setRequestId("ID_" + i);
            request.setCreator(ANONYMOUS_USER);
            request.setStatus(FAILURE);
            request.setServerAddress("Request n°" + i);
            requestRepository.save(request);
        }
        // We should have 20 000 requests.
        assertTrue(requestRepository.findByRequestId("ID_30001").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_40000").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_40001").isEmpty());
        assertEquals(40_000, requestRepository.count());

        // =============================================================================================================
        // Requests with "SUCCESS" status.
        // We create 10 000 "Add proof request" with "SUCCESS" status.
        for (int i = 40_001; i <= 50_000; i++) {
            AddProofRequest request = new AddProofRequest();
            request.setRequestId("ID_" + i);
            request.setCreator(ANONYMOUS_USER);
            request.setStatus(SUCCESS);
            request.setProof("Request n°" + i);
            requestRepository.save(request);
        }
        // We should have 30 000 requests.
        assertTrue(requestRepository.findByRequestId("ID_40001").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_50000").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_50001").isEmpty());
        assertEquals(50_000, requestRepository.count());

        // We create 10 000 "Add universe server request" with "SUCCESS" status.
        for (int i = 50_001; i <= 60_000; i++) {
            AddUniverseServerRequest request = new AddUniverseServerRequest();
            request.setRequestId("ID_" + i);
            request.setCreator(ANONYMOUS_USER);
            request.setStatus(SUCCESS);
            request.setServerAddress("Request n°" + i);
            requestRepository.save(request);
        }
        // We should have 20 000 requests.
        assertTrue(requestRepository.findByRequestId("ID_50001").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_60000").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_60001").isEmpty());
        assertEquals(60_000, requestRepository.count());

        // =============================================================================================================
        // The rule is that all failed requests besides MAXIMUM_FAILED_REQUESTS_STORE (10 000) must be deleted.
        int totalRequests = requestRepository.findAll().size();
        assertEquals(60_000, totalRequests);
        long failedRequests = requestRepository.countByStatusOrderById(FAILURE);
        assertEquals(20_000, failedRequests);
        assertTrue(requestRepository.findByRequestId("ID_40001").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_60000").isPresent());

        // =============================================================================================================
        // Running the purge.
        // We have 60 000 requests in total.
        // We have 20 000 requests with "FAILURE" status.
        // 10 000 can only be kept.
        // 10 000 should be deleted, the ones between after 50 000... up to 60 000.
        purgeBatch.purge();

        totalRequests = requestRepository.findAll().size();
        assertEquals(50_000, totalRequests);
        failedRequests = requestRepository.countByStatusOrderById(FAILURE);
        assertEquals(10_000, failedRequests);
        assertTrue(requestRepository.findByRequestId("ID_20001").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_30000").isPresent());
        assertTrue(requestRepository.findByRequestId("ID_30001").isEmpty());
        assertTrue(requestRepository.findByRequestId("ID_40000").isEmpty());
        assertTrue(requestRepository.findByRequestId("ID_40001").isPresent());
    }

}
