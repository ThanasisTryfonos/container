package org.opentosca.bus.application.plugin.soaphttp.service.impl.processor;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.CxfPayload;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.helpers.DOMUtils;
import org.xml.sax.SAXException;

/**
 * Header-Processor of the Application Bus-SOAP/HTTP-Plug-in.<br>
 *
 * This processor copies all self defined header of the exchange object into
 * SoapHeader of the outgoing Soap message.
 *
 *
 *
 * @author Michael Zimmermann - zimmerml@iaas.uni-stuttgart.de
 *
 */
public class HeaderProcessor implements Processor {
	
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		@SuppressWarnings("unchecked")
		CxfPayload<SoapHeader> payload = exchange.getIn().getBody(CxfPayload.class);
		
		Map<String, Object> headers = exchange.getIn().getHeaders();
		for (Map.Entry<String, Object> entry : headers.entrySet()) {
			
			if (entry.getKey().equalsIgnoreCase("ReplyTo")) {
				
				String xml1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ReplyTo " + "xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Address>" + entry.getValue().toString() + "</wsa:Address></ReplyTo>";
				SoapHeader replyToSoapHeader = new SoapHeader(new QName("http://www.w3.org/2005/08/addressing", "ReplyTo"), DOMUtils.readXml(new StringReader(xml1)).getDocumentElement());
				payload.getHeaders().add(replyToSoapHeader);
				
			} else if (entry.getKey().equalsIgnoreCase("MessageID")) {
				
				String xml2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><MessageID " + "xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">" + entry.getValue().toString() + "</MessageID>";
				SoapHeader messageIdSoapHeader = new SoapHeader(new QName("http://www.w3.org/2005/08/addressing", "MessageID"), DOMUtils.readXml(new StringReader(xml2)).getDocumentElement());
				payload.getHeaders().add(messageIdSoapHeader);
				
			} else {
				
				payload.getHeaders().add(this.getSoapHeader(entry.getKey(), entry.getValue().toString()));
				
			}
		}
		
		exchange.getIn().setBody(payload);
		
	}
	
	/**
	 * Returns a SoapHeader
	 *
	 * @param key of the header
	 * @param content of the header
	 * @return SoapHeader
	 */
	private SoapHeader getSoapHeader(String key, String content) {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><" + key + ">" + content + "</" + key + ">";
		try {
			return new SoapHeader(new QName(key), DOMUtils.readXml(new StringReader(xml)).getDocumentElement());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
}
