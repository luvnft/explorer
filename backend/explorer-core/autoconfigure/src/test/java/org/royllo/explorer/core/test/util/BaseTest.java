package org.royllo.explorer.core.test.util;

import org.royllo.explorer.core.util.base.Base;
import org.royllo.explorer.core.util.enums.AssetType;

import java.math.BigInteger;

import static org.royllo.explorer.core.util.enums.AssetType.NORMAL;

/**
 * Utility classes for tests.
 */
@SuppressWarnings("SpellCheckingInspection")
public class BaseTest extends Base {

    /** Non existing database. */
    protected static final String BITCOIN_TRANSACTION_NON_EXISTING = "non_existing";
    /** txid of the first bitcoin transaction in database - one output. */
    protected static final String BITCOIN_TRANSACTION_1_TXID = "2a5726687859bb1ec8a8cfeac78db8fa16b5b1c31e85be9c9812dfed68df43ea";
    /** txid of the second bitcoin transaction in database - two outputs. */
    protected static final String BITCOIN_TRANSACTION_2_TXID = "46804b8a193cae200c99531f0ea90d81cc0c0e44e718b57e7b9ab5bb3926b946";
    /** txid of a third transaction not in our database but in the blockchain - three outputs. */
    protected static final String BITCOIN_TRANSACTION_3_TXID = "117ad75a79af2e7fdb2908baee9171fde4d6fb80c7322dcb895a2429f84f4d4a";
    /** txid of a taproot transaction in our database used for test. */
    protected static final String BITCOIN_TAPROOT_TRANSACTION_1_TXID = "taproot_test_transaction_number_1_d6fb80c7322dcb895a2429f84f4d4a";
    /** txid of a taproot transaction NOT in our database, but IN the blockchain. */
    protected static final String BITCOIN_TAPROOT_TRANSACTION_2_TXID = "d61a4957e5e756a7631246b1a00d685e4854f98f8c2835bafafed8b1d1e26be5";
    /** txid of a taproot transaction containing a Taproot asset on the testnet (output 1). */
    protected static final String BITCOIN_TESTNET_TAPROOT_ASSET_TRANSACTION_1_TXID = "d8a8016095b9fcd1f63c57342d375026ecbc72c885a54b676c6e62b216e15365";
    /** Taproot asset number 1. */
    protected static final String ASSET_ID_NUMBER_01 = "b34b05956d828a7f7a0df598771c9f6df0378680c432480837852bcb94a8f21e";

    /* ============================================================================================================== */
    /* royllo coin - Asset aleady existing in our database and on testnet (data-assets.xml) */
    protected static final int ROYLLO_COIN_ID = 1;
    protected static final int ROYLLO_COIN_VERSION = 0;

    protected static final String ROYLLO_COIN_GENESIS_POINT_TXID = "57faede9e107f3af74bb93e730bc1dfd07f81b166a974068f823d30d9eb10111";
    protected static final int ROYLLO_COIN_GENESIS_POINT_VOUT = 1;
    protected static final String ROYLLO_COIN_NAME = "roylloCoin";
    protected static final String ROYLLO_COIN_META_DATA_HASH = "0c482467dfb29000804e044c4c6044b487c0280082002d4611a8ba7f22703d63";
    protected static final String ROYLLO_COIN_ASSET_ID = "f9dd292bb211dae8493645150b36efa990841b11038d026577440d2616d1ec32";
    protected static final int ROYLLO_COIN_OUTPUT_INDEX = 0;
    protected static final int ROYLLO_COIN_GENESIS_VERSION = 0;

    protected static final AssetType ROYLLO_COIN_ASSET_TYPE = NORMAL;
    protected static final BigInteger ROYLLO_COIN_AMOUNT = BigInteger.valueOf(999L);
    protected static final int ROYLLO_COIN_LOCK_TIME = 0;
    protected static final int ROYLLO_COIN_RELATIVE_LOCK_TIME = 0;

    protected static final String ROYLLO_COIN_SCRIPT_KEY = "025d615b377761a5bfcfe84f0f11afd35837a68f702dd8a0cac0a2a4052b20d211";
    protected static final int ROYLLO_COIN_SCRIPT_VERSION = 0;

