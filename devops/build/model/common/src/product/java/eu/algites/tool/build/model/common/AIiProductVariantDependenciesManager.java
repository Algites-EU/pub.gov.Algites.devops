package eu.algites.tool.build.model.common;

import java.util.List;

/**
 * <p>
 * Title: {@link AIiProductVariantDependenciesManager}
 * </p>
 * <p>
 * Description: Container of the background managed dependencies
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
public interface AIiProductVariantDependenciesManager extends AIiManagedDependencies {

	/**
	 * Gets the managed product variant uncotrolled dependencies
	 *
	 * @return the managed product variant uncotrolled dependencies
	 */
	List<AIiArtifactDependency<? extends AIiUncontrolledArtifact>> getManagedProductVariantDependencies();

}
