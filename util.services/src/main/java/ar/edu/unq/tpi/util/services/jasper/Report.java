package ar.edu.unq.tpi.util.services.jasper;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import ar.edu.unq.tpi.util.commons.exeption.UserException;

public class Report<T> {
    private static final String PATH = "src/main/resources/jasper/";

    private JasperReport jasperReport;

    private JasperPrint jasperPrint;

    private String reportName;

    public Report(final String reportName) {
        this.reportName = reportName;
        this.initializeJassperReport();
    }

    public void initializeJassperReport() {
        try {
            jasperReport = JasperCompileManager.compileReport(PATH + reportName + ".jrxml");
        } catch (JRException e) {
            throw new UserException(e);
        }
    }

    public void initializeJassperPrint(final List<T> objects, final HashMap<String, String> parameters) {
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                    new JRBeanCollectionDataSource(objects));
        } catch (JRException e) {
            throw new UserException(e);
        }
    }

    public File exportRTF(final String pathFile, final List<T> objects, final HashMap<String, String> parameters) {

        this.initializeJassperPrint(objects, parameters);
        File destFile;

        destFile = new File(pathFile);

        JRRtfExporter pdfExporter = new JRRtfExporter();

        pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        pdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

        try {
            pdfExporter.exportReport();
            return destFile;
        } catch (JRException e) {
            throw new UserException(e);
        }
    }

    public File exportEXEL(final String pathFile, final List<T> objects, final HashMap<String, String> parameters) {

        this.initializeJassperPrint(objects, parameters);

        File destFile = new File(pathFile);

        JRXlsExporter xlsExporter = new JRXlsExporter();

        xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
        xlsExporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);

        try {
            xlsExporter.exportReport();
            return destFile;
        } catch (JRException e) {
            throw new UserException(e);
        }
    }

    public File exportPDF(final String pathFile, final List<T> objects, final HashMap<String, String> parameters) {

        this.initializeJassperPrint(objects, parameters);

        try {
            JasperExportManager.exportReportToPdfFile(jasperPrint, pathFile);
            return new File(pathFile);
        } catch (JRException e) {
            throw new UserException(e);
        }
    }

}
