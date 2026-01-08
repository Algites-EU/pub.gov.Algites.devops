package eu.algites.tool.build.model.artifact.yaml.dto;

import eu.algites.tool.build.model.artifact.common.AInArtifactDependencyScopeLevel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AIcYamlScopeDefDTO {

	@JsonProperty(value = "level", required = true)
	private AInArtifactDependencyScopeLevel level;

	@JsonProperty("locked")
	private Boolean locked;

	@JsonProperty("transitive")
	private Boolean transitive;

	public AInArtifactDependencyScopeLevel getLevel() {
		return level;
	}

	public void setLevel(AInArtifactDependencyScopeLevel level) {
		this.level = level;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getTransitive() {
		return transitive;
	}

	public void setTransitive(Boolean transitive) {
		this.transitive = transitive;
	}
}
