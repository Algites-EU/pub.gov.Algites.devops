package eu.algites.tool.build.model;

import eu.algites.tool.build.model.artifact.intf.AIiAbstractArtifact;
import eu.algites.tool.build.model.repository.intf.AIiSourceRepository;

import java.util.Collections;
import java.util.Map;

/**
 * Resolved artifact model loaded from YAML. Holds both ordered list and ID-based lookup.
 * @author linhart1
 */
public record AIcArtifactModel(AIiSourceRepository sourceRepository, Map<String, AIiAbstractArtifact> artifactsByIdMap) {

	public AIcArtifactModel(AIiSourceRepository sourceRepository, Map<String, AIiAbstractArtifact> artifactsByIdMap) {
		this.sourceRepository = sourceRepository;
		this.artifactsByIdMap = artifactsByIdMap == null ? Collections.emptyMap() : Collections.unmodifiableMap(artifactsByIdMap);
	}

	public AIiAbstractArtifact getArtifactById(String id) {
		return artifactsByIdMap.get(id);
	}

	public AIiSourceRepository getSourceRepository() {
		return sourceRepository;
	}
}
