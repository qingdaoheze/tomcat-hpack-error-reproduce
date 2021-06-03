# tomcat-hpack-error-reproduce
HPack decode error

Tomcat issue: https://bz.apache.org/bugzilla/show_bug.cgi?id=65340

Reason description:
When the length of one header value is greater than 127 and its first prefix byte is the last one in org.apache.coyote.http2.Http2Parser#headerReadBuffer, then the return value of org.apache.coyote.http2.Hpack#decodeInteger will be -1. And then the exception "java.lang.NegativeArraySizeException: -1" is thrown in org.apache.coyote.http2.HpackDecoder#readHpackString, because the length is -1.
