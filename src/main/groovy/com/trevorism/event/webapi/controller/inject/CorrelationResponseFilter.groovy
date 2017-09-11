package com.trevorism.event.webapi.controller.inject

import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.http.util.CorrelationGenerator

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter

/**
 * @author tbrooks
 */
class CorrelationResponseFilter implements ContainerResponseFilter{

    @Override
    void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if(responseContext.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY))
            return

        String correlationId = requestContext.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY)
        if(!correlationId)
            correlationId = CorrelationGenerator.generate()
        responseContext.headers.putSingle(HeadersHttpClient.CORRELATION_ID_HEADER_KEY, correlationId)
    }
}
