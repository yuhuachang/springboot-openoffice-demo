package com.example.springbootopenofficedemo;

import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeUtils;
import org.jodconverter.local.JodConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OfficeDocumentProcessor {

    private final String officeHome;

    public OfficeDocumentProcessor(String env) {
        switch(env) {
            case "amd64/debian":
            case "openjdk:11":
                officeHome = "/usr/lib/libreoffice";
                break;
            case "amd64/fedora":
            case "public.ecr.aws/lambda/java:11":
                officeHome = "/usr/lib64/libreoffice";
                break;
            default:
                officeHome = null;
                break;
        };
    }

    public byte[] convertExcelToPdf(byte[] bytes) throws IOException, OfficeException {

        final LocalOfficeManager officeManager = LocalOfficeManager
            .builder()
            .officeHome(officeHome)
            .install()
            .build();

        try {
            officeManager.start();
            try (var out = new ByteArrayOutputStream()) {
                JodConverter
                    .convert(new ByteArrayInputStream(bytes), true)
                    .as(DefaultDocumentFormatRegistry.XLSX)
                    .to(out, false)
                    .as(DefaultDocumentFormatRegistry.PDF)
                    .execute();
                return out.toByteArray();
            }
        } finally {
            OfficeUtils.stopQuietly(officeManager);
        }
    }
}
