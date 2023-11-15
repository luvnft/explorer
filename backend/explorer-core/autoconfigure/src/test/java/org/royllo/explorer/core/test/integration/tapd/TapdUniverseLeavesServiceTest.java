package org.royllo.explorer.core.test.integration.tapd;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.royllo.explorer.core.provider.tapd.DecodedProofResponse;
import org.royllo.explorer.core.provider.tapd.TapdService;
import org.royllo.explorer.core.provider.tapd.UniverseLeavesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(properties = {"tapd.api.base-url=https://testnet.universe.royllo.org:8089"})
@DisplayName("Lightning TAPD universe leaves service test")
public class TapdUniverseLeavesServiceTest {

    @Autowired
    private TapdService tapdService;

    @Test
    @DisplayName("getUniverseLeaves() on lightning TAPD")
    public void getUniverseLeavesTest() {
        // Testing this:
        // curl https://testnet.universe.lightning.finance/v1/taproot-assets/universe/leaves/asset-id/05c34a505589025a0a78c31237e560406e4a2c5dc5a41c4ece6f96abbe77ad53?proof_type=PROOF_TYPE_ISSUANCE

        UniverseLeavesResponse response = tapdService.getUniverseLeaves("https://testnet.universe.lightning.finance",
                "05c34a505589025a0a78c31237e560406e4a2c5dc5a41c4ece6f96abbe77ad53").block();

        // Testing the response.
        assertNotNull(response);
        assertEquals(1, response.getLeaves().size());
        assertNotNull(response.getLeaves().get(0));
        assertNotNull(response.getLeaves().get(0).getProof());

        // Check we can decode the proof.
        final DecodedProofResponse decodedProof = tapdService.decode(response.getLeaves().get(0).getProof()).block();
        assertNotNull(decodedProof);
        assertNull(decodedProof.getErrorCode());
        assertNotNull(decodedProof.getDecodedProof());
    }

    @Test
    @DisplayName("getUniverseLeaves() with wrong value on lightning TAPD")
    public void getUniverseLeavesWithWrongValueTest() {
        UniverseLeavesResponse response = tapdService.getUniverseLeaves("https://testnet.universe.lightning.finance",
                "NON_EXISTING_ASSET_ID").block();

        // Testing the response (error).
        assertNotNull(response);
        assertEquals(2, response.getErrorCode());
        assertEquals("encoding/hex: invalid byte: U+004E 'N'", response.getErrorMessage());
    }

}
