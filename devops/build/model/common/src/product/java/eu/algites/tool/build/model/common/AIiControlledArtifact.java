package eu.algites.tool.build.model.common;

/**
 * <p>
 * Title: {@link AIiControlledArtifact}
 * </p>
 * <p>
 * Description: Basic interface for the Algites Known artifacts (wth known structure, dependencies, etc)
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 07.01.26 14:07
 */
public interface AIiControlledArtifact extends AIiAbstractArtifact {

	/**
	 * Gets the version context of the known artifact
	 * @return the version context of the known artifact
	 */
	AIiVersionContext getVersionContext();

}
