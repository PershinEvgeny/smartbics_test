package com.smartbics;

import java.util.Map;
import java.util.stream.Collectors;

import static com.smartbics.ConfigConstants.FILE_REGEXP;
import static com.smartbics.ConfigConstants.NUM_FILES;
import static com.smartbics.ConfigConstants.NUM_ROWS_GENERATE;
import static com.smartbics.ConfigConstants.PATH;

public class Main {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        //---generate logs
        LogGen logGen = new LogGen();
        logGen.setNumFiles(NUM_FILES);
        logGen.setNumRows(NUM_ROWS_GENERATE);
        logGen.setPath(PATH);

        logGen.generateAllFileLog(); //генерация файлов логов
        //---generate logs

        LogParse parsing = new LogParse(PATH, FILE_REGEXP);
        parsing.parse();
        System.out.println(parsing.getCountErrors() + " - ERRORs");

        //-----HOUR report
        Map<String, Long> reportDiaHour = parsing.getErrors().stream()
                .collect(Collectors.groupingBy(Message::getGroupHour, Collectors.counting()));
        logGen.statisticReport(reportDiaHour);
        //-----HOUR report

        System.out.println((int) (System.currentTimeMillis() - start) + " millisec");
    }
}
