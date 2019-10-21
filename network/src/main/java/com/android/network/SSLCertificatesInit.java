package com.android.network;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Guyue on 2017/8/15.
 */

public class SSLCertificatesInit {
    public static final String TLS = "TLS";
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    private static final String SERVER_CERTIFICATE_TYPE = "X.509";
    private static final String CLIENT_CERTIFICATE_TYPE = "BKS";
    
    /**
     * 信任所有站点
     *
     * @param socketFactory 输出参数，在外部创建1个SSLSocketFactory数组
     * @param trustManager  输出参数，在外部创建1个X509TrustManager数组
     */
    public static void init(SSLSocketFactory[] socketFactory, X509TrustManager[] trustManager) {
        try {
            SSLContext sslContext = SSLContext.getInstance(TLS);
            trustManager[0] = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
                
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    
                }
            };
            sslContext.init(null, new TrustManager[]{trustManager[0]}, new SecureRandom());
            socketFactory[0] = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 单向验证
     *
     * @param socketFactory 输出参数，在外部创建1个SSLSocketFactory数组
     * @param trustManager  输出参数，在外部创建1个X509TrustManager数组
     * @param in            服务器cer证书文件的InputStream对象
     */
    public static void init(SSLSocketFactory[] socketFactory, X509TrustManager[] trustManager, InputStream in) {
        try {
            SSLContext sslContext = SSLContext.getInstance(TLS);
            TrustManager[] tmArr = createTrustManagers(genServerKeyStore(in));
            sslContext.init(null, tmArr, new SecureRandom());
            trustManager[0] = getX509TrustManager(tmArr);
            socketFactory[0] = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static TrustManager[] createTrustManagers(KeyStore ks) throws KeyStoreException, NoSuchAlgorithmException {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        return tmf.getTrustManagers();
    }
    
    private static KeyStore genServerKeyStore(InputStream in) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        CertificateFactory cf = CertificateFactory.getInstance(SERVER_CERTIFICATE_TYPE);
        keyStore.setCertificateEntry(SERVER_CERTIFICATE_TYPE, cf.generateCertificate(in));
        return keyStore;
    }
    
    /**
     * 双向验证
     *
     * @param socketFactory 输出参数，在外部创建1个SSLSocketFactory数组
     * @param trustManager  输出参数，在外部创建1个X509TrustManager数组
     * @param serverIn      服务器cer证书文件的InputStream对象
     * @param clientIn      客户端bks证书的InputStream对象
     * @param clientPwd     客户端bks证书的密码
     */
    public static void getSocketFactory(SSLSocketFactory[] socketFactory, X509TrustManager[] trustManager, InputStream serverIn, InputStream clientIn, String clientPwd) {
        try {
            SSLContext sslContext = SSLContext.getInstance(TLS);
            TrustManager[] tmArr = createTrustManagers(genServerKeyStore(serverIn));
            sslContext.init(createKeyManagers(genClientKeyStore(clientIn, clientPwd), clientPwd), tmArr, new SecureRandom());
            trustManager[0] = getX509TrustManager(tmArr);
            socketFactory[0] = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } finally {
            try {
                serverIn.close();
                clientIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static KeyManager[] createKeyManagers(KeyStore ks, String pwd) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, pwd.toCharArray());
        return kmf.getKeyManagers();
    }
    
    private static KeyStore genClientKeyStore(InputStream in, String pwd) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore ks = KeyStore.getInstance(CLIENT_CERTIFICATE_TYPE);
        ks.load(in, pwd.toCharArray());
        return ks;
    }
    
    private static X509TrustManager getX509TrustManager(TrustManager[] tmArr) {
        for (TrustManager tm : tmArr) {
            if (tm instanceof X509TrustManager) {
                return (X509TrustManager) tm;
            }
        }
        return null;
    }
}
