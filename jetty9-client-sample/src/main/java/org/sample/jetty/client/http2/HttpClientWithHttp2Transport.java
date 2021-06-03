package org.sample.jetty.client.http2;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpClientTransport;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.client.http.HttpClientTransportOverHTTP2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Date: 2021/4/1
 */
public class HttpClientWithHttp2Transport {
    private static final Logger log = LoggerFactory.getLogger(HttpClientWithHttp2Transport.class);

    public static void main(String[] args) {
        String url = "http://127.0.0.1:8081/tomcat/get";

        HttpClient httpClient = builderHttpClient();

        Request request = buildRequest(httpClient, url);
        ContentResponse contentResponse = null;
        try {
            contentResponse = request.send();
            printResponse(contentResponse);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } catch (TimeoutException e) {
            log.error(e.getMessage(), e);
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }

        try {
            httpClient.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private static Request buildRequest(HttpClient httpClient, String url) {
        Request request = httpClient.newRequest(url).method(HttpMethod.GET);
        request.timeout(6, TimeUnit.SECONDS);
        request.idleTimeout(5, TimeUnit.SECONDS);

        String headerName = "bd3bbf3d46d74aeodwdf4a3beb2-a1cewefsdfsewfewsdfsfewfsdfewfwebe43a49f794oqdkfadpfjsidfslkdfjskldfjsdf21jwwakljdklsjflksjkpweoisdfjqpidfj293djidjfapeiujr1-823refa;asdfeowisdfdlkafdsfjdajpeiwf23j89f8u982jflsjf293fj29pfj2398fj2fjsjf9238jf9j9js9jf923jfajdj023jd3jfjoiiqwjf9j129388jf9831j9fj91jf13qiwdj9182djsadoqwijfdiwojfqiojfoiqwiojfqoweijfowiqojfqowijfoqiwfoiqwiofqiojfid46d7443eb2a1cbe43a49f794oqdkfadpfjsidfslkdfjskldfjsdfjakljdklsjflksjkpweoisdfjqpidfj293djidjfapeiujr1-823refa;asdfeowisdfdlkafdsfjdajpeiwf23j89f8u982jflsjf293fj29pfj2398fj2fjsjf9238jf9j9js9jf923jfajdj023jd3jfjoiiqwjf9j129388jf9831j9fj91jf13qiwdj9182djsadoqwijfdiwojfqiojfoiqwiojfqoweijfowiqojfqowijfoqiwfoiqwiofqiojfid46d7443eb2a1cbe43a49f794oqdkfadpfjsidfslkdfjskldfjsdfjakljdklsjflksjkpweoisdfjqpidfj293djidjfapeiujr1-823refa;asdfeowisdfdlkafdsfjdajpeiwf23j89f8u982jflsjf293fj29pfj2398fj2fjsjf9238jf9j9js9jf923jfajdj023jd3jfjoiiqwjf9j129388jf9831j9fj91jf13qiwdj9182djsadoqwijfdiwojfqiojfoiqwiojfqoweijfowiqojfqowijfoqiwfoiqwiofqiojfioqwj1293jfjsajoeijwfoiqjfioj3891f;ajdpjf2f80e9831j9fj91jf13qiwdj9182djsadoqwijfdiwojfqiojfoiqwiojfqoweijfowiqojfqowijfoqiwfoiqwiofqiojfioqwj1293jfjsajoeijwfoiqjfioj3891f;ajdpjf2f80e9831j9fj91jf13qiwdj9182djsadoqwijfdiwojfqiojfoiqwiojfqoweijfowiqojfqowijfoqiwfoiqwiofqiojfioqwj1293jfjsajoeijwfoiqjfioj3891f;ajdpjf2f80e9831j9fj91jf13qiwdj9182djsadoqwijfdiwojfqiojfo";
        String headerValue = "ldfjsdfjakljdklsjflksjkpweoisdfjqpidfj293djidjfapeiujr1-823refa;asdfeowisdfdlkafdsfjdajpeiwf23j89f8u982jflsjf293fj29pfj2398fj2fjsjf9238jf9j9js9jf923jfajdj023jd3jfjoiiqwjf9j129388jf9831j9fj91jf13qiwdj9182djsadoqwijfdiwojfqiojfoiqwiojfqoweijfowiqojfqowijfoqiwfoiqwiofqiojfid46d7443eb2a1cbe43a49f794oqdkfadpfjsidfslkdfjskldfjsdfjakljdklsjflksjkpweoisdfjqpidfj293djidjfapeiujr1-823refa;asdfeowisdfdlkafdsfjdajpeiwf23j89f8u982jflsjf293fj29pfj2398fj2fjsjf9238jf9j9js9jf923jfajdj023jd3jfjoiiqwjf9j129388jf9831j9fj91";
        request.header(headerName, headerValue);

        return request;
    }

    private static void printResponse(ContentResponse contentResponse) {
        try {
            int status = contentResponse.getStatus();
            HttpVersion version = contentResponse.getVersion();
            String contentAsString = contentResponse.getContentAsString();
            String contentEncoding = contentResponse.getHeaders().get(HttpHeader.CONTENT_ENCODING);
            log.info("status: {}, version:{}, contentEncoding:{}, contentAsString:{}", status, version, contentEncoding, contentAsString);
        } catch (Exception e) {
            log.error("print response error");
        }
    }

    private static HttpClient builderHttpClient() {
        HTTP2Client http2Client = new HTTP2Client();
        http2Client.setSelectors(1);
        HttpClientTransport transport = new HttpClientTransportOverHTTP2(http2Client);

        HttpClient httpClient = new HttpClient(transport, null);
        httpClient.setConnectTimeout(1000);
        httpClient.setIdleTimeout(30000);
        // Increase header size
        httpClient.setRequestBufferSize(16384);
        httpClient.setFollowRedirects(true);
        try {
            httpClient.start();
            return httpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
