//package com.example.gymAp.health;
//
//
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//
//@Component
//public class DiskSpaceHealthIndicator implements HealthIndicator {
//
//    private static final long MIN_DISK_SPACE_THRESHOLD = 1024 * 1024 * 100; // 100 MB
//
//    @Override
//    public Health health() {
//        File root = new File("/");
//        long freeSpace = root.getFreeSpace();
//
//        if (freeSpace >= MIN_DISK_SPACE_THRESHOLD) {
//            return Health.up().build();
//        } else {
//            return Health.down().withDetail("freeSpace", freeSpace).build();
//        }
//    }
//}