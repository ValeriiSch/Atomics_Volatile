package com.company;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter_3 = new AtomicInteger(0);
    public static AtomicInteger counter_4 = new AtomicInteger(0);
    ;
    public static AtomicInteger counter_5 = new AtomicInteger(0);
    ;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
            System.out.println(texts[i]);
        }

        Thread thread_3 = new Thread(() -> {
            for (String text : texts) {
                if (text.length() == 3) {
                    if (text.charAt(0) == text.charAt(2)) {
                        counter_3.getAndIncrement();
                    }
                }
            }
        });
        thread_3.start();

        Thread thread_4 = new Thread(() -> {
            for (String text : texts) {
                if (text.length() == 4) {
                    if ((text.charAt(0) == text.charAt(3)) && (text.charAt(1) == text.charAt(2))) {
                        counter_4.getAndIncrement();
                    }
                }
            }
        });
        thread_4.start();

        Thread thread_5 = new Thread(() -> {
            for (String text : texts) {
                if (text.length() == 5) {
                    if ((text.charAt(0) == text.charAt(4)) && (text.charAt(1) == text.charAt(3))) {
                        counter_5.getAndIncrement();
                    }
                }
            }
        });
        thread_5.start();

        thread_3.join();
        thread_4.join();
        thread_5.join();

        System.out.println("Красивых слов с длиной 3: " + counter_3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + counter_4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + counter_5.get() + " шт");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