    protected static final String ROYLLO_COIN_RAW_GROUP_KEY = "033b8449aae83eb0ed04f7952108637d078836bb5d3353a33b6486248b401f60da";
    protected static final String ROYLLO_COIN_TWEAKED_GROUP_KEY = "02260e9ffbe7fdabe746b4ba4c3b86c8f237d7946f116949e93db77d5e0357c13d";
    protected static final String ROYLLO_COIN_ASSET_ID_SIG = "d0d86f5c646fcca990b8ceaf7a480762c92900351ae84e8135740fa92a66c8c0a4e509c8d10df8913924a583d74e9cf25ccd56eb2552c3b92a8c3c9ebe8cca97";


    protected static final String ROYLLO_COIN_ANCHOR_TX = "020000000001021101b19e0dd323f86840976a161bf807fd1dbc30e793bb74aff307e1e9edfa570100000000ffffffffdc62e81401abfec546a45213c65c9910466f06ea1b831b017c13edfa71d775b10200000000ffffffff02e80300000000000022512040b3ddefc0df09b89fd1379a3bd246bf2827ed461506a5899dc230324d0aa158bc05000000000000225120d7ab0deb6185088278a66f5599be41510db531fd7f2df39e3b8558e2d2b65b0602473044022023f92ca5b7289d5cfb2ae415b91efa8bb9758d97b78b8ad17c81ce43fa9d262a02205c790874cbd4dae139f1d3e2b029bb97c4bf13bff0bc80127dacf9e917a37ed5012102cf948a447b6b49ccf83754b2455b7c6f77a9daeb1bb192f17ae34d3f52182a1a024630430220698a8f0543c97775ece1ad26a63f99d837ff9fd3b136e5c43cc984c37a75b3dd021f4d183f9811ac7a7cde904c795f68f96bda845efb5493a95582b60a93ca55c201210305c9ac7185c67621b7534e700d9592863f07ae5b9add38da9576e4a06a66c10000000000";
    protected static final String ROYLLO_COIN_ANCHOR_TX_ID = "0324ddf7ec79711a340a04e1ee3cee53005e046b4e0de31d498cc586ba9181c7";
    protected static final String ROYLLO_COIN_ANCHOR_BLOCK_HASH = "0000000000006b8c5e2e1a5658a841d8a20f839ba710120ecc323c8d12b10632";
    protected static final String ROYLLO_COIN_ANCHOR_OUTPOINT = "0324ddf7ec79711a340a04e1ee3cee53005e046b4e0de31d498cc586ba9181c7:0";
    protected static final String ROYLLO_COIN_INTERNAL_KEY = "026ad322cc8a05cf5723bf8aeb5c778c6462146e573af182ba20c6bed53ea29ae6";
    protected static final String ROYLLO_COIN_MERKLE_ROOT = "cd773bc1a59f3b641e819b51944d1bcf10e4676cba0620ebe3b064c5d0a777da";
    protected static final String ROYLLO_COIN_TAPSCRIPT_SIBLING = "";

