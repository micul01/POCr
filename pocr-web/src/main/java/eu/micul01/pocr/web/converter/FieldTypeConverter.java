package eu.micul01.pocr.web.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.micul01.pocr.entity.TypeEntity;

@FacesConverter("fieldTypeConverter")
public class FieldTypeConverter implements Converter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FieldTypeConverter.class);

	private static String SEPARATOR = ",";
	private static String END = ".";

	@Override
	public Object getAsObject(final FacesContext context,
			final UIComponent component, final String typeAsString) {

		LOGGER.info("getAsObject(). String to be converted: " + typeAsString);
		final String[] typeSplitted = typeAsString.split(SEPARATOR);
		if (typeSplitted.length != 3) {
			final FacesMessage msg = new FacesMessage("Eroare de conversie.",
					"Format invalid string tip camp.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(msg);
		}

		final TypeEntity type = new TypeEntity();
		type.setId(Integer.parseInt(typeSplitted[0]));
		type.setName(typeSplitted[1]);
		return type;
	}

	@Override
	public String getAsString(final FacesContext context,
			final UIComponent component, final Object typeAsObject) {
		if (typeAsObject == null || !(typeAsObject instanceof TypeEntity)) {
			final FacesMessage msg = new FacesMessage("Eroare de conversie.",
					"Obiectul primit nu este de tipul asteptat.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(msg);
		}
		final TypeEntity type = (TypeEntity) typeAsObject;
		final String typeAsString = type.getId() + SEPARATOR + type.getName()
				+ SEPARATOR + END;
		LOGGER.info("getAsString(). String resulted: " + typeAsString);
		return typeAsString;
	}
}
