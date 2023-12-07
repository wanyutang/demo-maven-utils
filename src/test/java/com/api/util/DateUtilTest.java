package com.api.util;

import java.util.Date;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtilTest {

    @Test
    void testFormatDate() {
        log.debug("res: {}", DateUtil.formatDate(new Date(), DateUtil.YYYMMDDHHMMSS));
    }
}
