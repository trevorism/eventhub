package com.trevorism.event.webapi.controller.inject

import com.trevorism.http.headers.HeadersHttpClient

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import java.util.logging.Logger

/**
 * @author tbrooks
 */
class LoggingRequestFilter implements ContainerRequestFilter{

    private static final Logger log = Logger.getLogger(LoggingRequestFilter.class.name)

    @Override
    void filter(ContainerRequestContext requestContext) throws IOException {
        String correlationId = requestContext.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY)
        String output = ""
        if(correlationId)
            output = "${correlationId}: "

        log.info("${output}Received a ${requestContext.getMethod()} request at ${requestContext.requestUri}")
    }
}
