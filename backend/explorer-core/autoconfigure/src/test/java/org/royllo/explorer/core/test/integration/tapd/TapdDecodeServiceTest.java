package org.royllo.explorer.core.test.integration.tapd;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.royllo.explorer.core.provider.tapd.DecodedProofResponse;
import org.royllo.explorer.core.provider.tapd.TapdService;
import org.royllo.test.TestAssets;
import org.royllo.test.tapd.DecodedProofValue;
import org.royllo.test.tapd.DecodedProofValueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.royllo.test.TestAssets.ROYLLO_COIN_ASSET_ID;

@SpringBootTest(properties = {"tapd.api.base-url=https://157.230.85.88:8089"})
@DisplayName("TAPD proof service test")
public class TapdDecodeServiceTest {

    @Autowired
    private TapdService tapdService;

    @Test
    @DisplayName("Calling decode() on TAPD")
    @SuppressWarnings("SpellCheckingInspection")
    public void decodeTest() {
        // Value coming from the server.
        final DecodedProofResponse response = tapdService.decode("0000000001fd051400241101b19e0dd323f86840976a161bf807fd1dbc30e793bb74aff307e1e9edfa570000000101500000d6224f34b13ded7327694942bf00a9060938f759a544e98e7b0139120000000000002d2c483ab11c46d36e302cae0e225c790ce201449138186b3dc7e83e72652a594816a764ffff001d274d807202fd0189020000000001021101b19e0dd323f86840976a161bf807fd1dbc30e793bb74aff307e1e9edfa570100000000ffffffffdc62e81401abfec546a45213c65c9910466f06ea1b831b017c13edfa71d775b10200000000ffffffff02e80300000000000022512040b3ddefc0df09b89fd1379a3bd246bf2827ed461506a5899dc230324d0aa158bc05000000000000225120d7ab0deb6185088278a66f5599be41510db531fd7f2df39e3b8558e2d2b65b0602473044022023f92ca5b7289d5cfb2ae415b91efa8bb9758d97b78b8ad17c81ce43fa9d262a02205c790874cbd4dae139f1d3e2b029bb97c4bf13bff0bc80127dacf9e917a37ed5012102cf948a447b6b49ccf83754b2455b7c6f77a9daeb1bb192f17ae34d3f52182a1a024630430220698a8f0543c97775ece1ad26a63f99d837ff9fd3b136e5c43cc984c37a75b3dd021f4d183f9811ac7a7cde904c795f68f96bda845efb5493a95582b60a93ca55c201210305c9ac7185c67621b7534e700d9592863f07ae5b9add38da9576e4a06a66c1000000000003c206e47b7ffac0a46c638ebd66bd72c07c93059cb9dcacd5474b4d29bb0f1cc661ef12dc3d91a992c4d4800fadc4593e665783966e9ea18257e7d1d187ee3f70a3a53f660f84796da9dccf2521f2bc0caddb627789062cca22e8de926f1e2327370f38e8899c9da3fcc32ccbccb65ccac88e253398c0d447323a456813bf08f7b22bd1af29b3bec54a12010c5bcd4a545e0bc461507236adc7fef624b70042f53a0ebf264c2dfde363f7e68820f57767962e412238ce2ee06ace92795f496ee1c5eb2e04fd015600010001541101b19e0dd323f86840976a161bf807fd1dbc30e793bb74aff307e1e9edfa57000000010a726f796c6c6f436f696e0c482467dfb29000804e044c4c6044b487c0280082002d4611a8ba7f22703d6300000000000201000303fd03e70669016700650000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080200000921025d615b377761a5bfcfe84f0f11afd35837a68f702dd8a0cac0a2a4052b20d2110a6102260e9ffbe7fdabe746b4ba4c3b86c8f237d7946f116949e93db77d5e0357c13dd0d86f5c646fcca990b8ceaf7a480762c92900351ae84e8135740fa92a66c8c0a4e509c8d10df8913924a583d74e9cf25ccd56eb2552c3b92a8c3c9ebe8cca97059f0004000000000121026ad322cc8a05cf5723bf8aeb5c778c6462146e573af182ba20c6bed53ea29ae60274004900010001209e4f59a9e6c363a266472043f21f2f11b0b7f206f2abd8760d555832f16cf63f02220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0630012e00040000000101210264e26d9bcccf4e442e08a8bab1fc2a0b64fcb83b34cbba1fe4b0404c3fd110980303020101081c0001000117526f796c6c6f20636f696e20666f7220746573746e657401bc6c08537fd418062422c4c2803bca5cc7edba3fd5693f5d3f6370a45d0d7d").block();
        assertNotNull(response);

        // Value coming from our test data.
        final DecodedProofValue decodedProofValueFromTest = TestAssets.findAssetValueByAssetId(ROYLLO_COIN_ASSET_ID).getDecodedProofValues().get(0);
        assertNotNull(decodedProofValueFromTest);

        // =============================================================================================================
        // Testing all the value from the response with what we have in test data.

        // Errots.
        assertNull(response.getErrorCode());
        assertNull(response.getErrorMessage());

        // Asset.
        final DecodedProofResponse.DecodedProof.Asset assetFromServer = response.getDecodedProof().getAsset();
        final DecodedProofValueResponse.DecodedProof.Asset assetFromTestData = decodedProofValueFromTest.getResponse().getDecodedProof().getAsset();
        assertEquals(assetFromTestData.getAssetType(), assetFromServer.getAssetType());
        assertEquals(0, assetFromTestData.getAmount().compareTo(assetFromServer.getAmount()));
        assertEquals(assetFromTestData.getLockTime(), assetFromServer.getLockTime());
        assertEquals(assetFromTestData.getRelativeLockTime(), assetFromServer.getRelativeLockTime());
        assertEquals(assetFromTestData.getScriptKey(), assetFromServer.getScriptKey());
        assertEquals(assetFromTestData.getScriptVersion(), assetFromServer.getScriptVersion());

        // Genesis point.
        final DecodedProofResponse.DecodedProof.Asset.AssetGenesis assetGenesisFromServer = assetFromServer.getAssetGenesis();
        final DecodedProofValueResponse.DecodedProof.Asset.AssetGenesis assetGenesisFromTestData = assetFromTestData.getAssetGenesis();
        assertEquals(assetGenesisFromTestData.getGenesisPoint(), assetGenesisFromServer.getGenesisPoint());
        assertEquals(assetGenesisFromTestData.getName(), assetGenesisFromServer.getName());
        assertEquals(assetGenesisFromTestData.getMetaDataHash(), assetGenesisFromServer.getMetaDataHash());
        assertEquals(assetGenesisFromTestData.getAssetId(), assetGenesisFromServer.getAssetId());
        assertEquals(assetGenesisFromTestData.getOutputIndex(), assetGenesisFromServer.getOutputIndex());
        assertEquals(assetGenesisFromTestData.getVersion(), assetGenesisFromServer.getVersion());

        // Asset group.
        final DecodedProofResponse.DecodedProof.Asset.AssetGroup assetGroupFromServer = response.getDecodedProof().getAsset().getAssetGroup();
        final DecodedProofValueResponse.DecodedProof.Asset.AssetGroup assetGroupFromTestData = assetFromTestData.getAssetGroup();
        // TODO Waiting for issue https://github.com/lightninglabs/taproot-assets/issues/407 to be fixed.
        assertEquals("", assetGroupFromServer.getRawGroupKey());
        assertEquals(assetGroupFromTestData.getTweakedGroupKey(), assetGroupFromServer.getTweakedGroupKey());
        assertEquals(assetGroupFromTestData.getAssetIdSig(), assetGroupFromServer.getAssetIdSig());

        // Chain anchor.
        final DecodedProofResponse.DecodedProof.Asset.ChainAnchor chainAnchorFromServer = response.getDecodedProof().getAsset().getChainAnchor();
        final DecodedProofValueResponse.DecodedProof.Asset.ChainAnchor chainAnchorFromTestData = assetFromTestData.getChainAnchor();
        assertEquals(chainAnchorFromTestData.getAnchorTx(), chainAnchorFromServer.getAnchorTx());
        assertEquals(chainAnchorFromTestData.getAnchorTxId(), chainAnchorFromServer.getAnchorTxId());
        assertEquals(chainAnchorFromTestData.getAnchorBlockHash(), chainAnchorFromServer.getAnchorBlockHash());
        assertEquals(chainAnchorFromTestData.getAnchorOutpoint(), chainAnchorFromServer.getAnchorOutpoint());
        assertEquals(chainAnchorFromTestData.getInternalKey(), chainAnchorFromServer.getInternalKey());
        // TODO Waiting for issue https://github.com/lightninglabs/taproot-assets/issues/407 to be fixed.
        assertEquals("", chainAnchorFromServer.getMerkleRoot());
        assertEquals(chainAnchorFromTestData.getTapscriptSibling(), chainAnchorFromServer.getTapscriptSibling());
    }

