package org.coador.jpa2;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import org.coador.TimePeriod;

public class JPA2TimePeriod extends JPA2Operand implements TimePeriod {

    private Calendar cI;
    private Calendar cF;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public JPA2TimePeriod(Calendar cI, Calendar cF) {
        this.cI = cI;
        this.cF = cF;
    }

    @Override
    public Expression<?> getExpression(CriteriaBuilder cb) {

        return cb
                .function(
                        "period",
                        Time.class,
                        cb.literal("[" + toUTMString(cI) + ","
                                + toUTMString(cF) + ")"));
    }

    private static String toUTMString(Calendar cal) {
        return FORMAT.format(cal.getTime());
    }

    @Override
    public Expression<?> getLowerExpression(CriteriaBuilder cb) {
        return null;
    }

}
