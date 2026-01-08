package eu.algites.tool.build.model.common;

/**
 * <p>
 * Title: {@link AIiParentContainer}
 * </p>
 * <p>
 * Description: Contains the parent for the artifact
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 08.01.26 10:33
 */
public interface AIiParentContainer<A extends AIiAbstractArtifact> {

	/**
	 * Gets the parent of the artifact
	 * @return the parent of the artifact
	 */
	A getParent();
}
