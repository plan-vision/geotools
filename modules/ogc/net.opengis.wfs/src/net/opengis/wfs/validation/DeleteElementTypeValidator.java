/**
 *
 * $Id$
 */
package net.opengis.wfs.validation;

import javax.xml.namespace.QName;

import org.geotools.api.filter.Filter;

/**
 * A sample validator interface for {@link net.opengis.wfs.DeleteElementType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface DeleteElementTypeValidator {
  boolean validate();

  boolean validateFilter(Filter value);
  boolean validateHandle(String value);
  boolean validateTypeName(QName value);
}
