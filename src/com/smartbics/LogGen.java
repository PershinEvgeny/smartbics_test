package com.smartbics;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.random;

public class LogGen {
    private static final String ROW = "%s;%s;%s\n";
    private static final String[] level = {"ERROR", "WARN", "DEBUG", "INFO"};
    private static final String[] desc = {"Ошибка", "Предупреждение", "Отладка", "Информация"};

    private static final Logger LOG = LoggerFactory.getLogger(LogGen.class);

    private Random random;
    private Writer writer;
    private LocalDateTime dateTime;
    private String filename;

    private String path;
    private int numFiles;
    private int numRows;


    //constructor
    public LogGen() {
        random = new Random();
        dateTime = LocalDateTime.now();
    }

    public void statisticReport(Map<String, Long> distr) {
        try {
            filename = "Statistic.txt";
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path + "/" + filename)));
        } catch (FileNotFoundException e) {
            LOG.error("Файл " + filename + " в папке - " + path + " не найден", e);
        }
        distr.forEach((k, v) -> {
            try {
                writer.write(k + " Количество ошибок: " + v + "\n");
            } catch (IOException e) {
                LOG.error("В файле " + filename + " интервал <<" + k + ">> не может быть записан", e);
            }
        });
        try {
            writer.close();
        } catch (IOException e) {
            LOG.error("Поток файла" + filename + " не может быть закрыт", e);
        }
        System.out.println("distributionReport finish");
    }

    public void generateAllFileLog() {
        for (int i = 0; i < numFiles; i++) {
            filename = "log" + i + ".log";
            generateOneFileLog();
        }
        System.out.println(numFiles + " - log file was GENERATED success");
    }

    private void generateOneFileLog() {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path + "/" + filename)));
        } catch (FileNotFoundException e) {
            LOG.error("Файл " + filename + " в папке - " + path + " не найден", e);
        }
        for (int j = 0; j < numRows; j++) {
            try {
                writer.write(generateOneRow());
            } catch (IOException e) {
                LOG.error("В файле " + filename + " строка № " + j + " не может быть записана", e);
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            LOG.error("Поток файла" + filename + " не может быть закрыт", e);
        }
    }

    private String generateOneRow() {
        return String.format(ROW,
                randTimeStamp(),
                level[random.nextInt(4)],
                desc[random.nextInt(4)] + (random.nextInt(100)));
    }

    private String randTimeStamp() {
        dateTime = dateTime.minusSeconds((long) (random() * 100 + random() * 100 * 10 * random.nextInt(4)));
        return dateTime.toString();
    }

    //----------------Setters--------------
    public void setPath(String path) {
        this.path = path;
    }

    public void setNumFiles(int numFiles) {
        this.numFiles = numFiles;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }
    //----------------Setters--------------
}
