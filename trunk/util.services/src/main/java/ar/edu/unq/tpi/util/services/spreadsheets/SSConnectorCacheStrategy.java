package ar.edu.unq.tpi.util.services.spreadsheets;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public final class SSConnectorCacheStrategy implements SSConnectorStrategy {

    private String id;

    @Override
    public void setConnectionData(final String user, final String password) {
        // nothing to do
    }


    @Override
    public void connect() {
        // nothing to do
    }


    @Override
    public void parseSheet(final String sheetName) {
        this.id = sheetName;
    }

    @Override
    public void parseSheet(final String spreadsheetEntryName, final String worksSheetName) {
        this.id = spreadsheetEntryName + "-" + worksSheetName;
    }

    @Override
    public Collection<Map<String, Container>> getValues() {
        try {
            final File file = new File(DDLGenerator.ROOT_RT_PATH() + "spreadsheets-cache/" + id);

            return (Collection<Map<String, Container>>) SerializationHelper.loadObjectFromFile(file);
        } catch (Exception e) {
            throw new GeneratorExcepction("", e);
        }
    }

    @Override
    public void printInfo() {
        // nothing to do
    }
}
