package eu.algites.tool.build.model.artifact.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AIcDependencyDefDTO {

	/**
	 * ID of the target artifact in the same YAML model
	 */
	@JsonProperty(value = "target", required = true)
	private String targetId;

	@JsonProperty("scope")
	private AIcScopeDefDTO scope;

	@JsonProperty("exclusions")
	private List<AIcExclusionDefDTO> exclusions;

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public AIcScopeDefDTO getScope() {
		return scope;
	}

	public void setScope(AIcScopeDefDTO scope) {
		this.scope = scope;
	}

	public List<AIcExclusionDefDTO> getExclusions() {
		return exclusions;
	}

	public void setExclusions(List<AIcExclusionDefDTO> exclusions) {
		this.exclusions = exclusions;
	}
}
