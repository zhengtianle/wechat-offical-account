package util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * 证书信任管理器(用于https请求)
 * 本来应该是信任我们制定的证书
 * 这份代码意味着信任所有证书，不管是不是权威机构发放
 *
 * @auther ZhengTianle
 * @Date: 18-7-15
 */
public class MyX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