    protected static final String ROYLLO_COIN_PROOF_ID = "09fcd6349cceea648dc00545846e40b50efdf3c9e27e3d7feb43103f6e593576";
    protected static final String ROYLLO_COIN_RAW_PROOF = "0000000001fd051400241101b19e0dd323f86840976a161bf807fd1dbc30e793bb74aff307e1e9edfa570000000101500000d6224f34b13ded7327694942bf00a9060938f759a544e98e7b0139120000000000002d2c483ab11c46d36e302cae0e225c790ce201449138186b3dc7e83e72652a594816a764ffff001d274d807202fd0189020000000001021101b19e0dd323f86840976a161bf807fd1dbc30e793bb74aff307e1e9edfa570100000000ffffffffdc62e81401abfec546a45213c65c9910466f06ea1b831b017c13edfa71d775b10200000000ffffffff02e80300000000000022512040b3ddefc0df09b89fd1379a3bd246bf2827ed461506a5899dc230324d0aa158bc05000000000000225120d7ab0deb6185088278a66f5599be41510db531fd7f2df39e3b8558e2d2b65b0602473044022023f92ca5b7289d5cfb2ae415b91efa8bb9758d97b78b8ad17c81ce43fa9d262a02205c790874cbd4dae139f1d3e2b029bb97c4bf13bff0bc80127dacf9e917a37ed5012102cf948a447b6b49ccf83754b2455b7c6f77a9daeb1bb192f17ae34d3f52182a1a024630430220698a8f0543c97775ece1ad26a63f99d837ff9fd3b136e5c43cc984c37a75b3dd021f4d183f9811ac7a7cde904c795f68f96bda845efb5493a95582b60a93ca55c201210305c9ac7185c67621b7534e700d9592863f07ae5b9add38da9576e4a06a66c1000000000003c206e47b7ffac0a46c638ebd66bd72c07c93059cb9dcacd5474b4d29bb0f1cc661ef12dc3d91a992c4d4800fadc4593e665783966e9ea18257e7d1d187ee3f70a3a53f660f84796da9dccf2521f2bc0caddb627789062cca22e8de926f1e2327370f38e8899c9da3fcc32ccbccb65ccac88e253398c0d447323a456813bf08f7b22bd1af29b3bec54a12010c5bcd4a545e0bc461507236adc7fef624b70042f53a0ebf264c2dfde363f7e68820f57767962e412238ce2ee06ace92795f496ee1c5eb2e04fd015600010001541101b19e0dd323f86840976a161bf807fd1dbc30e793bb74aff307e1e9edfa57000000010a726f796c6c6f436f696e0c482467dfb29000804e044c4c6044b487c0280082002d4611a8ba7f22703d6300000000000201000303fd03e70669016700650000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080200000921025d615b377761a5bfcfe84f0f11afd35837a68f702dd8a0cac0a2a4052b20d2110a6102260e9ffbe7fdabe746b4ba4c3b86c8f237d7946f116949e93db77d5e0357c13dd0d86f5c646fcca990b8ceaf7a480762c92900351ae84e8135740fa92a66c8c0a4e509c8d10df8913924a583d74e9cf25ccd56eb2552c3b92a8c3c9ebe8cca97059f0004000000000121026ad322cc8a05cf5723bf8aeb5c778c6462146e573af182ba20c6bed53ea29ae60274004900010001209e4f59a9e6c363a266472043f21f2f11b0b7f206f2abd8760d555832f16cf63f02220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0630012e00040000000101210264e26d9bcccf4e442e08a8bab1fc2a0b64fcb83b34cbba1fe4b0404c3fd110980303020101081c0001000117526f796c6c6f20636f696e20666f7220746573746e657401bc6c08537fd418062422c4c2803bca5cc7edba3fd5693f5d3f6370a45d0d7d";

