package ar.edu.unq.tpi.util.services.spreadsheets;

import java.io.Serializable;


public class Container implements Serializable {

        private static final long serialVersionUID = 8244042268800849188L;

        private final String value;

        public Container(final String value) {
            this.value = value;
        }


        public String asString() {
            return value == null ? null : value.trim();
        }

        @Override
        public String toString() {
            return value;
        }

        public Integer asInteger() {
            return Integer.parseInt(value);
        }
        
        public Double asDouble() {
            return Double.parseDouble(value);
        }

        public Float asFloat() {
            return Float.parseFloat(value);
        }
    }