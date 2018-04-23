package com.radlly.response;

import com.radlly.constant.RestCodeConstants;

/**
 * Created by heng.xu on 2018/4/17.
 */
public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}
