package ar.edu.unq.tpi.util.services.jasper;

import java.util.ArrayList;

public class JasperReportBasic {

    public static void main(final String[] args) {
        // Report<Person> report = new Report<Person>("persona");
        //
        // HashMap<String, String> parameters = new HashMap<String, String>();
        // parameters.put("Title", "Personas");
        //
        // List<Person> beanCollection = getBeanCollection();
        // report.exportPDF(beanCollection, parameters);
        //
        // report.exportEXEL(beanCollection, parameters);
        //
        // report.exportRTF(beanCollection, parameters);
        // System.out.println("Done");

    }

    public static ArrayList<Person> getBeanCollection() {

        Person phone1 = new Person("Jorge", "home", "913-906-6000", 123123);
        Person phone2 = new Person("Luis", "work", "913-906-6001", 123123);
        Person phone3 = new Person("Pedro", "mobile", "913-906-6002", 12312);
        Person phone4 = new Person("Susy", "casa", "913-906-6002", 123123);
        Person phone5 = new Person("Pepe", "lala", "913-906-6002", 1231);
        Person phone6 = new Person("Ronny", "sasa", "913-906-6002", 123123);
        Person phone7 = new Person("Juan", "mala", "913-906-6002", 123123);

        ArrayList<Person> phones = new ArrayList<Person>();

        phones.add(phone1);
        phones.add(phone2);
        phones.add(phone3);
        phones.add(phone4);
        phones.add(phone5);
        phones.add(phone6);
        phones.add(phone7);
        return phones;
    }
}
