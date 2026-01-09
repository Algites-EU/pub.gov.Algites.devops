package eu.algites.tool.build.model.common.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Single "union" DTO for all artifact kinds. Unused fields for a given kind may be omitted in YAML.
 * @author linhart1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AIcArtifactContainerDefDTO {

	/**
	 * Aggregator only
	 */
	@JsonProperty("containedArtifactRelativePaths")
	private List<String> containedArtifactRelativePaths;

	public List<String> getContainedArtifactRelativePaths() {
		return containedArtifactRelativePaths;
	}

	public void setContainedArtifactRelativePaths(List<String> aContainedArtifactPaths) {
		this.containedArtifactRelativePaths = aContainedArtifactPaths;
	}

}
