package ar.edu.unq.tpi.util.services.spreadsheets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.map.ListOrderedMap;

import com.google.gdata.client.GoogleAuthTokenFactory.UserToken;
import com.google.gdata.client.spreadsheet.CellQuery;
import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.Cell;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

public final class SSConnectorRefreshStrategy implements SSConnectorStrategy {

    private String user;
    private String password;
    private SpreadsheetService service;
    private SpreadsheetFeed feed;
    private final Map<Integer, Map<String, Container>> values = new ListOrderedMap<Integer, Map<String, Container>>();
    private final Map<Integer, String> headers = new ListOrderedMap<Integer, String>();

    private Collection<Map<String, Container>> result;
    private static String token = "";
    
    
    
    public SSConnectorRefreshStrategy(final String user, final String password) {
    	setConnectionData(user, password);
    	connect();
    }

    @Override
    public void setConnectionData(final String user, final String password) {
        this.user = user;
        this.password = password;
    }


    @Override
    public void connect() {
        try {
            final FeedURLFactory factory = FeedURLFactory.getDefault();

            service = new SpreadsheetService("FWK Generators");

            if (token.equals("")) {
                service.setUserCredentials(user, password);
                final UserToken auth_token = (UserToken) service.getAuthTokenFactory().getAuthToken();
                token = auth_token.getValue();
            } else {
                service.setUserToken(token);
            }
            feed = service.getFeed(factory.getSpreadsheetsFeedUrl(), SpreadsheetFeed.class);
        } catch (Exception e) {
            throw new GeneratorExcepction("PROGRAMMING", e);
        }
    }


    @Override
    public void parseSheet(final String sheetName) {
        try {
            final WorksheetEntry worksheet = CollectionUtils.find(feed.getEntries(), new Predicate<SpreadsheetEntry>() {
                @Override
                public boolean evaluate(final SpreadsheetEntry spreadsheetEntry) {
                    return sheetName.equals(spreadsheetEntry.getTitle().getPlainText());
                }
            }).getDefaultWorksheet();

            parseWorksheet(worksheet, sheetName);

        } catch (Exception e) {
            throw new GeneratorExcepction("PROGRAMMING", e);
        }
    }

    @Override
    public void parseSheet(final String spreadsheetEntryName, final String worksSheetName) {
        try {
            final SpreadsheetEntry spreadsheetEntry = CollectionUtils.find(feed.getEntries(), new Predicate<SpreadsheetEntry>() {
                @Override
                public boolean evaluate(final SpreadsheetEntry spreadsheetEntry) {
                    return spreadsheetEntryName.equals(spreadsheetEntry.getTitle().getPlainText());
                }
            });

            parseWorksheet(CollectionUtils.find(spreadsheetEntry.getWorksheets(), new Predicate<WorksheetEntry>() {
                @Override
                public boolean evaluate(final WorksheetEntry worksheetEntry) {
                    return worksSheetName.equals(worksheetEntry.getTitle().getPlainText());
                }
            }), spreadsheetEntryName + "-" + worksSheetName);
        } catch (Exception e) {
            throw new GeneratorExcepction("PROGRAMMING", e);
        }
    }

    private void parseWorksheet(final WorksheetEntry worksheet, final String id) throws IOException, ServiceException, ClassNotFoundException {
        final CellQuery query = new CellQuery(worksheet.getCellFeedUrl());
        final CellFeed cellFeed = service.query(query, CellFeed.class);

        values.clear();
        headers.clear();

        for (final CellEntry cellEntry : cellFeed.getEntries()) {
            final Cell cell = cellEntry.getCell();

            if (cell.getRow() == 1) {
                headers.put(cell.getCol(), cell.getValue());
            } else {
                Map<String, Container> map = values.get(cell.getRow());

                if (map == null) {
                    map = new HashMap<String, Container>();
                    values.put(cell.getRow(), map);
                }

                map.put(headers.get(cell.getCol()), new Container(cell.getValue()));
            }
        }

        // saving to cache
        new File(DDLGenerator.ROOT_RT_PATH() + "spreadsheets-cache/").mkdirs();

        final Collection<Map<String, Container>> saving = new ArrayList<Map<String, Container>>(values.values());
        final File file = new File(DDLGenerator.ROOT_RT_PATH() + "spreadsheets-cache/" + id);

        SerializationHelper.saveObjectFile(saving, file);
        result = (Collection<Map<String, Container>>) SerializationHelper.loadObjectFromFile(file);
    }


    @Override
    public void printInfo() {
        System.err.println("Headers:");
        for (final Map.Entry<Integer, String> entry : headers.entrySet()) {
            System.err.println("[" + entry.getKey() + "] is [" + entry.getValue() + "]");
        }

        System.err.println("\nValues:");
        for (final Map.Entry<Integer, Map<String, Container>> rowEntry : values.entrySet()) {
            for (final Map.Entry<String, Container> colEntry : rowEntry.getValue().entrySet()) {

                System.err.println("row [" + rowEntry.getKey() + "] header [" + colEntry.getKey() + "] has value [" + colEntry.getValue().asString() + "]");
            }
        }
    }


    @Override
    public Collection<Map<String, Container>> getValues() {
        return result;
    }
}
