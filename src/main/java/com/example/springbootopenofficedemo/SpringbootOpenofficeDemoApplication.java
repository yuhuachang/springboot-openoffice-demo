package com.example.springbootopenofficedemo;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class SpringbootOpenofficeDemoApplication implements CommandLineRunner {

    private static final String EXCEL_FILE = "/data/FinancialSample.xlsx";
    private static final String PDF_FILE = "/data/FinancialSample.pdf";

    public static void main(String[] args) {
        SpringApplication.run(SpringbootOpenofficeDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var env = args.length > 0 ? args[0] : null;
        System.out.println("env = " + env);

        run("uname -a");

        try {
            byte[] bytes;
            try (var is = new FileInputStream(new File(EXCEL_FILE))) {
                assert is != null;
                bytes = IOUtils.toByteArray(is);
            }
            System.out.println("Read Excel " + bytes.length + " bytes.");

            byte[] pdf = new OfficeDocumentProcessor(env).convertExcelToPdf(bytes);
            System.out.println("Generate PDF " + bytes.length + " bytes.");

            try (var os = new FileOutputStream(new File(PDF_FILE))) {
                assert os != null;
                IOUtils.write(pdf, os);
            }
            System.out.println("Write " + bytes.length + " bytes.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run(String cmd) throws IOException, InterruptedException {
        System.out.println(">" + cmd);
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(cmd);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        while ((line = buf.readLine()) != null) {
            System.out.println(line);
        }
    }
}
