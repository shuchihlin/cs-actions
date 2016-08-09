package io.cloudslang.content.jclouds.services;

import io.cloudslang.content.jclouds.entities.AuthorizationHeader;
import io.cloudslang.content.jclouds.entities.constants.Constants;
import io.cloudslang.content.jclouds.services.helpers.AWSSignatureHelper;
import io.cloudslang.content.jclouds.services.helpers.AWSSignatureV4;
import io.cloudslang.content.jclouds.services.helpers.UriEncoder;
import org.apache.commons.lang3.StringUtils;

import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mihai Tusa.
 * 8/8/2016.
 */
public class AmazonSignatureService {
    private static final String AWS4_SIGNING_ALGORITHM = "AWS4-HMAC-SHA256";
    private static final String DOT = ".";
    private static final String EMPTY_STRING_HASH = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    private static final String HOST = "host";
    private static final String HTTP_METHOD_GET = "GET";
    private static final String SLASH = "/";
    private static final String T_REGEX_STRING = "T";

    private static final char SLASH_CHAR = '/';

    private AWSSignatureV4 awsSignatureV4 = new AWSSignatureV4();

    /**
     * Computes the AWS Signature Version 4 used to authenticate requests by using the authorization header.
     * For this signature type the checksum of the entire payload is computed.
     */
    public AuthorizationHeader computeSignatureAuthorization(String requestHttpMethod, String requestUri, String payloadHash,
                                                             Map<String, String> queryParams, Map<String, String> requestHeaders,
                                                             String amazonApi, String requestEndpoint, String accessKeyId,
                                                             String secretAccessKey, String amzDate, boolean uriEncode)
            throws SignatureException {
        requestHeaders = requestHeaders == null ? new HashMap<String, String>() : requestHeaders;

        if (!requestHeaders.containsKey(HOST)) {
            requestHeaders.put(HOST, requestEndpoint);
        }
        if (uriEncode) {
            requestUri = UriEncoder.escapeString(requestUri, false, new char[]{SLASH_CHAR});
        }

        requestHttpMethod = StringUtils.isBlank(requestHttpMethod) ? HTTP_METHOD_GET : requestHttpMethod;
        requestUri = StringUtils.isBlank(requestUri) ? SLASH : requestUri;
        payloadHash = StringUtils.isBlank(payloadHash) ? EMPTY_STRING_HASH : payloadHash;
        requestEndpoint = StringUtils.isBlank(requestEndpoint) ? amazonApi.toLowerCase() + DOT +
                Constants.Miscellaneous.AMAZON_HOSTNAME : requestEndpoint.toLowerCase();

        AWSSignatureHelper signatureUtils = new AWSSignatureHelper();

        String amazonDate = StringUtils.isBlank(amzDate) ? signatureUtils.getAmazonDateString(new Date()) : amzDate;
        String dateStamp = amazonDate.split(T_REGEX_STRING)[0];
        String region = signatureUtils.getAmazonRegion(requestEndpoint);
        String credentialScope = signatureUtils.getAmazonCredentialScope(dateStamp, region, amazonApi.toLowerCase());
        String amzCredential = accessKeyId + Constants.Miscellaneous.SCOPE_SEPARATOR + credentialScope;

        String signedHeadersString = signatureUtils.getSignedHeadersString(requestHeaders);
        String canonicalRequest = awsSignatureV4.getCanonicalRequest(requestHttpMethod, requestUri,
                signatureUtils.canonicalizedQueryString(queryParams), signatureUtils.canonicalizedHeadersString(requestHeaders),
                signedHeadersString, payloadHash);

        String stringToSign = awsSignatureV4.getStringToSign(amazonDate, credentialScope, canonicalRequest);
        byte[] key = awsSignatureV4.getDerivedSigningKey(secretAccessKey, dateStamp, region, amazonApi.toLowerCase());
        String signature = awsSignatureV4.getSignature(stringToSign, key);

        return new AuthorizationHeader(AWS4_SIGNING_ALGORITHM, amzCredential, signedHeadersString, signature);
    }
}