package eu.algites.tool.build.model.artifact.intf;

import eu.algites.tool.build.model.version.intf.AIiVersionContext;

import java.nio.file.Path;

/**
 * <p>
 * Title: {@link AIiAbstractControlledArtifact}
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
public interface AIiAbstractControlledArtifact extends AIiAbstractArtifact {

	String ARTIFACT_CONFIG_FILE_NAME_WITHOUT_EXT = "algites-artifact";

	/**
	 * Gets the version context of the known artifact
	 *
	 * @return the version context of the known artifact
	 */
	AIiVersionContext getVersionContext();

	/**
	 * Gets the path to the artifact configuration file.
	 * @return the path to the artifact configuration file.
	 */
	Path getArtifactConfigurationFile();

}