    @Test
    @DisplayName("Calling decode() with proofAtDepth parameter")
    public void proofAtDepthTest() {
        // Test made with TestCoin (proof2 as there are two proof inside the file).
        // docs/design/asset-page-design/README.md
        final String rawProof = "0000000002fd042d0024770648d57aa5bd6a18c59d3838b79df466de8ed918e0cb32322acefc6b84d5450000000101500000c020facc6d7241d883c0f02617fab150dfa134ea2c59eafb89b601000000000000002365cc8749293b1d1e9604033a994ee7d669665f1e2d96543b0c31c9775651afb125cc649fc63e1979d5272202cd02000000000101770648d57aa5bd6a18c59d3838b79df466de8ed918e0cb32322acefc6b84d5450100000000ffffffff02e803000000000000225120ee35cfe3ea75e18ab3aa6087a677eaa31542c4471c84122d8fb6af2653953b73bd3d0f000000000022512029b8b09e22b88adfd4cfd28bb84d74924dc79ab2976e68b00f4edcd4ce5afdc801406ec217f3617a537a5af6f5ac2cc01178f287c73f4ca02ab957bef59d7a393c844defa2a2122814d9e5c55c0b00bc92a615e7b8797407e15b522a904d5d1f4e110000000003a205a837e0fe9b6f90589ef170fc1025a820e3f32b78e8347e3dc507c7c1226231d622b41784c177d3a97900e3839cc7976033d4f5545d54cd6a17329316c4d5bbf290a98f08f654abde40622c2ccedba986c601f466121a6793578b3cde49ab989f6bfac818d524bc7beb5e4f44b77600722c26de4adabae0fcf02662e160a644cee0b205748fb69ed87a846059c3567e573fff5693e4b0926e2034265b010ad0910504fd01540001000152770648d57aa5bd6a18c59d3838b79df466de8ed918e0cb32322acefc6b84d545000000010854657374436f696e0b1f5ce77088e8206cb68efc2e287c9cea8a08f456656fe4aa0934cc8195436400000000000201000303fd03e8066901670065000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000008020000092102f02cc84b9f38f9596afd32542439d87fb8cd44e73ac4d94445044bb5e98331b10a610237ba39f4f3690a5c71de17ddad915afa8cd124f09bfc984de11546c2a76b07e35a81224467702a3565efe912c3dcdf6442fa2ea778dd548b7d97cc794183cbcb7a9801bee4583c2bad0f1e2135c22005fcc8570eebb3a0e1b5b2c6b3115f0fd6059f00040000000001210276105ab591188bfc5858d9fc83d45fbd54e598633397016fec096f53143d8757027400490001000120be80c63c3b4523e0d068d9eb3692d9a41ab11a4da731d3493d0f4a77b3c246c702220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0630012e0004000000010121028b171ac2d9857bcd250183f8122694dd881ce2e6b778a45ddc4afb676a99e1ac0303020101080f000100010a746573742061737365740b040025b2eaeb27d1ee90e3a55d5300ec1bca18240864321c6a764f188f3d7542b891aaad78fd060a00247d6fdca02ecb7c63ca589cd44eb528022d1a3ca9b2000361e311022c11bb12cb000000000150000020202f92dab5f33b76b61be90173f60638f1c01a7b24c938bb9b1d000000000000000921afbfa5af88e7620267c39c363b77c55c7a9f4ccd26245a65d6c6220ac486852dcc649fc63e19420b23d202fd0163020000000001027d6fdca02ecb7c63ca589cd44eb528022d1a3ca9b2000361e311022c11bb12cb0100000000ffffffff7d6fdca02ecb7c63ca589cd44eb528022d1a3ca9b2000361e311022c11bb12cb00000000000000000003e803000000000000225120e92050f204f23a1e62619b8d2cb02b9dec43e686fa920b5ba2c77b4fdbc73534e8030000000000002251203e979c9d5c20de3f48e8a7e188485141060bbd814c93faa3334883b685f23a9cd4380f00000000002251204ac53d882b52e25730e782aae311b25249bd189f3ccea1547e4236d2dc73c8e301405dabd9708f6b2887f42e705291bbeda6309c02594c4cef9dc39c2f544e8f92efae8144c12096ca8de5537c1b81b94e215b343693b2934ca3bb2ee47fc7fad5d10140286a51312ad27bfe20e3c538ad2b404093264caa7d3c24d53a727a6be3dda37aff861cdda5f46783f9508b3d82cb92dce0626e1f11ec36f5638b4300053352d00000000003c2061ec383155d7be67efb06a96ea32a4768cd70c65861cc0b4fbe8d4fbc8467fc9ebd206cc2b5aea2526e79be15db9791ef43412bc612a930055294e1ecbf8d2557fc73db7228ecfedd8b3ce0518901d97b9baec1343f05232d130072bb6fa8e69fd897e2698890b828055bbb7950a225654767daade8a1244948052abcf376a8d66b86e8f560d937d883f0bca6b092987cfa6b6910a34d02cf08003bd2412f5308bfcaac2f94ff21e3d5df23d23a9a1987427bd25048b5456ff4464de45cc05ada1b04fd01c20001000152770648d57aa5bd6a18c59d3838b79df466de8ed918e0cb32322acefc6b84d545000000010854657374436f696e0b1f5ce77088e8206cb68efc2e287c9cea8a08f456656fe4aa0934cc8195436400000000000201000303fd03de06ad01ab00657d6fdca02ecb7c63ca589cd44eb528022d1a3ca9b2000361e311022c11bb12cb00000000981dd27089b3bebc0ea0cd17e78e69ca8d582e7b9695ac53f97ea10723a852f802f02cc84b9f38f9596afd32542439d87fb8cd44e73ac4d94445044bb5e98331b101420140fa144bbeb5242111c3f533f8dfa443396ed9e2cb1e3d045cd8e8c75a516c53f59f5c12d73cb458f32c32bb104964c944477929f59a473c612402e23d22ac3ce5072887aeb9944f2ae3b58973904613732e92e3a37bb7df4992260bad4f6b932e11dd00000000000003e808020000092102f37afbb9642b2929333045d6904afbacd9a66811081e1a5e22b1fcc89eccffa50a610237ba39f4f3690a5c71de17ddad915afa8cd124f09bfc984de11546c2a76b07e35a81224467702a3565efe912c3dcdf6442fa2ea778dd548b7d97cc794183cbcb7a9801bee4583c2bad0f1e2135c22005fcc8570eebb3a0e1b5b2c6b3115f0fd6059f000400000000012102cc9c45e6274e954c0c20bc8422c0bc1f392d7cbd7a0cd0d863780377a671969d027400490001000120be80c63c3b4523e0d068d9eb3692d9a41ab11a4da731d3493d0f4a77b3c246c702220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff06f802c7000400000001012102a1e5efa288215f7b9a07df3ddfa71a0a9ef2b7f770bb180a893693668d42ea65029c00710001000120be80c63c3b4523e0d068d9eb3692d9a41ab11a4da731d3493d0f4a77b3c246c7024a0001c832ebaed2264ddcc5f09c707cf7bd63f1a3491e861dba412a6b937b5b669da4000000000000000affffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffbf012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff2e000400000002012102c0468f97741b7bc3c74d6284ca1ae0c7f333302d03f86dfb7f8266f0b56ae65603030201010b040025b2eef64e4d163b9d199c154291e089bec532391bd5bb4dad643c072def157a89b595";

        final DecodedProofResponse response1 = tapdService.decode(rawProof, 0).block();
        assertNotNull(response1);
        assertEquals(2, response1.getDecodedProof().getNumberOfProofs());
        assertEquals(1, response1.getDecodedProof().getProofAtDepth());

        final DecodedProofResponse response2 = tapdService.decode(rawProof, 1).block();
        assertNotNull(response2);
        assertEquals(2, response1.getDecodedProof().getNumberOfProofs());
        assertEquals(0, response2.getDecodedProof().getProofAtDepth());
    }