    /* ============================================================================================================== */
    /* Unknown Royllo coin (living on testnet). */
    protected static final String UNKNOWN_ROYLLO_COIN_RAW_PROOF = "0000000001fd03d40024750e110bb206b6de993ff90cfe9936b112e42b9f5c0afe2db929c07df18572b90000000101500000002080b8233c36f756820bca031a1af25171256708b74db796fc090000000000000051ccbb70dd4f539ee43aa2497b2e574fe933cd6f8e99bdeefc7a14fb903de073c77bff63ffff001db224071402ea02000000000101750e110bb206b6de993ff90cfe9936b112e42b9f5c0afe2db929c07df18572b90100000000ffffffff02e80300000000000022512074381d0361b8bb274bbec7ae6ef19578e2c9c6b789d9f6a390920bbb6a816bc9890700000000000016001443e6aa57dd19692fdb4ed0d2fe4395f9073ac9d00247304402200312366d908deb99e44849b1ad34ad2066436a657475ff4a304dc9d6c494635b0220544a9154e234f0deb5456775b5b5e253e93edb359703705ead13434147d91abf012102b959655e63ecfe92589f45672305ab3b9842fabb893679cb49677b40965ac20c0000000003e2072ad5e87840f0f77f575d97a52ea6ac07304878255c1cd7bd43ad800cb136aa45cc4fb6dd4cc8ca29cd961f77f55996c56c3b9b7bcae769c8b9d31fb5ae0e0cd0371ab880257eebe8e1d1ea875c5a4e836c8cf080f30a446ced8c74dae02bce211172847e5bdeba4c82491400fe18532579f4a10322e10232bc391a914e3ad7485857253f4a648a88ab5cae518c73a763e13580405f3077dd1e2b665eb6002f33e78b44907726edcf4c8ab9c658598b24910a70a2fd65a9c92638898d84a3b49e3d5a6c6da1cc2df751b03f72190aeac38fc855462e2dfa897ac00d57083bdf044b04e9000100014a750e110bb206b6de993ff90cfe9936b112e42b9f5c0afe2db929c07df18572b90000000111756e6b6e6f776e526f796c6c6f436f696e0e5573656420627920526f796c6c6f00000000000201000303fd03ea066901670065000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000008020000092102bd5b831d6980ce7a1c342953d97fe73f7d150667b05e39d0466ba66efda380dc059f000400000000012103153c70ab08b260e17ad3d52d517544d4168041257dba377ca1b4cfc3a2de7e850274004900010001209eaee900fc3948eda143238f220372a61b535c6b1b984b6b26acd4c014537c1502220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff9c221f59e840166a09cd365da351d907b18febc8f71fa2656b7bb60da8c00bbd";
    protected static final int UNKNOWN_ROYLLO_COIN_VERSION = 0;
    protected static final String UNKNOWN_ROYLLO_COIN_GENESIS_POINT_TXID = "b97285f17dc029b92dfe0a5c9f2be412b13699fe0cf93f99deb606b20b110e75";
    protected static final int UNKNOWN_ROYLLO_COIN_GENESIS_POINT_VOUT = 1;
    protected static final String UNKNOWN_ROYLLO_COIN_NAME = "unknownRoylloCoin";
    protected static final String UNKNOWN_ROYLLO_COIN_META = "5573656420627920526f796c6c6f";
    protected static final String UNKNOWN_ROYLLO_COIN_ASSET_ID = "9eaee900fc3948eda143238f220372a61b535c6b1b984b6b26acd4c014537c15";
    protected static final int UNKNOWN_ROYLLO_COIN_OUTPUT_INDEX = 0;
    protected static final String UNKNOWN_ROYLLO_COIN_GENESIS_BOOTSTRAP_INFORMATION = "750e110bb206b6de993ff90cfe9936b112e42b9f5c0afe2db929c07df18572b90000000111756e6b6e6f776e526f796c6c6f436f696e0e5573656420627920526f796c6c6f0000000000";
    protected static final int UNKNOWN_ROYLLO_COIN_GENESIS_VERSION = 0;
    protected static final AssetType UNKNOWN_ROYLLO_COIN_ASSET_TYPE = NORMAL;
    protected static final BigInteger UNKNOWN_ROYLLO_COIN_AMOUNT = BigInteger.valueOf(1002);
    protected static final int UNKNOWN_ROYLLO_COIN_LOCK_TIME = 0;
    protected static final int UNKNOWN_ROYLLO_COIN_RELATIVE_LOCK_TIME = 0;
    protected static final int UNKNOWN_ROYLLO_COIN_SCRIPT_VERSION = 0;
    protected static final String UNKNOWN_ROYLLO_COIN_SCRIPT_KEY = "02bd5b831d6980ce7a1c342953d97fe73f7d150667b05e39d0466ba66efda380dc";
    protected static final String UNKNOWN_ROYLLO_COIN_ANCHOR_TX = "02000000000101750e110bb206b6de993ff90cfe9936b112e42b9f5c0afe2db929c07df18572b90100000000ffffffff02e80300000000000022512074381d0361b8bb274bbec7ae6ef19578e2c9c6b789d9f6a390920bbb6a816bc9890700000000000016001443e6aa57dd19692fdb4ed0d2fe4395f9073ac9d00247304402200312366d908deb99e44849b1ad34ad2066436a657475ff4a304dc9d6c494635b0220544a9154e234f0deb5456775b5b5e253e93edb359703705ead13434147d91abf012102b959655e63ecfe92589f45672305ab3b9842fabb893679cb49677b40965ac20c00000000";
    protected static final String UNKNOWN_ROYLLO_COIN_ANCHOR_TX_ID = "db848f3114a248aed35008febbf04505652cb296726d4e1a998d08ca351e4839";
    protected static final String UNKNOWN_ROYLLO_COIN_ANCHOR_BLOCK_HASH = "0000000062f2e314fb8ff7cf691a6ac31a4525920234045d3b50c0f2d406efe7";
    protected static final String UNKNOWN_ROYLLO_COIN_ANCHOR_OUTPOINT = "db848f3114a248aed35008febbf04505652cb296726d4e1a998d08ca351e4839";
    protected static final String UNKNOWN_ROYLLO_COIN_ANCHOR_INTERNAL_KEY = "03153c70ab08b260e17ad3d52d517544d4168041257dba377ca1b4cfc3a2de7e85";
    protected static final String UNKNOWN_ROYLLO_COIN_TX_MERKLE_PROOF = "072ad5e87840f0f77f575d97a52ea6ac07304878255c1cd7bd43ad800cb136aa45cc4fb6dd4cc8ca29cd961f77f55996c56c3b9b7bcae769c8b9d31fb5ae0e0cd0371ab880257eebe8e1d1ea875c5a4e836c8cf080f30a446ced8c74dae02bce211172847e5bdeba4c82491400fe18532579f4a10322e10232bc391a914e3ad7485857253f4a648a88ab5cae518c73a763e13580405f3077dd1e2b665eb6002f33e78b44907726edcf4c8ab9c658598b24910a70a2fd65a9c92638898d84a3b49e3d5a6c6da1cc2df751b03f72190aeac38fc855462e2dfa897ac00d57083bdf044b";
    protected static final String UNKNOWN_ROYLLO_COIN_INCLUSION_PROOF = "000400000000012103153c70ab08b260e17ad3d52d517544d4168041257dba377ca1b4cfc3a2de7e850274004900010001209eaee900fc3948eda143238f220372a61b535c6b1b984b6b26acd4c014537c1502220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";

