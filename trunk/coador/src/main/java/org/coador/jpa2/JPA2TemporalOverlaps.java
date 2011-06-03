package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.coador.Operand;
import org.coador.TimePeriod;

/**
 * TODO: Acertar, usar algum esquema de delegate... Implementação baseada no
 * PG_Temporal do PostgreSQL....
 * 
 * 
 * Isso não funciona para outros SGBDs, pelo menos imagino que não!
 * 
 * @author rthoth
 * 
 */
public class JPA2TemporalOverlaps extends JPA2Criterion {

    private JPA2Operand o1;
    private JPA2TimePeriod period;

    public JPA2TemporalOverlaps(Operand o1, TimePeriod period) {
        this.o1 = (JPA2Operand) o1;
        this.period = (JPA2TimePeriod) period;
    }

    @Override
    public Predicate predicate(CriteriaBuilder cb, Root<?> root) {
        Expression<Boolean> result = cb.function("overlaps", Boolean.class,
                period.getExpression(cb), o1.getExpression(cb));
        return cb.isTrue(result);
    }

}
