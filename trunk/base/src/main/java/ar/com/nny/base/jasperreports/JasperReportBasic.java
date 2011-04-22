package ar.com.nny.base.jasperreports;

import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class JasperReportBasic {
    public static void main(final String[] args) {
        String reportName = "plantilla";
        String path = "src/main/resources/jasper/";
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            // 1-Compilamos el archivo XML y lo cargamos en memoria
            jasperReport = JasperCompileManager.compileReport(path + reportName + ".jrxml");

            // 2-Llenamos el reporte con la información y parámetros necesarios
            // (En este caso nada)
            HashMap parameters = new HashMap();
            parameters.put("NAME", "Ronny");
            parameters.put("COST", 2.5);
            
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // 3-Exportamos el reporte a pdf y lo guardamos en disco
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + reportName + ".pdf");

            System.out.println("Done!");
        } catch (JRException e) {
            e.printStackTrace();
        }

    }
}
