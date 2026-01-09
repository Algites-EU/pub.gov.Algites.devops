package eu.algites.tool.build.model.repository.intf;

import eu.algites.tool.build.model.common.intf.AIiAbstractArtifactContainer;
import eu.algites.tool.build.model.version.intf.AIiVersionContext;

import java.nio.file.Path;

/**
 * <p>
 * Title: {@link AIiSourceRepository}
 * </p>
 * <p>
 * Description: Basic interface of the artifact source repository
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 09.01.26 11:31
 */
public interface AIiSourceRepository extends AIiAbstractArtifactContainer {

	String SOURCE_REPOSITORY_CONFIG_FILE_NAME_WITHOUT_EXT = "algites-source-repository";

	/**
	 * Gets the unique identifier of the repository
	 * @return the unique identifier of the repository
	 */
	String getId();

	/**
	 * Gets the name of the repository
	 * @return the name of the repository
	 */
	String getName();

	/**
	 * Gets the description of the repository
	 * @return the description of the repository
	 */
	String getDescription();

	/**
	 * Gets the version context of the repository
	 *
	 * @return the version context of the repository. It may be empty if no is defined, then this returns null
	 */
	AIiVersionContext getVersionContext();

	/**
	 * Gets the path to the repository configuration file.
	 * @return the path to the repository configuration file.
	 */
	Path getRepositoryConfigurationFile();

}
