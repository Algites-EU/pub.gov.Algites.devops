package eu.algites.tool.build.model.common;

import static eu.algites.tool.build.model.common.AInArtifactKind.AGGREGATOR;

import java.util.List;

/**
 * <p>
 * Title: {@link AIiControlledAggregatorArtifact}
 * </p>
 * <p>
 * Description: Basic interface for the Algites Aggregator Artifacts
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
public interface AIiControlledAggregatorArtifact extends AIiControlledArtifact {

	/**
	 * Gets the contained artifacts
	 *
	 * @return the contained artifacts
	 */
	List<AIiControlledArtifact> getContainedArtifacts();

	/**
	 * Gets the kind of the artifact {@link AInArtifactKind#AGGREGATOR}
	 *
	 * @return the kind of the artifact
	 */
	@Override
	default AInArtifactKind getArtifactKind() {
		return AGGREGATOR;
	}
}
