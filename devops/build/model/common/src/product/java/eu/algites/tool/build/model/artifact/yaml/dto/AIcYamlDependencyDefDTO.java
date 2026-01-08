package eu.algites.tool.build.model.artifact.yaml.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AIcYamlDependencyDefDTO {

	/**
	 * ID of the target artifact in the same YAML model
	 */
	@JsonProperty(value = "target", required = true)
	private String targetId;

	@JsonProperty("scope")
	private AIcYamlScopeDefDTO scope;

	@JsonProperty("exclusions")
	private List<AIcYamlExclusionDefDTO> exclusions;

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public AIcYamlScopeDefDTO getScope() {
		return scope;
	}

	public void setScope(AIcYamlScopeDefDTO scope) {
		this.scope = scope;
	}

	public List<AIcYamlExclusionDefDTO> getExclusions() {
		return exclusions;
	}

	public void setExclusions(List<AIcYamlExclusionDefDTO> exclusions) {
		this.exclusions = exclusions;
	}
}
