/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.verify;

import com.google.common.base.Preconditions;
import io.dropwizard.auth.AuthFilter;
import java.io.IOException;
import java.security.Principal;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtContext;

/**
 *
 * @author malopez
 * @param <P>
 */
@Priority(Priorities.AUTHENTICATION)
public final class JwtAuthFilter<P extends Principal> extends AuthFilter<JwtContext, P> {

    private final JwtConsumer consumer;

    private JwtAuthFilter(JwtConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static class Builder<P extends Principal> extends AuthFilterBuilder<JwtContext, P, JwtAuthFilter<P>> {

        private JwtConsumer consumer;

        /**
         * This method sets the value of the consumer property from this class.
         *
         * @param consumer is the value to set to consumer property from this class.
         * @return the actual value of consumer from this class.
         */
        public Builder<P> setJwtConsumer(JwtConsumer consumer) {
            this.consumer = consumer;
            return this;
        }

        /**
         * This method sets the actual value of costumer property to the new Instance.
         *
         * @return a JwtAuthFilter with consumer value set in.
         */
        @Override
        protected JwtAuthFilter<P> newInstance() {
            Preconditions.checkNotNull(consumer, "JwtConsumer is not set");
            return new JwtAuthFilter<>(consumer);
        }
    }
}
