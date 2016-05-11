/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import org.mousepilots.es.core.command.Update;
import org.mousepilots.es.core.command.UpdateAttribute;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.PluralAttributeES;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <A>
 * @param <EL>
 * @param <AD>
 * @param <MS>
 */
public interface UpdatePluralAttribute<E, EL, A, AD extends PluralAttributeES<? super E, A, EL>, MS> extends UpdateAttribute<E, A, AD, MS> {

    default A getNonNullAttributeValueOnServer(Update<E, ?, A, AD, ?> update) {
        final AD attribute = update.getAttribute();
        final MemberES<? super E, A> javaMember = attribute.getJavaMember();
        final E realSubject = update.getRealSubject();
        A retval = javaMember.get(realSubject);
        if(retval==null){
            retval = attribute.createEmpty();
            javaMember.set(realSubject,retval);
        }
        return retval;
    }

}
