package com.wtc;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class UUIDTest {

    @Test
    public void testUUID() {
        for (int i = 0; i < 100; i++) {
            System.out.println(UUID.randomUUID().toString().replace("-", ""));
        }
    }
}
