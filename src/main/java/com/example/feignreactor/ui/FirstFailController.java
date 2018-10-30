package com.example.feignreactor.ui;

import com.example.feignreactor.application.HttpbinResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CopyOnWriteArraySet;

@RestController
@RequestMapping("/first-fail")
public class FirstFailController {
    private final Logger log = LoggerFactory.getLogger(FirstFailController.class);
    private final CopyOnWriteArraySet<String> uids = new CopyOnWriteArraySet<>();

    /**
     * 최초 접근 시에는 오류. 그 이후에는 성공
     * <p>
     * retry 테스트를 위한 API
     * </p>
     */
    @GetMapping("/{uid}")
    public HttpbinResponse get(@PathVariable("uid") String uid) {
        if (uids.contains(uid)) {
            log.info("After first success : {}", uid);
            return new HttpbinResponse(uid, "localhost");
        }
        uids.add(uid);
        throw new UnsupportedOperationException("First fail : " + uid);
    }
}
