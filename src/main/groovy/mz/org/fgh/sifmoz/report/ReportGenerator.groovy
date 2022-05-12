package mz.org.fgh.sifmoz.report

import net.sf.jasperreports.engine.JRExporterParameter
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource
import net.sf.jasperreports.engine.export.JRXlsExporter
import net.sf.jasperreports.export.ExporterInput
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
import net.sf.jasperreports.export.SimpleXlsReportConfiguration

import java.sql.Connection

class ReportGenerator {

    static byte[] generateReport(Map<String, Object> parameters, List<Map<String, Object>> reportObjects, String reportPath ) {
        try {
            JRMapCollectionDataSource mapCollectionDataSource = new JRMapCollectionDataSource(reportObjects)
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath)
            def jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mapCollectionDataSource)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
           // render(file: byteArrayOutputStream.toByteArray(), contentType: 'application/pdf')
            //     response.setContentType("application/pdf")  //<-- you'll have to handle this dynamically at some point
            //    response.setHeader("Content-disposition", "attachment;filename=${11}")
            //   response.outputStream <<  byteArrayOutputStream.toByteArray()
        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        }
    }

    static byte[] generateReport(Map<String, Object> parameters, String reportPath, String report, Connection connection) {
        try {
            String reportUri = reportPath+"/"+report
            JasperReport jasperReport = JasperCompileManager.compileReport(reportUri)
            def jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream)

           /* JRXlsExporter xlsExporter = new JRXlsExporter();
            xlsExporter.setExporterInput(new SimpleExporterInput(xlsPrint));
            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outXlsName));
            SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
            xlsReportConfiguration.setOnePagePerSheet(false);
            xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
            xlsReportConfiguration.setDetectCellType(false);
            xlsReportConfiguration.setWhitePageBackground(false);
            xlsExporter.setConfiguration(xlsReportConfiguration);

            xlsExporter.exportReport();*/

            return byteArrayOutputStream.toByteArray()
        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        }
    }
}
