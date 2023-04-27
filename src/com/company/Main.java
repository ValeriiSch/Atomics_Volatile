package com.company;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter_3 = new AtomicInteger(0);
    public static AtomicInteger counter_4 = new AtomicInteger(0);
    public static AtomicInteger counter_5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
//            System.out.println(texts[i]);
        }

        Thread thread_isSameLetter = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    incCounter(text.length());
                }
            }
        });
        thread_isSameLetter.start();

        Thread thread_isGrow = new Thread(() -> {
            for (String text : texts) {
                if (isGrow(text) && !isSameLetter(text)) {
                    incCounter(text.length());
                }
            }
        });
        thread_isGrow.start();

        Thread thread_isPolindrom = new Thread(() -> {
            for (String text : texts) {
                if (isPolindrom(text) && !isSameLetter(text)) {
                    incCounter(text.length());
                }
            }
        });
        thread_isPolindrom.start();

        thread_isSameLetter.join();
        thread_isGrow.join();
        thread_isPolindrom.join();

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

    public static void incCounter(int length) {
        if (length == 3) {
            counter_3.getAndIncrement();
        } else if ((length == 4)) {
            counter_4.getAndIncrement();
        } else if ((length == 5)) {
            counter_5.getAndIncrement();
        }
    }

    public static boolean isSameLetter(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) != text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isGrow(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPolindrom(String text) {
        for (int i = 0; i < (text.length()/2); i++) {
            if (text.charAt(i) != text.charAt(text.length() - 1 - i)){
                return false;
            }
        }
        return true;
    }
}