    /* ============================================================================================================== */
    /* active royllo coin (living on testnet). */
    protected static final long ACTIVE_ROYLLO_COIN_ID = 10000;
    protected static final String ACTIVE_ROYLLO_COIN_ASSET_ID = "1781a8879353ab2f8bb70dcf96f5b0ff620a987cf1044b924d6e3c382e1e5413";
    protected static final String ACTIVE_ROYLLO_COIN_PROOF_1_RAWPROOF = "0000000001fd0393002439481e35ca088d991a4e6d7296b22c650545f0bbfe0850d3ae48a214318f84db00000001015004e00020e6c99a0dff85ede53ea46fef1c2c7c06fb0e2f4a904e43f21f00000000000000dd5aa62693ed7a74bb2fe8c449fc358b48717b4ba7c2fa000a91e199b6d112926b7dff63bcfe3319ba3fe85f02ea0200000000010139481e35ca088d991a4e6d7296b22c650545f0bbfe0850d3ae48a214318f84db0100000000ffffffff026f02000000000000160014b3cb6391af7e94b41475cb925e1eafed57c318cae803000000000000225120541c599eae0b80c2c7c5c7a17b75b147a0fe20663277bbbd49247e87b9b1c1370247304402204afb9a04135bc36e9062e8e0cafad11a58fc2e13f6dcf01447938faced44b53e02202492dc504f6b5577d4add1ea550fad8f7357f7c7215a7d5ec21e14269f270a5e0121027ebfbaf2f6612b4819b188f3b80386d5ddf3d4c55c5f4af8c688d97b8984b0d60000000003a205e44c946dc58fca94bfc32e4c55b7f4aad4a3742246453617d4e56a314f9949c95cc123946cb7bc245bc43c8e0b3c29317c690504a0cfa7b2e0c790731184d0f5b7fb34b383e47b12e5335ef4e37d3dbe1eca30d9ba62805ac8344efee4871ff684af935b1aff57d8aef1dc0fed4862a2965529a0c9f8a7d3572d8fa513303c744f7ba713278679e08bb0fd955df359de9605b6e97b6e78cc44ce67bfc84c76151104e8000100014939481e35ca088d991a4e6d7296b22c650545f0bbfe0850d3ae48a214318f84db0000000110616374697665526f796c6c6f436f696e0e5573656420627920526f796c6c6f00000001000201000303fd03eb0669016700650000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080200000921024e9d77ff1df871af183419a6cfd308235f512717f13da57dbf045a4a8c2ca5cc059f000400000001012103bea9941963648cfaaa2981d68ebf209e20b3e68287d94371805832e9624014290274004900010001201781a8879353ab2f8bb70dcf96f5b0ff620a987cf1044b924d6e3c382e1e541302220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8d2726a957285fe1e6f4c9916deb2086f5cd3fa67327caa039dbc85d57c9576f";
    protected static final String ACTIVE_ROYLLO_COIN_PROOF_2_RAWPROOF = "0000000002fd0393002439481e35ca088d991a4e6d7296b22c650545f0bbfe0850d3ae48a214318f84db00000001015004e00020e6c99a0dff85ede53ea46fef1c2c7c06fb0e2f4a904e43f21f00000000000000dd5aa62693ed7a74bb2fe8c449fc358b48717b4ba7c2fa000a91e199b6d112926b7dff63bcfe3319ba3fe85f02ea0200000000010139481e35ca088d991a4e6d7296b22c650545f0bbfe0850d3ae48a214318f84db0100000000ffffffff026f02000000000000160014b3cb6391af7e94b41475cb925e1eafed57c318cae803000000000000225120541c599eae0b80c2c7c5c7a17b75b147a0fe20663277bbbd49247e87b9b1c1370247304402204afb9a04135bc36e9062e8e0cafad11a58fc2e13f6dcf01447938faced44b53e02202492dc504f6b5577d4add1ea550fad8f7357f7c7215a7d5ec21e14269f270a5e0121027ebfbaf2f6612b4819b188f3b80386d5ddf3d4c55c5f4af8c688d97b8984b0d60000000003a205e44c946dc58fca94bfc32e4c55b7f4aad4a3742246453617d4e56a314f9949c95cc123946cb7bc245bc43c8e0b3c29317c690504a0cfa7b2e0c790731184d0f5b7fb34b383e47b12e5335ef4e37d3dbe1eca30d9ba62805ac8344efee4871ff684af935b1aff57d8aef1dc0fed4862a2965529a0c9f8a7d3572d8fa513303c744f7ba713278679e08bb0fd955df359de9605b6e97b6e78cc44ce67bfc84c76151104e8000100014939481e35ca088d991a4e6d7296b22c650545f0bbfe0850d3ae48a214318f84db0000000110616374697665526f796c6c6f436f696e0e5573656420627920526f796c6c6f00000001000201000303fd03eb0669016700650000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080200000921024e9d77ff1df871af183419a6cfd308235f512717f13da57dbf045a4a8c2ca5cc059f000400000001012103bea9941963648cfaaa2981d68ebf209e20b3e68287d94371805832e9624014290274004900010001201781a8879353ab2f8bb70dcf96f5b0ff620a987cf1044b924d6e3c382e1e541302220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8d2726a957285fe1e6f4c9916deb2086f5cd3fa67327caa039dbc85d57c9576ffd05a60024d7bd7a50f78b9e6a0621c34175bc390b091070e3532908660ad19ad2061cb1600000000101500000002056e41b012000c3de4265eac3671a8278ab65cbb05dfd66586a8178bf0000000094b2a4d08f54f11f05276326894ba9cc7a27a3d99247e669bd505422e8e8330c308bff63ffff001de1101dd102fd0180020000000001024186b51fb5050ce20332a18c940934faaf7b7f80874a9d9889b456a8927a20050200000000ffffffffd7bd7a50f78b9e6a0621c34175bc390b091070e3532908660ad19ad2061cb16001000000000000000003e8030000000000002251207d84b124b559f1a0ab396cec2677d5a27a140caaef680cfdbecd7a16a4593762e8030000000000002251209588124b01b2a1f00db66c22b3c5560d58a7f6f8c09a98878723e87ee6eb10a3c90c0000000000001600148cb54fe42666c5e008316c342aa58c0d803392ca0247304402203b09ac49a17faf2c1bed11faccba212cfcfb01eb962656b9fd28f4a60fdc81980220561c9053d536e8cdcb771c13ba5957497e421c5ac6456b0c5be76ef908fc60c8012103219e2594f427e338690c3f51409706439d8ab83fde02d18d0c28ec55c285801c01407eb8a1f6bf214a8471f72a894cc99e6fe756b8f2258a6c786cb825767fc2762d2e1320da2eecabaa92872fa03d584d52e5ff5c80056be1488f44cc0aeb50559c0000000003e207938006468f02d2d507be06a19e95dcb745c0c80d2f909b699b1e9be7d51ab20f013260064752b181c2b7211d73f34b6a898c7728eed82e4fa837d3032a0f9f940b660b9ab0110750f38696f8afb8c9e1efe82f134bb264ccb624d2a89afde4d9eb44c9b74161a524df3f8d0cee247be9984f6694ca31766d2a12470bfc4e0b5c33b2659a8b50f188deb330af74218788c0717f66f4f56832b2ec568e571e97dd0fc080ed0b6465878f8547163ddf71a6825290b08846484fab9ab9f84895feedd0ff6506a12b5c5e718cd7ee09f8474c852587b7a6c693819fd4add29752f6796304fd0156000100014939481e35ca088d991a4e6d7296b22c650545f0bbfe0850d3ae48a214318f84db0000000110616374697665526f796c6c6f436f696e0e5573656420627920526f796c6c6f00000001000201000303fd038706ad01ab0065d7bd7a50f78b9e6a0621c34175bc390b091070e3532908660ad19ad2061cb160000000011781a8879353ab2f8bb70dcf96f5b0ff620a987cf1044b924d6e3c382e1e5413024e9d77ff1df871af183419a6cfd308235f512717f13da57dbf045a4a8c2ca5cc014201402b9b32468fc0a3e8809763fdd191a720aba0f6fc0e46642e192484114cf1b002ac82e56cb4e8aaf8fcd9c7742e2b4fa6c195af91cedaa1a670ab46cb4a3811920728b48931d124c3c1911c5671ffabc9477c82b62cd62d3920d2cadd5878d31f882100000000000003eb08020000092102b0664f13bfc71ddcb8e0eb004b5b486cda6d0dfd33340b85d43f618b484e1517059f000400000000012102d180fa5fde1f070a9df166d1cc4c0ec8fd3ccd57da8744a4fed8a1a1578cf1ae0274004900010001201781a8879353ab2f8bb70dcf96f5b0ff620a987cf1044b924d6e3c382e1e541302220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff06c901c70004000000010121030411db4d023a8d55607fedc562d519b1854af7752d9e1f00279213380d439e88029c007100010001201781a8879353ab2f8bb70dcf96f5b0ff620a987cf1044b924d6e3c382e1e5413024a000151f716888109ae4abf80fa5120d1eaca3e36ec8a2a45849e573001373de715170000000000000064ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffbf012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff48740a7a6841d86a30a38719fce36e4e30393e63acd4e5132ee2544dce048d37";
    protected static final String ACTIVE_ROYLLO_COIN_PROOF_3_RAWPROOF = "0000000002fd0393002439481e35ca088d991a4e6d7296b22c650545f0bbfe0850d3ae48a214318f84db00000001015004e00020e6c99a0dff85ede53ea46fef1c2c7c06fb0e2f4a904e43f21f00000000000000dd5aa62693ed7a74bb2fe8c449fc358b48717b4ba7c2fa000a91e199b6d112926b7dff63bcfe3319ba3fe85f02ea0200000000010139481e35ca088d991a4e6d7296b22c650545f0bbfe0850d3ae48a214318f84db0100000000ffffffff026f02000000000000160014b3cb6391af7e94b41475cb925e1eafed57c318cae803000000000000225120541c599eae0b80c2c7c5c7a17b75b147a0fe20663277bbbd49247e87b9b1c1370247304402204afb9a04135bc36e9062e8e0cafad11a58fc2e13f6dcf01447938faced44b53e02202492dc504f6b5577d4add1ea550fad8f7357f7c7215a7d5ec21e14269f270a5e0121027ebfbaf2f6612b4819b188f3b80386d5ddf3d4c55c5f4af8c688d97b8984b0d60000000003a205e44c946dc58fca94bfc32e4c55b7f4aad4a3742246453617d4e56a314f9949c95cc123946cb7bc245bc43c8e0b3c29317c690504a0cfa7b2e0c790731184d0f5b7fb34b383e47b12e5335ef4e37d3dbe1eca30d9ba62805ac8344efee4871ff684af935b1aff57d8aef1dc0fed4862a2965529a0c9f8a7d3572d8fa513303c744f7ba713278679e08bb0fd955df359de9605b6e97b6e78cc44ce67bfc84c76151104e8000100014939481e35ca088d991a4e6d7296b22c650545f0bbfe0850d3ae48a214318f84db0000000110616374697665526f796c6c6f436f696e0e5573656420627920526f796c6c6f00000001000201000303fd03eb0669016700650000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080200000921024e9d77ff1df871af183419a6cfd308235f512717f13da57dbf045a4a8c2ca5cc059f000400000001012103bea9941963648cfaaa2981d68ebf209e20b3e68287d94371805832e9624014290274004900010001201781a8879353ab2f8bb70dcf96f5b0ff620a987cf1044b924d6e3c382e1e541302220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8d2726a957285fe1e6f4c9916deb2086f5cd3fa67327caa039dbc85d57c9576ffd07830024d7bd7a50f78b9e6a0621c34175bc390b091070e3532908660ad19ad2061cb1600000000101500000002056e41b012000c3de4265eac3671a8278ab65cbb05dfd66586a8178bf0000000094b2a4d08f54f11f05276326894ba9cc7a27a3d99247e669bd505422e8e8330c308bff63ffff001de1101dd102fd0180020000000001024186b51fb5050ce20332a18c940934faaf7b7f80874a9d9889b456a8927a20050200000000ffffffffd7bd7a50f78b9e6a0621c34175bc390b091070e3532908660ad19ad2061cb16001000000000000000003e8030000000000002251207d84b124b559f1a0ab396cec2677d5a27a140caaef680cfdbecd7a16a4593762e8030000000000002251209588124b01b2a1f00db66c22b3c5560d58a7f6f8c09a98878723e87ee6eb10a3c90c0000000000001600148cb54fe42666c5e008316c342aa58c0d803392ca0247304402203b09ac49a17faf2c1bed11faccba212cfcfb01eb962656b9fd28f4a60fdc81980220561c9053d536e8cdcb771c13ba5957497e421c5ac6456b0c5be76ef908fc60c8012103219e2594f427e338690c3f51409706439d8ab83fde02d18d0c28ec55c285801c01407eb8a1f6bf214a8471f72a894cc99e6fe756b8f2258a6c786cb825767fc2762d2e1320da2eecabaa92872fa03d584d52e5ff5c80056be1488f44cc0aeb50559c0000000003e207938006468f02d2d507be06a19e95dcb745c0c80d2f909b699b1e9be7d51ab20f013260064752b181c2b7211d73f34b6a898c7728eed82e4fa837d3032a0f9f940b660b9ab0110750f38696f8afb8c9e1efe82f134bb264ccb624d2a89afde4d9eb44c9b74161a524df3f8d0cee247be9984f6694ca31766d2a12470bfc4e0b5c33b2659a8b50f188deb330af74218788c0717f66f4f56832b2ec568e571e97dd0fc080ed0b6465878f8547163ddf71a6825290b08846484fab9ab9f84895feedd0ff6506a12b5c5e718cd7ee09f8474c852587b7a6c693819fd4add29752f6796304fd0292000100014939481e35ca088d991a4e6d7296b22c650545f0bbfe0850d3ae48a214318f84db0000000110616374697665526f796c6c6f436f696e0e5573656420627920526f796c6c6f000000010002010003016406fd021301fd020f0065000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002fd01a44a00018205ee72be16f3b195b465cb095b97e811edddabb0dcec197f9e682d05a31ca00000000000000387ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff7ffd0156000100014939481e35ca088d991a4e6d7296b22c650545f0bbfe0850d3ae48a214318f84db0000000110616374697665526f796c6c6f436f696e0e5573656420627920526f796c6c6f00000001000201000303fd038706ad01ab0065d7bd7a50f78b9e6a0621c34175bc390b091070e3532908660ad19ad2061cb160000000011781a8879353ab2f8bb70dcf96f5b0ff620a987cf1044b924d6e3c382e1e5413024e9d77ff1df871af183419a6cfd308235f512717f13da57dbf045a4a8c2ca5cc014201402b9b32468fc0a3e8809763fdd191a720aba0f6fc0e46642e192484114cf1b002ac82e56cb4e8aaf8fcd9c7742e2b4fa6c195af91cedaa1a670ab46cb4a3811920728b48931d124c3c1911c5671ffabc9477c82b62cd62d3920d2cadd5878d31f882100000000000003eb08020000092102b0664f13bfc71ddcb8e0eb004b5b486cda6d0dfd33340b85d43f618b484e151708020000092102576e6cf95f0d7724f2e17afcd74a690231bf4e1ecb1963229af3fe33edcd58ca059f0004000000010121030411db4d023a8d55607fedc562d519b1854af7752d9e1f00279213380d439e880274004900010001201781a8879353ab2f8bb70dcf96f5b0ff620a987cf1044b924d6e3c382e1e541302220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff06c901c7000400000000012102d180fa5fde1f070a9df166d1cc4c0ec8fd3ccd57da8744a4fed8a1a1578cf1ae029c007100010001201781a8879353ab2f8bb70dcf96f5b0ff620a987cf1044b924d6e3c382e1e5413024a00019fe1a55b2c9813569b7c7a3bf514bafc0cf43748d646a2bf39848870b06677a70000000000000387ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffbf012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff079f000400000000012102d180fa5fde1f070a9df166d1cc4c0ec8fd3ccd57da8744a4fed8a1a1578cf1ae0274004900010001201781a8879353ab2f8bb70dcf96f5b0ff620a987cf1044b924d6e3c382e1e541302220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff012700010001220000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe362126b922539fc3451739b11745035fcc950ee86d0edace720399be6951c7e";

}