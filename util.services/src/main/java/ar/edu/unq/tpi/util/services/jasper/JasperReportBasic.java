package ar.edu.unq.tpi.util.services.jasper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

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
            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("Title", "Telefonos");
            
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(getBeanCollection()));

            // 3-Exportamos el reporte a pdf y lo guardamos en disco
            exportPDF(reportName, path, jasperPrint);
            
            // 4-Exportamos el reporte a exel y lo guardamos en disco
			File destFile;
			exportEXEL(reportName, path, jasperPrint);
			
			
			//create a RTF file
			exportRTF(reportName, path, jasperPrint);

            System.out.println("Done!");
        } catch (JRException e) {
            e.printStackTrace();
        }

    }


	protected static void exportRTF(String reportName, String path,
			JasperPrint jasperPrint) throws JRException {
		File destFile;

		destFile = new File(path + reportName + ".rtf");
		
		JRRtfExporter pdfExporter = new JRRtfExporter();
		
		pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		pdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
		
		pdfExporter.exportReport();
	}


	protected static void exportEXEL(String reportName, String path,
			JasperPrint jasperPrint) throws JRException {
		
		File destFile = new File(path + reportName + ".xls");
		
		JRXlsExporter xlsExporter = new JRXlsExporter();
					
		xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		xlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
		xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		
		xlsExporter.exportReport();
	}


	protected static void exportPDF(String reportName, String path,
			JasperPrint jasperPrint) throws JRException {
		JasperExportManager.exportReportToPdfFile(jasperPrint, path + reportName + ".pdf");
	}
    
    
public static ArrayList<Phone> getBeanCollection() {
		
		Phone phone1 = new Phone("home","913-906-6000");
		Phone phone2 = new Phone("work", "913-906-6001");
		Phone phone3 = new Phone("mobile", "(913-906-6002");
		
		ArrayList<Phone> phones = new ArrayList<Phone>();
		
		phones.add(phone1);
		phones.add(phone2);
		phones.add(phone3);
		return phones;
	}
}