    @Test
    @DisplayName("Calling decode() on tapd with an invalid proof")
    public void decodeErrorTest() {
        final DecodedProofResponse response = tapdService.decode("0000000001fd03f1002488d6b6d1f7fe8dd4134e699037b52fa7273674e3a181deacc52bdddbf62274ac00000000015000000020dbc0a8738a77d0b1f29df61820e015784d87b13f508d07af1200000000000000e9a0e62808024419f29e12075ee7192283d45787ba4e510dcc4325c4f13e2da617252c64ffff001dee9af8c502ea0200000000010188d6b6d1f7fe8dd4134e699037b52fa7273674e3a181deacc52bdddbf62274ac0000000000ffffffff02e80300000000000022512068b8ab655c6f3edfc0b241a3c560355146ed6bbf92514c09b10a0c86ac140e23910a000000000000160014c35088956d26c44af4c00dfc8bf10d9a4c79320e0247304402201829c56dba3c2ab1cd85de1fe3d7bfec8dffc2b2d4aac8c362cae25052a420290220506b92ca45a514a8b2daabb1eb8adf5283ab29b382d0331c9c884f8c89dd830f012103219e2594f427e338690c3f51409706439d8ab83fde02d18d0c28ec55c285801c0000000003fd0102083c7c4198be914f1cab7319592f0249d727fa87890ec4185b8b7881d777158d0af976bfe9e708afddbef3616fe086f90e0879295f231ac2c6d5be387ba682903bc9f67bd23cff72af821bc6feb5995b83b603aba9074ce1c74a4e13a45f108713102ce2cdd7590df19cc6c9b0f631097a2dedec3e30c1633300ea02e179eea39ef366c1a3711657d6302e516e3218b8de4a0089ca0f92621a8a49a8790dc9a1deba788b11327b109130ca4dede1a29d6ac6c03a86454199c5bc6e8a74d32df7f93d7b24f5dec13ec57f232b95d712d5947b04785cac15d00e4ea9ae6e0ffd9b3361cab39effb1b072f0918fd3ea9e9e0ae35ee61ed51e9d09548c619f06d4caa8c104e4000100014588d6b6d1f7fe8dd4134e699037b52fa7273674e3a181deacc52bdddbf62274ac000000000c6d79526f796c6c6f436f696e0e5573656420627920526f796c6c6f00000000000201000303fd03e7066901670065000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000008020000092102102a7c269bf61b587c6278206d05433fa09b9fbe30d618a2cc61868622f5518f059f000400000000012102bf9d0eac1be77456ced581721d9f8837b42346173556c418794e0347e08f817102740049000100012018c4eaca8905cad3ae027494b8c70a085e9452226ddea8eacfdaf6cb88431c1202220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0ea5f7971d2fe0f27d8975f656332a5f6bf0176b998a52dedaedf2ffbaef922f").block();

        // Testing all the value from the response.
        assertNotNull(response);
        assertNotNull(response.getErrorCode());
        assertNotNull(response.getErrorMessage());
    }

}
