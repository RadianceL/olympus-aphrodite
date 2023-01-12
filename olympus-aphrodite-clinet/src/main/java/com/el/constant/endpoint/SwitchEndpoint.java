package com.el.constant.endpoint;

import com.el.constant.data.Response;
import com.el.constant.data.common.RequestEndpointUrl;
import com.el.constant.data.common.ResponseCodeConstants;
import com.el.constant.utils.PublicIpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 心跳端点 </br>
 * since 2020/12/1
 *
 * @author eddie
 */
@Slf4j
@RestController
public class SwitchEndpoint {

    @GetMapping(RequestEndpointUrl.ONLINE_HEARTBEAT_ENDPOINT)
    public Response<String> onlineHeartbeat() {
        return Response.ofSuccess(null);
    }

}

