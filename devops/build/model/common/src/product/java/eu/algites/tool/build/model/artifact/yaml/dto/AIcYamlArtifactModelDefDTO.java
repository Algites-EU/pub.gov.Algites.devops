package eu.algites.tool.build.model.artifact.yaml.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AIcYamlArtifactModelDefDTO {

	@JsonProperty("artifacts")
	private List<AIcYamlArtifactDefDTO> artifacts = new ArrayList<>();

	public List<AIcYamlArtifactDefDTO> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(List<AIcYamlArtifactDefDTO> artifacts) {
		this.artifacts = artifacts;
	}
}
