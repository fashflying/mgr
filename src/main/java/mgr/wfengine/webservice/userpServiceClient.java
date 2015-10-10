
package mgr.wfengine.webservice;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;

import mgr.wfengine.util.Utility;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

import com.qtong.service.userpServicePortType;
public class userpServiceClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public userpServiceClient() throws IOException {
        create0();
        Endpoint userpServicePortTypeLocalEndpointEP = service0 .addEndpoint(new QName("http://service.qtong.com", "userpServicePortTypeLocalEndpoint"), new QName("http://service.qtong.com", "userpServicePortTypeLocalBinding"), "xfire.local://userpService");
        endpoints.put(new QName("http://service.qtong.com", "userpServicePortTypeLocalEndpoint"), userpServicePortTypeLocalEndpointEP);
  
        //Endpoint userpServiceHttpPortEP = service0 .addEndpoint(new QName("http://service.qtong.com", "userpServiceHttpPort"), new QName("http://service.qtong.com", "userpServiceHttpBinding"), Utility.getUSER_WSURL());
        Endpoint userpServiceHttpPortEP = service0 .addEndpoint(new QName("http://service.qtong.com", "userpServiceHttpPort"), new QName("http://service.qtong.com", "userpServiceHttpBinding"), "http://10.120.147.26/userpInterface/services/userpService");
        endpoints.put(new QName("http://service.qtong.com", "userpServiceHttpPort"), userpServiceHttpPortEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

    public Collection getEndpoints() {
        return endpoints.values();
    }

    private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap props = new HashMap();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((com.qtong.service.userpServicePortType.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://service.qtong.com", "userpServiceHttpBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://service.qtong.com", "userpServicePortTypeLocalBinding"), "urn:xfire:transport:local");
        }
    }

    public userpServicePortType getuserpServicePortTypeLocalEndpoint() {
        return ((userpServicePortType)(this).getEndpoint(new QName("http://service.qtong.com", "userpServicePortTypeLocalEndpoint")));
    }

    public userpServicePortType getuserpServicePortTypeLocalEndpoint(String url) {
        userpServicePortType var = getuserpServicePortTypeLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public userpServicePortType getuserpServiceHttpPort() {
        return ((userpServicePortType)(this).getEndpoint(new QName("http://service.qtong.com", "userpServiceHttpPort")));
    }

    public userpServicePortType getuserpServiceHttpPort(String url) {
        userpServicePortType var = getuserpServiceHttpPort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }
}
