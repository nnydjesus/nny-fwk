package ar.com.nny.base.persistence.annotations;


public enum OrderType {

    ASC {
        @Override
        public String getOrderString(String fieldName) {
            return fieldName + " asc";
        }
    },
    DESC {
        @Override
        public String getOrderString(String fieldName) {
            return fieldName + " desc";
        }
    };


    public abstract String getOrderString(String fieldName);

}
