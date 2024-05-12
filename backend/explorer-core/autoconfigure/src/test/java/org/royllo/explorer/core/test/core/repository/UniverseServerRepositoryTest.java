package org.royllo.explorer.core.test.core.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.royllo.explorer.core.domain.universe.UniverseServer;
import org.royllo.explorer.core.repository.universe.UniverseServerRepository;
import org.royllo.explorer.core.test.util.TestWithMockServers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.royllo.explorer.core.util.constants.AnonymousUserConstants.ANONYMOUS_USER;

@SpringBootTest
@DirtiesContext
@DisplayName("UniverseServerRepository tests")
public class UniverseServerRepositoryTest extends TestWithMockServers {

    @Autowired
    private UniverseServerRepository universeServerRepository;

    @Test
    @DisplayName("findFirstByOrderByLastSynchronizationAttemptAsc()")
    public void findFirstByOrderByLastSynchronizationAttemptAsc() {
        // We remove all universe servers.
        universeServerRepository.deleteAll();

        // We create 6 servers:
        // Server  : lastSynchronizedOn = now - 1 day.
        UniverseServer server1 = universeServerRepository.save(UniverseServer.builder()
                .universeServerId("server1ID")
                .owner(ANONYMOUS_USER)
                .serverAddress("server1")
                .lastSynchronizationAttempt(ZonedDateTime.now().minusDays(1))
                .build());
        // Server 2: lastSynchronizedOn = now - 3 days.
        UniverseServer server2 = universeServerRepository.save(UniverseServer.builder()
                .universeServerId("server2ID")
                .owner(ANONYMOUS_USER)
                .serverAddress("server2")
                .lastSynchronizationAttempt(ZonedDateTime.now().minusDays(3))
                .build());
        // Server 3: lastSynchronizedOn = now - 1 month.
        UniverseServer server3 = universeServerRepository.save(UniverseServer.builder()
                .universeServerId("server3ID")
                .owner(ANONYMOUS_USER)
                .serverAddress("server3")
                .lastSynchronizationAttempt(ZonedDateTime.now().minusMonths(1))
                .build());
        // Server 4: lastSynchronizedOn = null.
        UniverseServer server4 = universeServerRepository.save(UniverseServer.builder()
                .universeServerId("server4ID")
                .owner(ANONYMOUS_USER)
                .serverAddress("server4")
                .lastSynchronizationAttempt(null)
                .build());
        // Server 5: lastSynchronizedOn = now - 2 days.
        UniverseServer server5 = universeServerRepository.save(UniverseServer.builder()
                .universeServerId("server5ID")
                .owner(ANONYMOUS_USER)
                .serverAddress("server5")
                .lastSynchronizationAttempt(ZonedDateTime.now().minusDays(2))
                .build());
        // Server 6: lastSynchronizedOn = null.
        UniverseServer server6 = universeServerRepository.save(UniverseServer.builder()
                .universeServerId("server6ID")
                .owner(ANONYMOUS_USER)
                .serverAddress("server6")
                .lastSynchronizationAttempt(null)
                .build());

        // Server1 : lastSynchronizedOn = now - 1 day.
        // Server2 : lastSynchronizedOn = now - 3 days.
        // server3 : lastSynchronizedOn = now - 1 month.
        // Server4 : lastSynchronizedOn = null.
        // Server5 : lastSynchronizedOn = now - 2 days.
        // Server6 : lastSynchronizedOn = null.

        // Next server to synchronize : Server4 as its date is null, and he is inserted before server 6.
        Optional<UniverseServer> serverToSynchronize = universeServerRepository.findFirstByOrderByLastSynchronizationAttemptAsc();
        assertTrue(serverToSynchronize.isPresent());
        assertEquals("server4", serverToSynchronize.get().getServerAddress());
        server4.setLastSynchronizationAttempt(ZonedDateTime.now());
        universeServerRepository.save(server4);

        // Next server to synchronise : Server6 as its date is null, and he is inserted after server 4.
        serverToSynchronize = universeServerRepository.findFirstByOrderByLastSynchronizationAttemptAsc();
        assertTrue(serverToSynchronize.isPresent());
        assertEquals("server6", serverToSynchronize.get().getServerAddress());
        server6.setLastSynchronizationAttempt(ZonedDateTime.now());
        universeServerRepository.save(server6);

        // Next server to synchronize : Server3 as its date is the oldest.
        serverToSynchronize = universeServerRepository.findFirstByOrderByLastSynchronizationAttemptAsc();
        assertTrue(serverToSynchronize.isPresent());
        assertEquals("server3", serverToSynchronize.get().getServerAddress());
        server3.setLastSynchronizationAttempt(ZonedDateTime.now());
        universeServerRepository.save(server3);

        // Next server to synchronize : Server2 as its date is the second oldest.
        serverToSynchronize = universeServerRepository.findFirstByOrderByLastSynchronizationAttemptAsc();
        assertTrue(serverToSynchronize.isPresent());
        assertEquals("server2", serverToSynchronize.get().getServerAddress());
        server2.setLastSynchronizationAttempt(ZonedDateTime.now());
        universeServerRepository.save(server2);

        // Next server to synchronize : Server5 as its date is the third oldest.
        serverToSynchronize = universeServerRepository.findFirstByOrderByLastSynchronizationAttemptAsc();
        assertTrue(serverToSynchronize.isPresent());
        assertEquals("server5", serverToSynchronize.get().getServerAddress());
        server5.setLastSynchronizationAttempt(ZonedDateTime.now());
        universeServerRepository.save(server5);

        // Next server to synchronize : Server1 as its date is the fourth oldest.
        serverToSynchronize = universeServerRepository.findFirstByOrderByLastSynchronizationAttemptAsc();
        assertTrue(serverToSynchronize.isPresent());
        assertEquals("server1", serverToSynchronize.get().getServerAddress());
        server1.setLastSynchronizationAttempt(ZonedDateTime.now());
        universeServerRepository.save(server1);

        // No more server to synchronize : all servers have been synchronized, we go back to server4.
        serverToSynchronize = universeServerRepository.findFirstByOrderByLastSynchronizationAttemptAsc();
        assertTrue(serverToSynchronize.isPresent());
        assertEquals("server4", serverToSynchronize.get().getServerAddress());
    }

}
