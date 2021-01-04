package com.smartbics;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogParse {

    private String path;
    private String fileRegexp;
    private static final Logger LOG = LoggerFactory.getLogger(LogParse.class);

    private ArrayList<Message> allErrorMessages;

    //constructor
    public LogParse(String path, String fileRegexp) {
        allErrorMessages = new ArrayList<>();
        this.path = path;
        this.fileRegexp = fileRegexp;
    }

    public void parse() {
        File[] lsResult = ls();
        if (lsResult == null) {
            LOG.error("Файлы не найдены, ls не отработал");
            return;
        }
        collectErrAllFile(lsResult);
        System.out.println(lsResult.length + " - log file was PARSE success ");
    }

    //analog linux ls
    private File[] ls() {
        File[] ls;
        File dir = new File(path);
        ls = dir.listFiles((dir1, name) -> name.matches(fileRegexp)); //regular for xxx.log
        return ls;
    }

    //all files
    private void collectErrAllFile(File[] files) {
        if (files == null) {
            LOG.error("Файлы не найдены, ls не отработал");
            return;
        }
        for (File file : files) {
            collectErrOneFile(file);
        }
        Collections.sort(allErrorMessages);
    }

    //1 file
    private void collectErrOneFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s = br.readLine();
            while (s != null) {
                collectErrOneRow(s);
                s = br.readLine();
            }
        } catch (FileNotFoundException e) {
            LOG.error("Файл не найден " + file.getPath(), e);
        } catch (IOException e) {
            LOG.error("IOException " + file.getPath(), e);
        }
    }

    //1 row
    private void collectErrOneRow(String row) {
        String[] r = row.split(";");
        if (r[1].equals("ERROR")) {
            LocalDateTime timestamp = LocalDateTime.parse(r[0]);
            String levelstamp = r[1];
            String descstamp = r[2];
            Message error = new Message(timestamp, levelstamp, descstamp);
            allErrorMessages.add(error);
        }
    }

    public int getCountErrors() {
        return allErrorMessages.size();
    }

    public List<Message> getErrors() {
        return allErrorMessages;
    }
}
