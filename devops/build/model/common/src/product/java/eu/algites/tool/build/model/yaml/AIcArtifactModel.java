package eu.algites.tool.build.model.yaml;

import eu.algites.tool.build.model.common.AIiAbstractArtifact;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Resolved artifact model loaded from YAML. Holds both ordered list and ID-based lookup.
 */
public record AIcArtifactModel(List<AIiAbstractArtifact> artifacts, Map<String, AIiAbstractArtifact> artifactsById) {

	public AIcArtifactModel(List<AIiAbstractArtifact> artifacts, Map<String, AIiAbstractArtifact> artifactsById) {
		this.artifacts = artifacts == null ? Collections.emptyList() : Collections.unmodifiableList(artifacts);
		this.artifactsById = artifactsById == null ? Collections.emptyMap() : Collections.unmodifiableMap(artifactsById);
	}

	public AIiAbstractArtifact getArtifactById(String id) {
		return artifactsById.get(id);
	}
}
