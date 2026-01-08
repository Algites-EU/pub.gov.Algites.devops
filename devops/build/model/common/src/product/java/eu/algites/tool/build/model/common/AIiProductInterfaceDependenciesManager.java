package eu.algites.tool.build.model.common;

import java.util.List;

/**
 * <p>
 * Title: {@link AIiProductInterfaceDependenciesManager}
 * </p>
 * <p>
 * Description: Container of the internface managed dependencies
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 08.01.26 1:16
 */
public interface AIiProductInterfaceDependenciesManager extends AIiManagedDependencies {

	/**
	 * Gets the managed background dependencies
	 * @return the managed background dependencies
	 */
	List<AIiArtifactDependency<? extends AIiControlledArtifact>> getManagedInterfaceDependencies();

}
