package ar.edu.unq.tpi.util.services.spreadsheets;

import java.util.Collection;
import java.util.Map;

public interface SSConnectorStrategy {

    void setConnectionData(final String user, final String password);

    void connect();

    void parseSheet(final String sheetName);

    void parseSheet(final String spreadsheetEntryName, final String worksSheetName);

    void printInfo();

    Collection<Map<String, Container>> getValues();
}
