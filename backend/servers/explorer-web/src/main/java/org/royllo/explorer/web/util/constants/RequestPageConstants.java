package org.royllo.explorer.web.util.constants;

import lombok.experimental.UtilityClass;

/**
 * Request page.
 */
@UtilityClass
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class RequestPageConstants {

    /** Request page. */
    public static final String REQUEST_PAGE = "request/view";

    /** Request - Add proof form. */
    public static final String ADD_PROOF_REQUEST_FORM_PAGE = "request/proof/add_form";

    /** Request - Saved proof request with success. */
    public static final String ADD_PROOF_REQUEST_SUCCESS_PAGE = "request/proof/add_success";

    /** Request - Add universe server form. */
    public static final String ADD_UNIVERSE_SERVER_REQUEST_FORM_PAGE = "request/universe_server/add_form";

    /** Request - Saved universe server request with success. */
    public static final String ADD_UNIVERSE_SERVER_REQUEST_SUCCESS_PAGE = "request/universe_server/add_success";

    /** Request - Claim asset ownership form. */
    public static final String CLAIM_ASSET_OWNERSHIP_REQUEST_FORM_PAGE = "/user/request/claim_asset_ownership/add_form";

    /** Request - Saved claim asset ownership request with success. */
    public static final String CLAIM_ASSET_OWNERSHIP_REQUEST_SUCCESS_PAGE = "/user/request/claim_asset_ownership/add_success";

}
