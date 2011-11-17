package ar.edu.unq.tpi.util.services.spreadsheets;

public enum GeneratorsEnvironments {

    DEFAULT("./", true),
    DEPLOY("./", true);

    private final String path;
    private final boolean useCacheSpreadSheets;

    public static boolean DEVELOPMENT = true;

    private GeneratorsEnvironments(final String path, final boolean useCacheSpreadSheets) {
        this.path = path;
        this.useCacheSpreadSheets = useCacheSpreadSheets;
    }

    public boolean useCacheSpreadSheets() {
        return useCacheSpreadSheets;
    }

    public String getPath() {
        return path;
    }
}
