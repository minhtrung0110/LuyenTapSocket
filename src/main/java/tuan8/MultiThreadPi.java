package tuan8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.atomic.AtomicInteger;
// Cau 1
public class MultiThreadPi {
    // Biến toàn cục chung để lưu tổng số points trong đường tròn
    private static AtomicInteger totalInCircle = new AtomicInteger(0);

    // Hàm xác định xem một point có nằm trong đường tròn hay không
    private static boolean isInCircle(double x, double y) {
        return x * x + y * y <= 1;
    }

    // Lớp chứa hàm phát sinh và đếm số points trong đường tròn
    // Số points được tăng lên biến toàn cục total_in_circle
    public static class PointsGenerator implements Runnable {
        private int numPoints;

        public PointsGenerator(int numPoints) {
            this.numPoints = numPoints;
        }

        @Override
        public void run() {
            for (int i = 0; i < numPoints; i++) {
                double x = Math.random() * 2 - 1;
                double y = Math.random() * 2 - 1;
                if (isInCircle(x, y)) {
                    totalInCircle.incrementAndGet();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Khởi tạo 2 thread và chạy cùng lúc
        Thread t1 = new Thread(new PointsGenerator(1000000));
        Thread t2 = new Thread(new PointsGenerator(1000000));
        t1.start();
        t2.start();

        // Đợi cho 2 thread hoàn tất
        t1.join();
        t2.join();

        // Tính toán và in ra kết quả
        double pi = 4.0 * totalInCircle.get() / (1000000 * 2);
        System.out.println("Giá trị Pi dựa trên phương pháp Monte Carlo: " + pi);
    }
}
