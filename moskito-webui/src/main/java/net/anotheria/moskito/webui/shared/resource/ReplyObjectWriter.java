package net.anotheria.moskito.webui.shared.resource;

import net.anotheria.moskito.webui.accumulators.api.AccumulatorDefinitionAO;
import net.anotheria.moskito.webui.dashboards.api.DashboardAO;
import net.anotheria.moskito.webui.dashboards.api.DashboardDefinitionAO;
import net.anotheria.moskito.webui.producers.api.UnitCountAO;
import net.anotheria.moskito.webui.threshold.api.ThresholdAlertAO;
import net.anotheria.moskito.webui.threshold.api.ThresholdDefinitionAO;
import net.anotheria.moskito.webui.threshold.api.ThresholdStatusAO;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Writer for xml output of the reply object.
 *
 * @author lrosenberg
 * @since 06.10.14 13:20
 */
@Provider
@Produces(MediaType.APPLICATION_XML)
public class ReplyObjectWriter implements MessageBodyWriter<ReplyObject> {
	/**
	 * The list of java classes to be recognized by the new {@link JAXBContext}.
     */
	private static final Class[] classesToBeBound = {
			ThresholdAlertAO.class,
			ThresholdStatusAO.class,
			AccumulatorDefinitionAO.class,
			ThresholdDefinitionAO.class,
			UnitCountAO.class,
			DashboardDefinitionAO.class,
			DashboardAO.class
	};

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return type.equals(ReplyObject.class);
	}

	@Override
	public long getSize(ReplyObject replyObject, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(ReplyObject replyObject, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
		try {

			entityStream.write(
					"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>".getBytes("UTF-8")
			);
			entityStream.write("<reply>".getBytes());
			entityStream.write(("<success>"+replyObject.isSuccess()+"</success>").getBytes("UTF-8"));
			if (replyObject.getMessage()!=null)
				entityStream.write(("<message>"+replyObject.getMessage()+"</message>").getBytes("UTF-8"));

			entityStream.write("<results>".getBytes());
			HashMap results = replyObject.getResults();
			Set<Map.Entry> resultSet = results.entrySet();

			JAXBContext context = JAXBContext.newInstance(classesToBeBound);
			Marshaller m = context.createMarshaller();
			m.setProperty("jaxb.fragment", Boolean.TRUE);

			for (Map.Entry entry : resultSet){
				String sectionName = entry.getKey().toString();
				entityStream.write(('<' +sectionName+ '>').getBytes());

				m.marshal(entry.getValue(), entityStream);

				entityStream.write(("</"+sectionName+ '>').getBytes());
			}

			entityStream.write("</results>".getBytes());
			entityStream.write("</reply>".getBytes());


        } catch (JAXBException exception) {
            exception.printStackTrace();
        }
	}

	private void writeList(List list, Marshaller marshaller, OutputStream stream ) throws IOException, JAXBException{
		for (Object o : list){
			marshaller.marshal(o, stream);
		}
	}
}
